package com.example.demo.services.interfaces;

import com.example.demo.dtos.SucursalDTO;
import java.util.List;

public interface ISucursalService {

    SucursalDTO create(SucursalDTO dto);

    SucursalDTO update(Long id, SucursalDTO dto);

    void delete(Long id);

    List<SucursalDTO> findAll();

    SucursalDTO findById(Long id);

}