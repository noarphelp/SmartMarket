package com.example.demo.services.interfaces;

import com.example.demo.dtos.DetalleVentaInputDTO;
import com.example.demo.dtos.VentaDTO;
import com.example.demo.entities.Venta;

import java.util.List;


public interface IVentaService {

    List<VentaDTO> faindAll();

    void create(DetalleVentaInputDTO detalleVenta);

    void delete(Long id);


    //Mapeadores
    VentaDTO convertirADTO(Venta venta);

    Venta convertirAOBJ(VentaDTO dto);
}
