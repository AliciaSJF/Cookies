package com.example.cookies.modelo;

import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor@RequiredArgsConstructor
@Setter @Getter @ToString
public class Formulario {
    private String nombre;
    private String clave;

}
