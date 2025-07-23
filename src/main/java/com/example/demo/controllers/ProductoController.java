package com.example.demo.controllers;


import com.example.demo.dtos.ProductoDTO;
import com.example.demo.services.interfaces.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    // Listar productos
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        List<ProductoDTO> productos = productoService.obtenerTodosLosProductos();
        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productos);
        }
        return ResponseEntity.ok(productos);
    }

    // Crear producto
    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO) {
        ProductoDTO nuevoProducto = productoService.crearProducto(productoDTO);
        if (nuevoProducto == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        try {
            ProductoDTO actualizado = productoService.actualizarProducto(id, productoDTO);
            return ResponseEntity.ok(actualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Obtener producto por ID (opcional extra)
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProductoPorId(@PathVariable Long id) {
        try {
            ProductoDTO producto = productoService.obtenerProductoPorId(id);
            return ResponseEntity.ok(producto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}