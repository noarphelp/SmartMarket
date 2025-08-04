package com.example.demo.controllers;

import com.example.demo.dtos.DetalleVentaDTO;
import com.example.demo.responses.StandardResponse;
import com.example.demo.services.interfaces.IDetalleVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/detalleVenta")
public class DetalleVentaController {

    private final IDetalleVentaService detalleVentaService;

    public DetalleVentaController(IDetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }


    @GetMapping
    public ResponseEntity<StandardResponse<List<DetalleVentaDTO>>> findByParameter(@RequestParam(required = false) LocalDate fecha,
                                                                                   @RequestParam(required = false) String sucursal) {

        List<DetalleVentaDTO> list = detalleVentaService.findByParameter(fecha, sucursal);
        if (!list.isEmpty())
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new StandardResponse<>("Consulta exitosa", list));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardResponse<>("Parámetros incorrectos, o no encontrados", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        detalleVentaService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Venta eliminada correctamente");
    }
}
