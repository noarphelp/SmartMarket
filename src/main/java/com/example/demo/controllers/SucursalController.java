package com.example.demo.controllers;


import com.example.demo.dtos.SucursalDTO;
import com.example.demo.responses.StandardResponse;
import com.example.demo.services.interfaces.ISucursalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sucursal")
public class SucursalController {

    private final ISucursalService sucursalService;

    public SucursalController(ISucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @GetMapping
    public ResponseEntity<StandardResponse<List<SucursalDTO>>> findAll() {
        List<SucursalDTO> list = sucursalService.finAll();

        if (!list.isEmpty())
            return ResponseEntity.ok(new StandardResponse<>("Consulta exitosa", list));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardResponse<>("Fallo al cargar lista de sucursales", null));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody SucursalDTO sucursalDTO) {

        if (sucursalDTO.getNombre().isEmpty() || sucursalDTO.getDireccion().isEmpty() || sucursalDTO.getCiudad().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fallo al crear una nueva sucursal. Los datos no pueden estar vacíos");
        }
        sucursalService.create(sucursalDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("La sucursal se a creado exitosamente.");

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody SucursalDTO sucursalDTO, @PathVariable Long id) {

        if (sucursalDTO.getNombre().isEmpty() || sucursalDTO.getDireccion().isEmpty() || sucursalDTO.getCiudad().isEmpty() || id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fallo al actualizar una sucursal. Los datos no pueden estar vacíos");
        }

        Optional<SucursalDTO> sucursalExistente = sucursalService.findById(id);
        if (sucursalExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La sucursal con ID " + id + " no existe.");
        }

        SucursalDTO actualizada = sucursalService.update(sucursalDTO, id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("La sucursal seleccionada se ha actualizado correctamente.\n" + actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Optional<SucursalDTO> sucursal = sucursalService.findById(id);
        if (sucursal.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se a podido eliminar la sucursal, o id invalido.");
        else {

            sucursalService.delete(id);
            return ResponseEntity.ok("Sucursal eliminada exitosamente.");
        }
    }
}
