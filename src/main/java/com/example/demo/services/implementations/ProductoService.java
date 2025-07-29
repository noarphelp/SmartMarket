package com.example.demo.services.implementations;

import com.example.demo.dtos.ProductoDTO;
import com.example.demo.entities.Producto;
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
    public void crearProducto(ProductoDTO dto) {
        if (dto == null || dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El DTO de producto o su nombre no puede ser nulo o vacío.");
        }

        Producto existente = productoRepository.findByNombre(dto.getNombre());
        if (existente != null) {
            throw new IllegalArgumentException("Ya existe un producto con el nombre: " + dto.getNombre());
        }

        Producto producto = toEntity(dto);
        productoRepository.save(producto);

    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Producto con ID " + id + " no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setCategoria(dto.getCategoria());
        producto.setPrecio(dto.getPrecio());

        Producto actualizado = productoRepository.save(producto);
        return toDTO(actualizado);
    }

    @Override
    public ProductoDTO obtenerProductoPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Producto con ID " + id + " no encontrado."));
        return toDTO(producto);
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
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }

        if (!productoRepository.existsById(id)) {
            throw new RecursoNoEncontrado("Producto con ID " + id + " no existe.");
        }

        productoRepository.deleteById(id);
    }

    public static ProductoDTO toDTO(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }

        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getPrecio()
        );

}

public static Producto toEntity(ProductoDTO dto) {
    if (dto == null) {
        throw new IllegalArgumentException("El DTO de producto no puede ser nulo.");
    }

    Producto producto = new Producto();
    producto.setId(dto.getId());
    producto.setNombre(dto.getNombre());
    producto.setCategoria(dto.getCategoria());
    producto.setPrecio(dto.getPrecio());
    return producto;
}
}


