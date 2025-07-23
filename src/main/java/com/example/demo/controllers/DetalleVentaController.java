package com.example.demo.controllers;

import com.example.demo.dtos.DetalleVentaDTO;

import com.example.demo.services.interfaces.IDetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
