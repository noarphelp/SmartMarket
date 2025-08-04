package com.example.demo.services.interfaces;

import com.example.demo.dtos.DetalleVentaDTO;
import com.example.demo.entities.DetalleVenta;

import java.time.LocalDate;
import java.util.List;

public interface IDetalleVentaService {

    List<DetalleVentaDTO> faindAll();

    List<DetalleVentaDTO> findByParameter(LocalDate fecha, String sucursal);

    public void delete(Long id);

    //Mapeadores
    DetalleVentaDTO convertirADTO(DetalleVenta detalleVenta);

    DetalleVenta convertirAOBJ(DetalleVentaDTO detalleVenta);
}
