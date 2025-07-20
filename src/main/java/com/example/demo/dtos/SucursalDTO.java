package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDTO {


    private Long id;
    private String nombre;
    private String direccion;
    private String ciudad;

    @JsonIgnore
    private List<VentaDTO>ventas;
}
