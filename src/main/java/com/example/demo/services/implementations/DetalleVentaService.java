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
    // Constructor para inyectar el mapper y el repositorio
    private final DetalleVentaMapper mapper;
    private final DetalleVentaRepository repository;

    public DetalleVentaService(DetalleVentaMapper mapper, DetalleVentaRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    //obtener todos los registros y los convierte en DTO
    public List<DetalleVentaDTO> findAll() {

        return repository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    //filtrar los detalles de venta por fecha y/o sucursal si los parámetros nulos no se aplican los filtros lo contrario se ordena por fecha
    @Override
    public List<DetalleVentaDTO> findByParameter(LocalDate fecha, String sucursal) {
        List<DetalleVentaDTO> list = findAll();

        return list.stream()
                .filter(l -> fecha == null || l.getFecha().equals(fecha))
                .filter(s -> sucursal == null || s.getNombreSucursal().toLowerCase().equals(sucursal.toLowerCase()))
                .sorted(Comparator.comparing(DetalleVentaDTO::getFecha))
                .collect(Collectors.toList());
    }

    //eliminar detalle vente por su iD si no se encuentra lanza la excepción
    @Override
    public void delete(Long id) {

        Optional<DetalleVenta> venta = repository.findById(id);
        if (!venta.isEmpty())
            repository.deleteById(id);
        else
            throw new RecursoNoEncontrado("El id introducido no se encuentra en la base de datos");


    }

    //Mappers
    //Convierte una entidad DetalleVenta a su correspondiente DTO
    @Override
    public DetalleVentaDTO convertirADTO(DetalleVenta detalleVenta) {
        return mapper.toADTO(detalleVenta);
    }

    //Convierte un DTO DetalleVenta a su correspondiente entidad
    @Override
    public DetalleVenta convertirAOBJ(DetalleVentaDTO detalleVenta) {
        return mapper.toEntity(detalleVenta);
    }
}
