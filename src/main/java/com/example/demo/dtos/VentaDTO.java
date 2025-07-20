package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaDTO {


    private Long id;
    private LocalDate fecha;
    private SucursalDTO sucursal;


}
