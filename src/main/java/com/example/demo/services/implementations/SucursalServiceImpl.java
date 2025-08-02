package com.example.demo.services.implementations;

import com.example.demo.dtos.SucursalDTO;
import com.example.demo.entities.Sucursal;
import com.example.demo.exceptions.RecursoNoEncontrado;
import com.example.demo.mappers.SucursalMapper;
import com.example.demo.repositories.SucursalRepository;
import com.example.demo.services.interfaces.ISucursalService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SucursalServiceImpl implements ISucursalService {

    private final SucursalRepository repository;

    public SucursalServiceImpl(SucursalRepository repository) {
        this.repository = repository;
    }

    @Override
    public SucursalDTO create(SucursalDTO dto) {
        Sucursal entity = SucursalMapper.toEntity(dto);
        return SucursalMapper.toDTO(repository.save(entity));
    }

    @Override
    public SucursalDTO update(Long id, SucursalDTO dto) {
        Sucursal existing = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Sucursal no encontrada con ID: " + id));
        existing.setNombre(dto.getNombre());
        existing.setDireccion(dto.getDireccion());
        existing.setCiudad(dto.getCiudad());
        return SucursalMapper.toDTO(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<SucursalDTO> findAll() {
        return repository.findAll().stream()
                .map(SucursalMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SucursalDTO findById(Long id) {
        return repository.findById(id)
                .map(SucursalMapper::toDTO)
                .orElseThrow(() -> new RecursoNoEncontrado("Sucursal no encontrada con ID: " + id));
    }
}