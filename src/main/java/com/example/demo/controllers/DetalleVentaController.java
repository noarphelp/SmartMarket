package com.example.demo.controllers;

import com.example.demo.dtos.DetalleVentaDTO;

import com.example.demo.entities.DetalleVenta;
import com.example.demo.exceptions.DatosInvalidos;
import com.example.demo.exceptions.RecursoNoEncontrado;
import com.example.demo.services.interfaces.IDetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/detalle-venta")
public class DetalleVentaController {

    @Autowired
    private IDetalleVentaService detalleVentaService;

    @GetMapping("/buscar")
    public ResponseEntity<List<DetalleVentaDTO>> buscarPorParametros(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) List<String> sucursales) {

        List<DetalleVentaDTO> resultados = detalleVentaService.buscarPorParametros(fecha, sucursales);

        return ResponseEntity.ok(resultados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPorId(@PathVariable Long id) {
        if (id <= 0) {
            throw new DatosInvalidos("ID inválido para eliminar.");
        }

        try {
            detalleVentaService.eliminarDetalleVenta(id);
        } catch (RecursoNoEncontrado e) {
            throw new RecursoNoEncontrado("No se pudo eliminar, no se encontró el detalle con ID: " + id);
        }

        return ResponseEntity.ok("Detalle eliminado exitosamente");
    }




}
