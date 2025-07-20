package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaInputDTO {

    private Long sucursalId;
    private Long productoId;
    private Integer cantidad;
}
