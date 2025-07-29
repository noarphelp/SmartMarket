package com.example.demo.services.implementations;

import com.example.demo.dtos.DetalleVentaDTO;

import com.example.demo.dtos.DetalleVentaInputDTO;
import com.example.demo.entities.DetalleVenta;
import com.example.demo.entities.Producto;
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
    public List<DetalleVentaDTO> findAllDetallesVenta() {
        List<DetalleVenta> detalles = detalleVentaRepository.findAll();

        if (detalles == null || detalles.isEmpty()) {
            throw new RecursoNoEncontrado("No hay detalles de venta registrados.");
        }

        return detalles.stream()
                .map(detalle -> {
                    if (detalle == null || detalle.getProducto() == null || detalle.getVenta() == null) {
                        throw new IllegalArgumentException("Detalle de venta incompleto o nulo.");
                    }

                    return new DetalleVentaDTO(
                            detalle.getId(),
                            detalle.getCantidad(),
                            detalle.getProducto().getNombre(),
                            detalle.getVenta().getFecha(),
                            detalle.getVenta().getSucursal().getNombre()
                    );
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<DetalleVentaDTO> buscarPorParametros(LocalDate fecha, List<String> sucursales) {
        List<DetalleVenta> detalles = detalleVentaRepository.findAll()
                .stream()
                .filter(detalle ->
                        (fecha == null || detalle.getVenta().getFecha().equals(fecha)) &&
                                (sucursales == null || sucursales.stream()
                                        .map(String::toLowerCase)
                                        .anyMatch(s -> detalle.getVenta().getSucursal().getNombre().toLowerCase().contains(s)))
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
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido para eliminar.");
        }

        if (!detalleVentaRepository.existsById(id)) {
            throw new RecursoNoEncontrado("El detalle de venta con ID " + id + " no existe.");
        }

        detalleVentaRepository.deleteById(id);
    }


    public static DetalleVenta toEntity(DetalleVentaInputDTO dto, Producto producto) {
        if (dto == null || producto == null) {
            throw new IllegalArgumentException("Datos de entrada inválidos para crear detalle de venta.");
        }

        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidad(dto.getCantidad());
        return detalle;
    }

    public DetalleVentaDTO toDTO(DetalleVenta detalleVenta) {
        if (detalleVenta == null ||
                detalleVenta.getProducto() == null ||
                detalleVenta.getVenta() == null ||
                detalleVenta.getVenta().getSucursal() == null) {
            throw new IllegalArgumentException("Detalle de venta incompleto para transformar a DTO.");
        }

        return new DetalleVentaDTO(
                detalleVenta.getId(),
                detalleVenta.getVenta().getFecha(),
                detalleVenta.getCantidad(),
                detalleVenta.getProducto().getNombre(),
                detalleVenta.getVenta().getSucursal().getNombre()
        );
    }
}

