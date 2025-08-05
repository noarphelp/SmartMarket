package com.example.demo.services.implementations;

import com.example.demo.dtos.ProductoDTO;
import com.example.demo.entities.DetalleVenta;
import com.example.demo.entities.Producto;
import com.example.demo.exceptions.RecursoNoEncontrado;
import com.example.demo.mappers.ProductoMapper;
import com.example.demo.repositories.ProductoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @InjectMocks
    private ProductoService service;

    @Mock
    private ProductoRepository repository;

    @Mock
    private ProductoMapper mapper;

    @Test
    @DisplayName("Buscar todos los productos")
    void findAllProductos() {
        List<Producto> productos = List.of(
                new Producto(1L, "Laptop", "Laptop potente", new BigDecimal("1200.0"), new ArrayList<>()),
                new Producto(2L, "Mouse", "Mouse ergonómico", new BigDecimal("25.0"), new ArrayList<>())
        );

        List<ProductoDTO> productosDTO = List.of(
                new ProductoDTO(1L, "Laptop", "Laptop potente", new BigDecimal("1200.0")),
                new ProductoDTO(2L, "Mouse", "Mouse ergonómico", new BigDecimal("25.0"))
        );


        when(repository.findAll()).thenReturn(productos);
        when(mapper.toADTO(productos.get(0))).thenReturn(productosDTO.get(0));
        when(mapper.toADTO(productos.get(1))).thenReturn(productosDTO.get(1));

        List<ProductoDTO> resultado = service.findAll();

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Laptop");
        assertThat(resultado.get(1).getPrecio()).isEqualByComparingTo(new BigDecimal("25.0"));
    }

    @Test
    @DisplayName("🔍 Buscar producto por ID existente")
    void findByIdExistente() {
        Long id = 1L;

        Producto producto = new Producto(id, "Teclado", "Teclado mecánico", new BigDecimal("45.0"), new ArrayList<DetalleVenta>()
        );

        ProductoDTO productoDTO = new ProductoDTO(id, "Teclado", "Teclado mecánico", new BigDecimal("45.0")
        );


        when(repository.findById(id)).thenReturn(Optional.of(producto));
        when(mapper.toADTO(producto)).thenReturn(productoDTO);

        Optional<ProductoDTO> resultado = service.findById(id);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Teclado");
    }

    @Test
    @DisplayName(" Buscar producto por ID inexistente")
    void findByIdNoExistente() {
        Long id = 99L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontrado.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Crear producto")
    void createProducto() {
        ProductoDTO dto = new ProductoDTO(null, "Monitor", "Monitor LED 24 pulgadas", new BigDecimal("300.0")
        );

        Producto producto = new Producto(null, "Monitor", "Monitor LED 24 pulgadas", new BigDecimal("300.0"), new ArrayList<>()
        );

        when(mapper.toEntity(dto)).thenReturn(producto);

        service.create(dto);

        verify(repository).save(producto);
    }

    @Test
    @DisplayName("Actualizar producto existente")
    void updateProductoExistente() {
        Long id = 1L;

        ProductoDTO dto = new ProductoDTO(null, "Tablet", "Tablet Android 10 pulgadas", new BigDecimal("250.0")
        );

        Producto producto = new Producto(id, "Tablet", "Tablet Android 10 pulgadas", new BigDecimal("250.0"), new ArrayList<>()
        );

        when(repository.findById(id)).thenReturn(Optional.of(new Producto()));
        when(mapper.toEntity(dto)).thenReturn(producto);
        when(mapper.toADTO(producto)).thenReturn(new ProductoDTO(id, "Tablet", "Tablet Android 10 pulgadas", new BigDecimal("250.0")));

        ProductoDTO actualizado = service.update(dto, id);

        assertThat(actualizado.getId()).isEqualTo(id);
        assertThat(actualizado.getNombre()).isEqualTo("Tablet");
    }

    @Test
    @DisplayName("🗑 Eliminar producto")
    void deleteProducto() {
        Long id = 2L;

        service.delete(id);

        verify(repository).deleteById(id);
    }
}
