package com.example.demo.mappers;


import com.example.demo.dtos.VentaDTO;
import com.example.demo.entities.Venta;

public class VentaMapper {

    public static Venta toEntity(VentaDTO dto) {
        if (dto == null) return null;
        Venta venta = new Venta();
        venta.setId(dto.getId());
        venta.setFecha(dto.getFecha());
        venta.setSucursal(SucursalMapper.toEntity(dto.getSucursal()));
        // No seteamos detalles aquí
        return venta;
    }

    public static VentaDTO toDTO(Venta entity) {
        if (entity == null) return null;
        VentaDTO dto = new VentaDTO();
        dto.setId(entity.getId());
        dto.setFecha(entity.getFecha());
        dto.setSucursal(SucursalMapper.toDTO(entity.getSucursal()));
        return dto;
    }

}