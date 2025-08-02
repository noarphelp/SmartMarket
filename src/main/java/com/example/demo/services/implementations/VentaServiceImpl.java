package com.example.demo.services.implementations;

import com.example.demo.dtos.VentaDTO;
import com.example.demo.entities.Venta;
import com.example.demo.exceptions.RecursoNoEncontrado;
import com.example.demo.mappers.VentaMapper;
import com.example.demo.repositories.VentaRepository;
import com.example.demo.services.interfaces.IVentaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements IVentaService {

    private final VentaRepository repository;

    public VentaServiceImpl(VentaRepository repository) {
        this.repository = repository;
    }

    @Override
    public VentaDTO create(VentaDTO dto) {
        Venta entity = VentaMapper.toEntity(dto);
        return VentaMapper.toDTO(repository.save(entity));
    }

    @Override
    public VentaDTO update(Long id, VentaDTO dto) {
        Venta existing = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Venta no encontrada con ID: " + id));
        existing.setFecha(dto.getFecha());
        existing.setSucursal(VentaMapper.toEntity(dto).getSucursal());
        return VentaMapper.toDTO(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<VentaDTO> findAll() {
        return repository.findAll().stream()
                .map(VentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VentaDTO findById(Long id) {
        return repository.findById(id)
                .map(VentaMapper::toDTO)
                .orElseThrow(() -> new RecursoNoEncontrado("Venta no encontrada con ID: " + id));
    }
}