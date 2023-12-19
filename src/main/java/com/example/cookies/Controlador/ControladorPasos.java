package com.example.cookies.Controlador;

import com.example.cookies.modelo.Colecciones;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class ControladorPasos {

    @GetMapping("paso1")
    public String paso1Get(HttpSession sesion) {
        sesion.setAttribute("paso",1);
        System.out.println(1);
        return "form";
    }


    @PostMapping("paso1")
    public String paso1Post(HttpSession sesion,@RequestParam String nombre) {
        boolean validarnombre;
        if (Colecciones.existeUsuario(nombre)) {
            sesion.setAttribute("nombre", nombre);
            return "redirect:/paso2";
        } else {
            return "redirect:/paso1";
        }
    }
    @GetMapping("paso2")
    public String paso2Get(HttpSession sesion,Model modelo){


        return "usuarioCorrecto";
    }
    @PostMapping("paso2")
    public String paso2Post(@RequestParam String clave, HttpSession sesion,Model modelo){
        String nombre = (String) sesion.getAttribute("nombre");

        if (Colecciones.validarClave(clave, nombre)) {
            sesion.setAttribute("clave",clave);
            int paso = (int) sesion.getAttribute("paso") +1;
            sesion.setAttribute("paso",paso);
            modelo.addAttribute("paso",paso);
            return "redirect:/paso3";
        } else {
            return "redirect:/paso2";
        }

    }


    @GetMapping("paso3")
    public String paso3Get(Model model,HttpSession sesion){
        int paso = (int) sesion.getAttribute("paso") +1;
        model.addAttribute("paso",paso);
        sesion.setAttribute("paso",paso);
        System.out.println(paso);
        return "vistaIntermedia";
    }
    @PostMapping("paso3")
    public String paso3(){

        return "redirect:/paso4";
    }
    @GetMapping("paso4")
    public String paso4Get(Model modelo,HttpSession sesion){
        int paso = (int) sesion.getAttribute("paso") +1;
        sesion.setAttribute("paso",paso);
        modelo.addAttribute("paso",paso);
        return "vistaIntermedia";
    }
    @PostMapping("paso4")
    public String paso4(){

        return "redirect:/paso5";
    }
    @GetMapping("paso5")
    public String paso5Get(Model modelo,HttpSession sesion){
        int paso = (int) sesion.getAttribute("paso") +1;
        sesion.setAttribute("paso",paso);
        modelo.addAttribute("paso",paso);
        return "vistaIntermedia";
    }
    @PostMapping("paso5")
    public String paso5(){
        return "redirect:/dentro";
    }
    @GetMapping("dentro")
    public String dentroGet(Model modelo,HttpSession sesion){
        modelo.addAttribute("nombre",(String) sesion.getAttribute("nombre"));
        modelo.addAttribute("clave",(String) sesion.getAttribute("clave"));
        return "dentro";
    }
/*
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

 */


}
