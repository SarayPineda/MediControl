package com.proyecto.medicontrol.clases;

public class utilidades {

    public static final String nombreBD = "MediControlBD";

    public static final String tablaProductos = "producto";
    public static final String campoCodigo = "codigo";
    public static final String campoDescripcion = "descripcion";
    public static final String campoPresentacion = "presentacion";
    public static final String campoDosis = "dosis";

    public static final String tablaIngresos = "ingreso";
    public static final String campoCodigoIngreso = "codigo";
    public static final String campoCodigoProducto = "producto";
    public static final String campoCantidad = "presentacion";
    public static final String campoFecha = "fecha";

    public static final String crearTablaProductos = "CREATE TABLE "+tablaProductos+" ("+campoCodigo+" INTEGER PRIMARY KEY, "+campoDescripcion+" TEXT, "+campoPresentacion+" TEXT, "+campoDosis+" TEXT)";
    public static final String crearTablaIngresos = "CREATE TABLE "+tablaIngresos+" ("+campoCodigoIngreso+" INTEGER PRIMARY KEY, "+campoCodigoProducto+" INTEGER, "+campoCantidad+" INTEGER, "+campoFecha+" TEXT)";
}
