package com.prueba.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.dao.ClientesDao;
import com.prueba.pojo.Clientes;
import com.prueba.util.ExceptionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@CrossOrigin(origins = { "http://localhost:4200/" })
@RestController
@RequestMapping("/pruebaTecnica/CrudClientes")
@Api(value = "Condiciones Servicio Clientes")
public class ServicioWebClientes {

	protected final Log log = LogFactory.getLog(this.getClass());

	@GetMapping(value = "/ListarCliente")
	@ApiOperation(value = "Consulta Cliente", response = Clientes.class, notes = "Obtiene Los datos del Cliente ")
	@ApiResponses({
			@ApiResponse(code = 200, message = "La consulta se Ejecuto de manera correcta", response = Clientes.class),
			@ApiResponse(code = 400, message = "Bad Request.No existen Clientes Asociados a esa cedula(String)", response = Clientes.class),
			@ApiResponse(code = 500, message = "Error del sistema inesperado", response = Clientes.class),
			@ApiResponse(code = 404, message = "No existen Clientes Asociados a esa cedula", response = Clientes.class), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tipo", required = true, paramType = "query", dataType = "String", value = "Tipo de Documento", defaultValue = "XXXX"),
			@ApiImplicitParam(name = "numero", required = true, paramType = "query", dataType = "String", value = "Numero De Documento", defaultValue = "XXXX")

	})
	public  List<Object> ListarClientes(
			 @ApiParam(name = "tipo", value = "Recibe el dato del tipo de documento", required = true)
	            @RequestParam String tipo,
	            @ApiParam(name = "numero", value = "Recibe el dato del numero de documento", required = true)
	            @RequestParam String numero) {
		
		  List<Object> response = new ArrayList<>();
		try {

			// Validación de parámetros nulos
			if (tipo == null || numero == null) {
				
				String errorCode = "{\"codigo\":\""+HttpStatus.BAD_REQUEST+"\",\"mensaje\":\"Mensaje Error\",\"descripcion\":\"Tipo y número de documento son obligatorios\"}";
				response.add(errorCode);
				return response; // Error 400 Bad Request
			}

			// Validación del tipo de documento
			if (!tipo.equals("C") && !tipo.equals("P")) {
				
				String errorCode = "{\"codigo\":\""+HttpStatus.BAD_REQUEST+"\",\"mensaje\":\"Mensaje Error\",\"descripcion\":\"Tipo de documento inválido\"}";
				response.add(errorCode);
				return response; // Error 400 Bad Request
			
			
			}

			ClientesDao servicioCliente = new ClientesDao();
			List<Clientes> clientes = servicioCliente.devolverListaClientes(tipo, numero);

			// Verifica si se encontraron clientes
			if (!clientes.isEmpty()) {
				for (Clientes cliente : clientes) {
                    response.add(cliente); // Añade cada cliente a la lista de respuestas
                }
			} else {
				
				String errorCode = "{\"codigo\":\""+HttpStatus.NOT_FOUND+"\",\"mensaje\":\"Mensaje Error\",\"descripcion\":\"Cliente no encontrado\"}";
				response.add(errorCode);
				return response; // Error 400 Bad Request
				
			}

			 return response;
			 
		} catch (Exception e) {
			log.error(ExceptionUtil.format(e));
			String errorCode = "{\"codigo\":\""+HttpStatus.INTERNAL_SERVER_ERROR+"\",\"mensaje\":\"Mensaje Error\",\"descripcion\":\"Error inesperado en el servidor\"}";
			response.add(errorCode);
			return response;  // Manejo de excepciones y retorno de error 500
		}
	}



}
