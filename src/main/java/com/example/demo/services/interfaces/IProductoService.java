package com.example.demo.services.interfaces;

import com.example.demo.dtos.ProductoDTO;
import com.example.demo.entities.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {

    List<ProductoDTO> finAll();

    void create(ProductoDTO productoDTO);

    ProductoDTO update(ProductoDTO productoDTO, Long id);

    void delete(Long id);

    Optional<ProductoDTO> faindById(Long id);

    //Mapeadores

    ProductoDTO convertirADTO(Producto producto);

    Producto convertirAOBJ(ProductoDTO productoDTO);
}
