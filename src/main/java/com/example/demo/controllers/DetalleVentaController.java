package com.example.demo.controllers;

import com.example.demo.dtos.DetalleVentaDTO;

import com.example.demo.exceptions.DatosInvalidos;
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

    @GetMapping
    public ResponseEntity<List<DetalleVentaDTO>> obtenerTodosLosDetalles() {
        List<DetalleVentaDTO> detalles = detalleVentaService.findAllDetallesVenta();
        if (detalles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.emptyList());
        }

        return ResponseEntity.ok(detalles);
    }
    @GetMapping("/buscar")
    public ResponseEntity<List<DetalleVentaDTO>> buscarPorParametros(
            @RequestParam(required = false) String producto,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Integer minCantidad,
            @RequestParam(required = false) List<String> sucursales,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
       //excepcion RecursoNoEncontrado esta en el servicio
        if (minCantidad != null && minCantidad < 0) {
            throw new DatosInvalidos("La cantidad mínima no puede ser negativa.");
        }

        List<DetalleVentaDTO> resultados = detalleVentaService.buscarPorParametros(
                producto, categoria, minCantidad, sucursales, fecha);

        return ResponseEntity.ok(resultados);
    }





    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPorId(@PathVariable Long id) {
        if (id <= 0) {
            throw new DatosInvalidos("ID inválido para eliminar.");
        }
        detalleVentaService.eliminarDetalleVenta(id);
        return ResponseEntity.ok("Detalle eliminado exitosamente");
    }


}
