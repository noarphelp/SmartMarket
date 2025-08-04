package com.example.demo.controllers;

import com.example.demo.dtos.DetalleVentaDTO;

import com.example.demo.entities.DetalleVenta;

import com.example.demo.exceptions.RecursoNoEncontrado;
import com.example.demo.repositories.DetalleVentaRepository;
import com.example.demo.services.interfaces.IDetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping("/detalle-venta")
public class DetalleVentaController {

    @Autowired
    private IDetalleVentaService detalleVentaService;
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;


    @GetMapping("/buscar")
    public ResponseEntity<List<DetalleVentaDTO>> buscarPorParametros(
            @RequestParam(required = false)  LocalDate fecha,
            @RequestParam(required = false) String sucursal) {

        List<DetalleVentaDTO> resultados = detalleVentaService.buscarPorParametros(fecha, sucursal);

        return ResponseEntity.ok(resultados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPorId(@PathVariable Long id) {
        DetalleVenta detalle = detalleVentaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("No se encontró el detalle con ID: " + id));

        detalleVentaRepository.delete(detalle);
        return ResponseEntity.ok("Detalle eliminado exitosamente");
    }





}
