package com.example.demo.services.implementations;

import com.example.demo.dtos.ProductoDTO;
import com.example.demo.entities.Producto;
import com.example.demo.repositories.ProductoRepository;
import com.example.demo.services.interfaces.IProductoService;
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
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setCategoria(dto.getCategoria());
        producto.setPrecio(dto.getPrecio());
        productoRepository.save(producto);
        return new ProductoDTO(producto.getId(), producto.getNombre(), producto.getCategoria(), producto.getPrecio());
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id).orElseThrow();
        producto.setNombre(dto.getNombre());
        producto.setCategoria(dto.getCategoria());
        producto.setPrecio(dto.getPrecio());
        productoRepository.save(producto);
        return new ProductoDTO(producto.getId(), producto.getNombre(), producto.getCategoria(), producto.getPrecio());
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }


    @Override
    public ProductoDTO obtenerProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow();
        return new ProductoDTO(producto.getId(), producto.getNombre(), producto.getCategoria(), producto.getPrecio());
    }

    @Override
    public List<ProductoDTO> obtenerTodosLosProductos() {
        return productoRepository.findAll().stream()
                .map(p -> new ProductoDTO(p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio()))
                .collect(Collectors.toList());
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

