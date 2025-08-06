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

    // Constructor que inyecta el repositorio y el mapper
    public ProductoService(ProductoRepository repository, ProductoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    //Obtiene todos los productos de la base de datos y los convierte a DTO
    @Override
    public List<ProductoDTO> findAll() {
        List<Producto> productos = repository.findAll();
        return productos.stream()
                .map(this::convertirADTO)
                .toList();
    }

    // Busca un producto por su ID si lo encuentra, lo convierte a DTO y lo devuelve si no, lanza una excepción personalizada
    @Override
    public Optional<ProductoDTO> findById(Long id) {
        Optional<Producto> existente = repository.findById(id);
        if (!existente.isEmpty()) {
            return repository.findById(id)
                    .map(this::convertirADTO);
        }
        throw new RecursoNoEncontrado("Producto con ID " + id + " no encontrado.");
    }

    //Crea un nuevo producto en la base de datos a partir de un DTO
    @Override
    public void create(ProductoDTO productoDTO) {
        Producto producto = convertirAOBJ(productoDTO);
        repository.save(producto);
    }

    //Actualiza un producto existente con nuevos datos Si el producto no existe, lanza una excepción, devuelve el DTO actualizado
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
    // Elimina un producto por su ID no lanza excepción si el ID no existe (puedes agregar validación si lo deseas)

    @Override
    public void delete(Long id) {

        repository.deleteById(id);

    }

    //Mappers
    //Convierte una entidad Producto a su correspondiente DTO
    @Override
    public ProductoDTO convertirADTO(Producto producto) {
        return mapper.toADTO(producto);
    }

    //Convierte un DTO Producto a su correspondiente entidad
    @Override
    public Producto convertirAOBJ(ProductoDTO productoDTO) {

        return mapper.toEntity(productoDTO);
    }
}
