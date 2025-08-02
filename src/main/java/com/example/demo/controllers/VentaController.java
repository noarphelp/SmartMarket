package com.example.demo.controllers;

import com.example.demo.dtos.VentaDTO;
import com.example.demo.services.interfaces.IVentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final IVentaService ventaService;

    public VentaController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public ResponseEntity<List<VentaDTO>> listarVentas() {
        return ResponseEntity.ok(ventaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> obtenerVenta(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VentaDTO> crearVenta(@RequestBody VentaDTO dto) {
        return ResponseEntity.ok(ventaService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> actualizarVenta(@PathVariable Long id, @RequestBody VentaDTO dto) {
        return ResponseEntity.ok(ventaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}