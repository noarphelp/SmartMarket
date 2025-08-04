package com.example.demo.services.interfaces;

import com.example.demo.dtos.VentaDTO;

import java.util.List;

public interface IVentaService {

    VentaDTO create(VentaDTO dto);

    VentaDTO update(Long id, VentaDTO dto);

    void delete(Long id);

    List<VentaDTO> findAll();

    VentaDTO findById(Long id);

}