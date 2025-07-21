package com.example.demo.controllers;

import com.example.demo.dtos.DetalleVentaDTO;
import com.example.demo.dtos.DetalleVentaInputDTO;
import com.example.demo.services.interfaces.IDetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/detalle-venta")
public class DetalleVentaController {

    @Autowired
    private IDetalleVentaService detalleVentaService;

    @PostMapping
    public ResponseEntity<DetalleVentaDTO> crearDetalle(@RequestBody DetalleVentaInputDTO inputDTO) {
        DetalleVentaDTO resultado = detalleVentaService.crearDetalleVenta(inputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }
}

