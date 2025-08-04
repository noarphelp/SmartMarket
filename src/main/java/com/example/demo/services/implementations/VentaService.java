package com.example.demo.services.implementations;

import com.example.demo.dtos.DetalleVentaInputDTO;
import com.example.demo.dtos.VentaDTO;
import com.example.demo.entities.DetalleVenta;
import com.example.demo.entities.Producto;
import com.example.demo.entities.Sucursal;
import com.example.demo.entities.Venta;
import com.example.demo.exceptions.RecursoNoEncontrado;
import com.example.demo.mappers.VentaMapper;
import com.example.demo.repositories.ProductoRepository;
import com.example.demo.repositories.SucursalRepository;
import com.example.demo.repositories.VentaRepository;
import com.example.demo.services.interfaces.ISucursalService;
import com.example.demo.services.interfaces.IVentaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService implements IVentaService {

    private final VentaRepository repository;
    private final SucursalRepository sucursalRepository;
    private final ProductoRepository productoRepository;
    private final VentaMapper mapper;

    public VentaService(VentaRepository repository, SucursalRepository sucursalRepository, ProductoRepository productoRepository, VentaMapper mapper) {
        this.repository = repository;
        this.sucursalRepository = sucursalRepository;
        this.productoRepository = productoRepository;
        this.mapper = mapper;
    }


    @Override
    public List<VentaDTO> faindAll() {
        return repository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Transactional
    @Override
    public void create(DetalleVentaInputDTO dto) {
        Sucursal sucursal = sucursalRepository.findById(dto.getSucursalId().longValue())
                .orElseThrow(() -> new RecursoNoEncontrado("Sucursal no encontrada con ID: " + dto.getSucursalId()));

        Venta venta = new Venta(null, LocalDate.now(), sucursal, new ArrayList<>());
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RecursoNoEncontrado("Producto no encontrado con ID: " + dto.getProductoId()));

        DetalleVenta detalle = new DetalleVenta();
        detalle.setCantidad(dto.getCantidad());
        detalle.setProducto(producto);
        detalle.setVenta(venta);

        venta.getDetalleVentas().add(detalle);
        repository.save(venta);
    }


    @Override
    public void delete(Long id) {

        Optional<Venta> venta = repository.findById(id);
        if (!venta.isEmpty())
            repository.deleteById(id);
        else
            throw new RecursoNoEncontrado("El id introducido no se encuentra en la base de datos");


    }

    //Mapeadores
    @Override
    public VentaDTO convertirADTO(Venta venta) {

        return mapper.toDTO(venta);
    }

    @Override
    public Venta convertirAOBJ(VentaDTO venta) {
        return mapper.toEntity(venta);
    }
}
