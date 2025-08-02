package com.example.demo.mappers;


import com.example.demo.dtos.SucursalDTO;
import com.example.demo.entities.Sucursal;

public class SucursalMapper {

    public static Sucursal toEntity(SucursalDTO dto) {
        if (dto == null) return null;
        Sucursal entity = new Sucursal();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setDireccion(dto.getDireccion());
        entity.setCiudad(dto.getCiudad());
        return entity;
    }

    public static SucursalDTO toDTO(Sucursal entity) {
        if (entity == null) return null;
        SucursalDTO dto = new SucursalDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDireccion(entity.getDireccion());
        dto.setCiudad(entity.getCiudad());
        return dto;
    }

}

