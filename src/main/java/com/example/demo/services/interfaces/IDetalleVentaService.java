package com.example.demo.services.interfaces;

import com.example.demo.dtos.DetalleVentaDTO;
import com.example.demo.dtos.DetalleVentaInputDTO;

import java.time.LocalDate;
import java.util.List;

public interface IDetalleVentaService {
    List<DetalleVentaDTO> findAllDetallesVenta();



    List<DetalleVentaDTO> buscarPorParametros (String nombreProducto, String categoria,
                                               Integer cantidadMinima, List<String> sucursales, LocalDate fecha);

    void eliminarDetalleVenta(Long id);
}


