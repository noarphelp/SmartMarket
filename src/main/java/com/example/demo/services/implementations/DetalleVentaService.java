package com.example.demo.services.implementations;

import com.example.demo.dtos.DetalleVentaDTO;

import com.example.demo.dtos.DetalleVentaInputDTO;
import com.example.demo.entities.DetalleVenta;
import com.example.demo.entities.Producto;
import com.example.demo.entities.Sucursal;
import com.example.demo.entities.Venta;
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
    @Override
    public List<DetalleVentaDTO> buscarPorParametros(String nombreProducto, String categoria,
                                                     Integer cantidadMinima, List<String> sucursales, LocalDate fecha) {
        List<DetalleVenta> detalles = detalleVentaRepository.findAll()
                .stream()
                .filter(detalle -> {
                    Producto producto = detalle.getProducto();
                    Venta venta = detalle.getVenta();
                    Sucursal sucursal = venta.getSucursal();


                    boolean coincideNombre = nombreProducto == null ||
                            producto.getNombre().toLowerCase().contains(nombreProducto.toLowerCase());

                    boolean coincideCategoria = categoria == null ||
                            producto.getCategoria().toLowerCase().contains(categoria.toLowerCase());

                    boolean cantidadValida = cantidadMinima == null ||
                            detalle.getCantidad() >= cantidadMinima;

                    boolean coincideSucursal = sucursales == null || sucursales.stream()
                            .map(String::toLowerCase)
                            .anyMatch(s -> sucursal.getNombre().toLowerCase().contains(s));

                    boolean coincideFecha = fecha == null || venta.getFecha().equals(fecha);

                    return coincideNombre && coincideCategoria &&
                            cantidadValida && coincideSucursal && coincideFecha;
                })
                .sorted(Comparator.comparing(DetalleVenta::getCantidad))
                .collect(Collectors.toList());

        if (detalles.isEmpty()) {
            throw new RecursoNoEncontrado("No se encontraron detalles con los parámetros proporcionados.");
        }

        return detalles.stream()
                .map(detalle -> new DetalleVentaDTO(
                        detalle.getId(),
                        detalle.getVenta().getFecha(),
                        detalle.getCantidad(),
                        detalle.getProducto().getNombre(),
                        detalle.getVenta().getSucursal().getNombre()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarDetalleVenta(Long id) {
        if (!detalleVentaRepository.existsById(id)) {
            throw new RecursoNoEncontrado("El detalle de venta con ID " + id + " no existe.");
        }
        detalleVentaRepository.deleteById(id);
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

