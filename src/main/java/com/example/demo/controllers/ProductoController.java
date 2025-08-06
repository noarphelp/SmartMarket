package com.example.demo.controllers;

import com.example.demo.dtos.ProductoDTO;
import com.example.demo.responses.StandardResponse;
import com.example.demo.services.interfaces.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

// Obtiene todos los productos disponibles
    @GetMapping
    public ResponseEntity<StandardResponse<List<ProductoDTO>>> findAll() {
        List<ProductoDTO> list = productoService.findAll();

        // Si hay productos, devuelve estado 200 con la lista
        if (!list.isEmpty()) {
            return ResponseEntity.ok(new StandardResponse<>("Consulta exitosa", list));
        }
        // Si no hay productos, devuelve estado 404 con mensaje de error
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardResponse<>("Fallo al cargar lista de productos", null));
    }
  // para crear
    @PostMapping
    public ResponseEntity<String> create(@RequestBody ProductoDTO productoDTO) {
       // Valida que los campos del producto no estén vacíos
        if (productoDTO.getNombre().isEmpty() || productoDTO.getCategoria().isEmpty() || productoDTO.getPrecio() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fallo al crear un nuevo producto, los datos no pueden estar vacíos.");
        }
        // Crea el producto y devuelve estado 201 con mensaje de éxito
        productoService.create(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("El producto se a creado exitosamente.");
    }
//para actualizar
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody ProductoDTO productoDTO, @PathVariable Long id) {
        // Valida que los campos del producto no estén vacíos
        if (productoDTO.getNombre().isEmpty() || productoDTO.getCategoria().isEmpty() || productoDTO.getPrecio() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fallo al actualizar un producto, los datos no pueden estar vacíos.");
        }
        // Verifica si el producto con el ID existe
        Optional<ProductoDTO> productoExistente = productoService.findById(id);
        if (productoExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El producto con ID " + id + " no existe.");
        }
        // Actualiza el producto y devuelve estado 201 con mensaje de éxito
        ProductoDTO actualizado = productoService.update(productoDTO, id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("El producto seleccionado se ha actualizado correctamente.\n" + actualizado);
    }
// para eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        // Verifica si el producto con el ID existe
        Optional<ProductoDTO> producto = productoService.findById(id);
        if (producto.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido eliminar el producto, o id invalido.");
        else {
            // Elimina el producto y devuelve estado 200 con mensaje de éxito
            productoService.delete(id);
            return ResponseEntity.ok("Producto eliminado exitosamente.");
        }
    }
}
