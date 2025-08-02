package com.example.demo.controllers;

import com.example.demo.dtos.SucursalDTO;
import com.example.demo.services.interfaces.ISucursalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    private final ISucursalService sucursalService;

    public SucursalController(ISucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @GetMapping
    public ResponseEntity<List<SucursalDTO>> listarSucursales() {
        return ResponseEntity.ok(sucursalService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalDTO> obtenerSucursal(@PathVariable Long id) {
        return ResponseEntity.ok(sucursalService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SucursalDTO> crearSucursal(@RequestBody SucursalDTO dto) {
        return ResponseEntity.ok(sucursalService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalDTO> actualizarSucursal(@PathVariable Long id, @RequestBody SucursalDTO dto) {
        return ResponseEntity.ok(sucursalService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
        sucursalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}