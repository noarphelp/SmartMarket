package com.example.demo.services.implementations;

import com.example.demo.dtos.ProductoDTO;
import com.example.demo.entities.Producto;
import com.example.demo.repositories.ProductoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @InjectMocks
    private ProductoService service;

    @Mock
    private ProductoRepository repository;

    @Test
    @DisplayName(" Crear producto con BigDecimal")
    void crearProducto() {
        ProductoDTO dto = new ProductoDTO(null, "Arroz", "Granos", new BigDecimal("1.50"));
        Producto productoGuardado = new Producto(1L, "Arroz", "Granos", new BigDecimal("1.50"), new ArrayList<>());

        when(repository.save(any())).thenReturn(productoGuardado);

        ProductoDTO resultado = service.crearProducto(dto);

        assertThat(resultado.getPrecio()).isEqualByComparingTo("1.50");
    }

    @Test
    @DisplayName("✅ Actualizar producto con BigDecimal")
    void actualizarProducto() {
        Producto existente = new Producto(1L, "Leche", "Lácteos", new BigDecimal("1.20"), new ArrayList<>());
        ProductoDTO nuevo = new ProductoDTO(null, "Leche Descremada", "Lácteos", new BigDecimal("1.40"));

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any())).thenAnswer(invoc -> invoc.getArgument(0));

        ProductoDTO resultado = service.actualizarProducto(1L, nuevo);

        assertThat(resultado.getPrecio()).isEqualByComparingTo("1.40");
    }

    @Test
    @DisplayName("✅ Obtener producto con precio BigDecimal")
    void obtenerPorId() {
        Producto producto = new Producto(2L, "Café", "Bebidas", new BigDecimal("3.25"), new ArrayList<>());

        when(repository.findById(2L)).thenReturn(Optional.of(producto));

        ProductoDTO dto = service.obtenerProductoPorId(2L);

        assertThat(dto.getPrecio()).isEqualByComparingTo("3.25");
    }

    @Test
    @DisplayName("✅ Listar productos con BigDecimal")
    void obtenerTodos() {
        List<Producto> productos = Arrays.asList(
                new Producto(1L, "Pan", "Panadería", new BigDecimal("1.00"), new ArrayList<>()),
                new Producto(2L, "Jugo", "Bebidas", new BigDecimal("2.00") ,new ArrayList<>())
        );

        when(repository.findAll()).thenReturn(productos);

        List<ProductoDTO> resultado = service.obtenerTodosLosProductos();

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getPrecio()).isEqualByComparingTo("1.00");
        assertThat(resultado.get(1).getPrecio()).isEqualByComparingTo("2.00");
    }

    @Test
    @DisplayName("✅ Eliminar producto por ID")
    void eliminarProducto() {
        doNothing().when(repository).deleteById(1L);
        service.eliminarProducto(1L);
        verify(repository).deleteById(1L);
    }
}
