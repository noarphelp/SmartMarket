package com.example.demo.mappers;

import com.example.demo.dtos.ProductoDTO;
import com.example.demo.entities.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoDTO toADTO(Producto producto) {
        return new ProductoDTO(producto.getId(), producto.getNombre(), producto.getCategoria(), producto.getPrecio());
    }


    public Producto toEntity(ProductoDTO productoDTO) {

        return new Producto(productoDTO.getId(), productoDTO.getNombre(), productoDTO.getCategoria(), productoDTO.getPrecio(), null);
    }
}
