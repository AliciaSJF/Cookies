package com.example.cookies.Controlador;

import com.example.cookies.modelo.Colecciones;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class Controlador {


    @GetMapping("form")
    public String devuelveForm(Model model, @CookieValue(name = "credenciales",defaultValue = "none")String contenidoCookie ) {
        if(!contenidoCookie.equals("none")){
            //Coger los nombres de la cookie
            String[] tuplas = contenidoCookie.split("#");
            ArrayList<String> nombres = new ArrayList<>();
            for (String tupla : tuplas) {
                String[] partes = tupla.split("&");
                nombres.add(partes[0]);
                System.out.println(partes[0]);
            }
            model.addAttribute("nombres", nombres);

        }
        return "form";
    }
    @PostMapping("comprobarUsuario")
    public String usuarioCorrecto(Model model,@RequestParam String nombre) {
         boolean validarnombre;
        model.addAttribute("nombre", nombre);
        if (Colecciones.existeUsuario(nombre)) {
            return "usuarioCorrecto";
        } else {
            return "form";
        }
    }
    @PostMapping("comprobarClave")
    public String claveCorrecta(
            @CookieValue(name = "credenciales",defaultValue = "none")String contenidoCookie,
            Model modelo, HttpServletResponse respuesta,
            @RequestParam String clave, @RequestParam String nombre) {
        modelo.addAttribute("nombre", nombre);

        if (Colecciones.validarClave(clave, nombre)) {
            String tuplas = "";
            int visitas = 1;
            //Hay algún registro
            if (contenidoCookie.equals("none")) {
                tuplas = nombre + "&" + visitas + "#";
                Cookie miCookie = new Cookie("credenciales", tuplas);
                miCookie.setPath("/form");
                respuesta.addCookie(miCookie);
            }
            //Hay registros anteriores
            else{
                //No contiene el nombre
                if (!contenidoCookie.contains(nombre)) {
                    tuplas = contenidoCookie + nombre + "&" + visitas + "#";
                    Cookie miCookie = new Cookie("credenciales", tuplas);
                    respuesta.addCookie(miCookie);
                    //Contiene el nombre
                }else {
                    String[] partes = contenidoCookie.split("#");
                    for (int i = 0; i < partes.length; i++) {
                        String[] info = partes[i].split("&");
                        if (info[0].equals(nombre)) {
                            visitas = Integer.parseInt(info[1]) + 1;
                            info[1] = String.valueOf(visitas); // Actualizar el número de visitas
                            partes[i] = String.join("&", info); // Reconstruir la parte de la cookie
                            break;
                        }
                    }
                    // Reconstruir la cookie
                    contenidoCookie = String.join("#", partes);
                    if (visitas == 1) {
                        contenidoCookie += (contenidoCookie.isEmpty() ? "" : "#") + nombre + "&" + visitas;
                    }
                    Cookie miCookie = new Cookie("credenciales", contenidoCookie);
                    respuesta.addCookie(miCookie);
                }
            }
            return "claveCorrecta";
        } else {
            return "usuarioCorrecto";
        }

    }

}
