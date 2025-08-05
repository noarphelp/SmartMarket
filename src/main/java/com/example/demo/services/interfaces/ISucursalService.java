package com.example.demo.services.interfaces;


import com.example.demo.dtos.SucursalDTO;
import com.example.demo.entities.Sucursal;

import java.util.List;
import java.util.Optional;

public interface ISucursalService {


    List<SucursalDTO> findAll();


    Optional<SucursalDTO> findById(Long id);

    void create(SucursalDTO sucursalDTO);

    SucursalDTO update(SucursalDTO sucursalDTO, Long id);

    void delete(Long id);


    //Mapeadores

    SucursalDTO convertirADTO(Sucursal sucursal);

    Sucursal convertirAOBJ(SucursalDTO sucursal);


}
