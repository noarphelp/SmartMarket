package com.example.demo.services.interfaces;


import com.example.demo.dtos.ProductoDTO;

import java.util.List;

public interface IProductoService {

    // Obtener producto por ID
    ProductoDTO obtenerProductoPorId(Long id);

    // Obtener todos los productos
    List<ProductoDTO> obtenerTodosLosProductos();

    // Crear producto
    ProductoDTO crearProducto(ProductoDTO productoDTO);

    // Actualizar producto
    ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO);

    // Eliminar producto
    void eliminarProducto(Long id);
}

