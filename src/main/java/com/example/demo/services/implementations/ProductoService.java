package com.example.demo.services.implementations;

import com.example.demo.dtos.ProductoDTO;
import com.example.demo.entities.Producto;
import com.example.demo.exceptions.DatosInvalidos;
import com.example.demo.repositories.ProductoRepository;
import com.example.demo.services.interfaces.IProductoService;
import com.example.demo.exceptions.RecursoNoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public ProductoDTO crearProducto(ProductoDTO dto) {
        if (dto.getNombre().trim().isEmpty() ||
                dto.getCategoria().trim().isEmpty() ||
                dto.getPrecio() == null) {
            throw new RecursoNoEncontrado("Nombre, categoría o precio del producto no pueden estar vacíos.");
        }

        Producto existente = productoRepository.findByNombre(dto.getNombre());
        if (existente == null) {
            throw new DatosInvalidos("Ya existe un producto con el nombre: " + dto.getNombre());
        }

        Producto producto = toEntity(dto);
        productoRepository.save(producto);

        return dto;
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Producto con ID " + id + " no encontrado"));
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setCategoria(dto.getCategoria());
        producto.setPrecio(dto.getPrecio());

        Producto actualizado = productoRepository.save(producto);
        return toDTO(actualizado);
    }

    @Override
    public ProductoDTO obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .map(ProductoService::toDTO)
                .orElseThrow(() -> new RecursoNoEncontrado("No se encontró ningún producto con el ID: " + id));
    }



    @Override
    public List<ProductoDTO> obtenerTodosLosProductos() {
        List<Producto> productos = productoRepository.findAll();

        if (productos == null || productos.isEmpty()) {
            throw new RecursoNoEncontrado("No hay productos disponibles.");
        }

        return productos.stream()
                .map(ProductoService::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public void eliminarProducto(Long id) {
        productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Producto con ID " + id + " no existe."));

        productoRepository.deleteById(id);
    }


    public static ProductoDTO toDTO(Producto producto) {


        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getPrecio()
        );

}

public static Producto toEntity(ProductoDTO dto) {


    Producto producto = new Producto();
    producto.setId(dto.getId());
    producto.setNombre(dto.getNombre());
    producto.setCategoria(dto.getCategoria());
    producto.setPrecio(dto.getPrecio());
    return producto;
}
}


