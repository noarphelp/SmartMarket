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


    @GetMapping
    public ResponseEntity<StandardResponse<List<ProductoDTO>>> findAll() {
        List<ProductoDTO> list = productoService.finAll();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(new StandardResponse<>("Consulta exitosa", list));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardResponse<>("Fallo al cargar lista de productos", null));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ProductoDTO productoDTO) {

        if (productoDTO.getNombre().isEmpty() || productoDTO.getCategoria().isEmpty() || productoDTO.getPrecio() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fallo al crear un nuevo producto, los datos no pueden estar vacíos.");
        }

        productoService.create(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("El producto se a creado exitosamente.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody ProductoDTO productoDTO, @PathVariable Long id) {
        if (productoDTO.getNombre().isEmpty() || productoDTO.getCategoria().isEmpty() || productoDTO.getPrecio() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fallo al actualizar un producto, los datos no pueden estar vacíos.");
        }

        Optional<ProductoDTO> productoExistente = productoService.faindById(id);
        if (productoExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El producto con ID " + id + " no existe.");
        }

        ProductoDTO actualizado = productoService.update(productoDTO, id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("El producto seleccionado se ha actualizado correctamente.\n" + actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Optional<ProductoDTO> producto = productoService.faindById(id);
        if (producto.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se a podido eliminar el producto, o id invalido.");
        else {
            productoService.delete(id);
            return ResponseEntity.ok("Producto eliminado exitosamente.");
        }
    }
}
