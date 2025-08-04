package com.example.demo.mappers;

import com.example.demo.dtos.DetalleVentaDTO;
import com.example.demo.entities.DetalleVenta;
import org.springframework.stereotype.Component;

@Component
public class DetalleVentaMapper {


    public DetalleVentaDTO toADTO(DetalleVenta detalleVenta) {

        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setId(detalleVenta.getId());
        dto.setFecha(detalleVenta.getVenta().getFecha());
        dto.setCantidad(detalleVenta.getCantidad());

        dto.setNombreProducto(detalleVenta.getProducto().getNombre());

        dto.setNombreSucursal(detalleVenta.getVenta().getSucursal().getNombre());


        return dto;

    }

    public DetalleVenta toEntity(DetalleVentaDTO detalleVenta) {
        return new DetalleVenta(detalleVenta.getId(), detalleVenta.getCantidad(), null, null);
    }
}
