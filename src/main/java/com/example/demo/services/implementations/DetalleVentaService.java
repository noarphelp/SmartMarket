package com.example.demo.services.implementations;

import com.example.demo.dtos.DetalleVentaDTO;


import com.example.demo.entities.DetalleVenta;

import com.example.demo.exceptions.RecursoNoEncontrado;
import com.example.demo.repositories.DetalleVentaRepository;
import com.example.demo.repositories.ProductoRepository;
import com.example.demo.services.interfaces.IDetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleVentaService implements IDetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ProductoRepository productoRepository;


    @Override
    public List<DetalleVentaDTO> buscarPorParametros(LocalDate fecha, String sucursal) {
        List<DetalleVenta> detalles = detalleVentaRepository.findAll()
                .stream()
                .filter(detalle ->
                        (fecha == null || detalle.getVenta().getFecha().equals(fecha)) &&
                                (sucursal == null || detalle.getVenta().getSucursal().getNombre().toLowerCase().contains(sucursal.toLowerCase()))
                )
                .sorted(Comparator.comparing(detalle -> detalle.getVenta().getFecha()))
                .collect(Collectors.toList());

        if (detalles.isEmpty()) {
            throw new RecursoNoEncontrado("No se encontraron detalles con los parámetros proporcionados.");
        }

        return detalles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }



    @Override
    public void eliminarDetalleVenta(Long id) {
        DetalleVenta detalle = detalleVentaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("El detalle de venta con ID " + id + " no existe."));
        detalleVentaRepository.delete(detalle);
    }


    public DetalleVenta toEntity(DetalleVentaDTO detalleVenta) {
   return new DetalleVenta(detalleVenta.getId(), detalleVenta.getCantidad(), null ,null);

    }



    public DetalleVentaDTO toDTO(DetalleVenta detalleVenta) {
        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setId(detalleVenta.getId());
        dto.setFecha(detalleVenta.getVenta().getFecha());
        dto.setCantidad(detalleVenta.getCantidad());
        dto.setNombreProducto(detalleVenta.getProducto().getNombre());
        dto.setNombreSucursal(detalleVenta.getVenta().getSucursal().getNombre());
        return dto;
    }

}

