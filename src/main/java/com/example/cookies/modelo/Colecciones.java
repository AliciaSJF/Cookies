package com.example.cookies.modelo;

import java.util.LinkedHashMap;
import java.util.Map;

public class Colecciones {
    private static final Map<String,String> credenciales = new LinkedHashMap<>();
    static{
        credenciales.put("alfa","beta");
        credenciales.put("gamma","delta");
    }

    public static Map<String, String> getCredenciales(){
        return credenciales;
    }
    public static Boolean existeUsuario(String usuario){
        return credenciales.containsKey(usuario);

    }
    public static Boolean validarClave(String clave,String usuario){
        return credenciales.get(usuario).equals(clave);
    }

}
