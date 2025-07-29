package com.example.demo.services.interfaces;

import com.example.demo.dtos.DetalleVentaDTO;
import com.example.demo.dtos.DetalleVentaInputDTO;

import java.time.LocalDate;
import java.util.List;

public interface IDetalleVentaService {
    List<DetalleVentaDTO> findAllDetallesVenta();

    List<DetalleVentaDTO> buscarPorParametros(LocalDate fecha, List<String> sucursales);

    void eliminarDetalleVenta(Long id);
}


