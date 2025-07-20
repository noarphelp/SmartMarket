package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaDTO {


    private Long id;
    private LocalDate fecha;
    private Integer cantidad;
    private String nombreProducto;
    private String nombreSucursal;
}
