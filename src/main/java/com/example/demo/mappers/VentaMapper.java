package com.example.demo.mappers;

import com.example.demo.dtos.SucursalDTO;
import com.example.demo.dtos.VentaDTO;
import com.example.demo.entities.Sucursal;
import com.example.demo.entities.Venta;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class VentaMapper {

    private final SucursalMapper sucursalMapper;

    public VentaMapper(SucursalMapper sucursalMapper) {
        this.sucursalMapper = sucursalMapper;
    }

    public VentaDTO toDTO(Venta venta) {
        if (venta == null) {
            return null;
        }
        SucursalDTO sucursalDTO = sucursalMapper.toDTO(venta.getSucursal(), v -> null);
        return new VentaDTO(
                venta.getId(),
                venta.getFecha(),
                sucursalDTO
        );
    }

    public Venta toEntity(VentaDTO dto) {
        if (dto == null) {
            return null;
        }
        Sucursal sucursal = sucursalMapper.toEntity(dto.getSucursal(), v -> null);
        return new Venta(
                dto.getId(),
                dto.getFecha(),
                sucursal,
                new ArrayList<>()
        );
    }
}
