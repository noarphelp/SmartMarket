package com.example.demo.controllers;

import com.example.demo.dtos.DetalleVentaInputDTO;
import com.example.demo.dtos.VentaDTO;
import com.example.demo.responses.StandardResponse;
import com.example.demo.services.interfaces.IVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venta")
public class VentaController {

    private final IVentaService ventaService;

    public VentaController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }


    @PostMapping
    public ResponseEntity<String> create(@RequestBody DetalleVentaInputDTO dto) {

        ventaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Venta creada exitosamente");

    }


}
