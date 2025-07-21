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

@Service
public class DetalleVentaService implements IDetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public DetalleVentaDTO crearDetalleVenta(DetalleVentaInputDTO inputDTO) {
        Producto producto = productoRepository.findById(inputDTO.getProductoId()).orElseThrow();
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidad(inputDTO.getCantidad());
        detalle = detalleVentaRepository.save(detalle);
        return new DetalleVentaDTO(detalle.getId(), LocalDate.now(), detalle.getCantidad(), producto.getNombre(), "Sucursal ejemplo");
    }
}

