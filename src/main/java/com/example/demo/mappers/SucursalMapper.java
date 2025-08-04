package com.example.demo.mappers;

import com.example.demo.dtos.SucursalDTO;
import com.example.demo.dtos.VentaDTO;
import com.example.demo.entities.Sucursal;
import com.example.demo.entities.Venta;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class SucursalMapper {

    public SucursalDTO toDTO(Sucursal sucursal, Function<Venta, VentaDTO> ventaConverter) {
        List<VentaDTO> ventas = sucursal.getVentas() == null ? new ArrayList<>() :
                sucursal.getVentas().stream().map(ventaConverter).toList();

        return new SucursalDTO(
                sucursal.getId(),
                sucursal.getNombre(),
                sucursal.getDireccion(),
                sucursal.getCiudad(),
                ventas
        );
    }

    public Sucursal toEntity(SucursalDTO dto, Function<VentaDTO, Venta> ventaConverter) {
        List<Venta> ventas = dto.getVentas() == null ? new ArrayList<>() :
                dto.getVentas().stream().map(ventaConverter).toList();

        return new Sucursal(
                dto.getId(),
                dto.getNombre(),
                dto.getDireccion(),
                dto.getCiudad(),
                ventas
        );
    }
}
