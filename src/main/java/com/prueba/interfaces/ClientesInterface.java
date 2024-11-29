package com.prueba.interfaces;


import java.util.List;

import com.prueba.pojo.Clientes;

public interface ClientesInterface {

	List<Clientes> devolverListaClientes(String tipo,String numero) throws Exception;

	List<Clientes> devolverClienteById(int id)  throws Exception;

	Clientes buscarClientePorDato(Clientes datos)  throws Exception;
	 
	String agregarCliente(Clientes clientes)  throws Exception;

	String actualizarCliente(Clientes clientes)  throws Exception;

	String eliminarCliente(int id)  throws Exception;
}
