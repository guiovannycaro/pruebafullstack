package com.prueba.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

public static String format(Exception e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    return sw.toString();
}
}
