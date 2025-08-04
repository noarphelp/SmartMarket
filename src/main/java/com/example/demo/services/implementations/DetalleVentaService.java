package com.example.demo.services.implementations;

import com.example.demo.dtos.DetalleVentaDTO;
import com.example.demo.entities.DetalleVenta;
import com.example.demo.exceptions.RecursoNoEncontrado;
import com.example.demo.mappers.DetalleVentaMapper;
import com.example.demo.repositories.DetalleVentaRepository;
import com.example.demo.services.interfaces.IDetalleVentaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetalleVentaService implements IDetalleVentaService {

    private final DetalleVentaMapper mapper;
    private final DetalleVentaRepository repository;

    public DetalleVentaService(DetalleVentaMapper mapper, DetalleVentaRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public List<DetalleVentaDTO> faindAll() {

        return repository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }


    @Override
    public List<DetalleVentaDTO> findByParameter(LocalDate fecha, String sucursal) {
        List<DetalleVentaDTO> list = faindAll();

        return list.stream()
                .filter(l -> fecha == null || l.getFecha().equals(fecha))
                .filter(s -> sucursal == null || s.getNombreSucursal().toLowerCase().equals(sucursal.toLowerCase()))
                .sorted(Comparator.comparing(DetalleVentaDTO::getFecha))
                .collect(Collectors.toList());
    }


    @Override
    public void delete(Long id) {

        Optional<DetalleVenta> venta = repository.findById(id);
        if (!venta.isEmpty())
            repository.deleteById(id);
        else
            throw new RecursoNoEncontrado("El id introducido no se encuentra en la base de datos");


    }

    //Mappers
    @Override
    public DetalleVentaDTO convertirADTO(DetalleVenta detalleVenta) {
        return mapper.toADTO(detalleVenta);
    }

    @Override
    public DetalleVenta convertirAOBJ(DetalleVentaDTO detalleVenta) {
        return mapper.toEntity(detalleVenta);
    }
}
