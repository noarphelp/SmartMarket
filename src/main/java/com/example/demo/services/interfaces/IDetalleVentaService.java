package com.example.demo.services.interfaces;

import com.example.demo.dtos.DetalleVentaDTO;
import com.example.demo.dtos.DetalleVentaInputDTO;

public interface IDetalleVentaService {
    DetalleVentaDTO crearDetalleVenta(DetalleVentaInputDTO inputDTO);
}

