package com.example.demo.services.implementations;

import com.example.demo.dtos.DetalleVentaDTO;

import com.example.demo.dtos.DetalleVentaInputDTO;
import com.example.demo.entities.DetalleVenta;
import com.example.demo.entities.Producto;
import com.example.demo.repositories.DetalleVentaRepository;
import com.example.demo.repositories.ProductoRepository;
import com.example.demo.services.interfaces.IDetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        return detalles.stream()
                .map(detalle -> {
                    Producto producto = detalle.getProducto();
                    return new DetalleVentaDTO(
                            detalle.getId(),
                            LocalDate.now(),
                            detalle.getCantidad(),
                            producto.getNombre(),
                            "Sucursal ejemplo");
                })
                            .collect(Collectors.toList());

    }
    public static DetalleVenta toEntity(DetalleVentaInputDTO dto, Producto producto) {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidad(dto.getCantidad());
        return detalle;
    }

    public static DetalleVentaDTO toDTO(DetalleVenta detalle) {
        return new DetalleVentaDTO(
                detalle.getId(),
                LocalDate.now(),
                detalle.getCantidad(),
                detalle.getProducto().getNombre(),
                "Sucursal ejemplo"
        );
    }

}

