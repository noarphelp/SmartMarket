package com.example.demo.services.implementations;

import com.example.demo.dtos.ProductoDTO;
import com.example.demo.entities.Producto;
import com.example.demo.exceptions.RecursoNoEncontrado;
import com.example.demo.mappers.ProductoMapper;
import com.example.demo.repositories.ProductoRepository;

import com.example.demo.services.interfaces.IProductoService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {
    private final ProductoRepository repository;
    private final ProductoMapper mapper;

    public ProductoService(ProductoRepository repository, ProductoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<ProductoDTO> findAll() {
        List<Producto> productos = repository.findAll();
        return productos.stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Override
    public Optional<ProductoDTO> findById(Long id) {
        Optional<Producto> existente = repository.findById(id);
        if (!existente.isEmpty()) {
            return repository.findById(id)
                    .map(this::convertirADTO);
        }
        throw new RecursoNoEncontrado("Producto con ID " + id + " no encontrado.");
    }

    @Override
    public void create(ProductoDTO productoDTO) {
        Producto producto = convertirAOBJ(productoDTO);
        repository.save(producto);
    }

    @Override
    public ProductoDTO update(ProductoDTO productoDTO, Long id) {
        Optional<Producto> existente = repository.findById(id);
        if (existente.isEmpty()) {
            throw new RecursoNoEncontrado("Producto con ID " + id + " no encontrado.");
        }

        Producto producto = convertirAOBJ(productoDTO);
        producto.setId(id);
        repository.save(producto);
        return convertirADTO(producto);
    }

    @Override
    public void delete(Long id) {

        repository.deleteById(id);

    }

    //Mapeadores
    @Override
    public ProductoDTO convertirADTO(Producto producto) {
        return mapper.toADTO(producto);
    }

    @Override
    public Producto convertirAOBJ(ProductoDTO productoDTO) {

        return mapper.toEntity(productoDTO);
    }
}
