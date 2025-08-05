package com.example.demo.services.implementations;

import com.example.demo.dtos.SucursalDTO;
import com.example.demo.entities.Sucursal;
import com.example.demo.exceptions.RecursoNoEncontrado;
import com.example.demo.mappers.SucursalMapper;
import com.example.demo.repositories.SucursalRepository;
import com.example.demo.services.interfaces.ISucursalService;
import com.example.demo.services.interfaces.IVentaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalService implements ISucursalService {

    private final SucursalRepository repository;
    private final IVentaService iVs;
    private final SucursalMapper mapper;

    public SucursalService(SucursalRepository repository, IVentaService iVs, SucursalMapper mapper) {
        this.repository = repository;
        this.iVs = iVs;
        this.mapper = mapper;
    }

    @Override
    public List<SucursalDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Override
    public Optional<SucursalDTO> findById(Long id) {
        return repository.findById(id)
                .map(this::convertirADTO);
    }

    @Override
    public void create(SucursalDTO sucursalDTO) {
        Sucursal sucursal = convertirAOBJ(sucursalDTO);
        repository.save(sucursal);
    }

    @Override
    public SucursalDTO update(SucursalDTO sucursalDTO, Long id) {
        Optional<Sucursal> existente = repository.findById(id);
        if (existente.isEmpty()) {
            throw new RecursoNoEncontrado("Sucursal con ID " + id + " no encontrada.");
        }

        Sucursal sucursal = convertirAOBJ(sucursalDTO);
        sucursal.setId(id);
        repository.save(sucursal);
        return convertirADTO(sucursal);
    }

    @Override
    public void delete(Long id) {

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RecursoNoEncontrado("Sucursal con ID " + id + " no encontrada.");
        }
    }


    //Mapeadores
    @Override
    public SucursalDTO convertirADTO(Sucursal sucursal) {
        return mapper.toDTO(sucursal, iVs::convertirADTO);
    }

    @Override
    public Sucursal convertirAOBJ(SucursalDTO sucursalDTO) {
        return mapper.toEntity(sucursalDTO, iVs::convertirAOBJ);
    }

}
