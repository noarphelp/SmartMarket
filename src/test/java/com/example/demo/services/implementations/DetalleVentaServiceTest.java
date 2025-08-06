package com.example.demo.services.implementations;

import com.example.demo.dtos.DetalleVentaDTO;
import com.example.demo.entities.DetalleVenta;
import com.example.demo.exceptions.RecursoNoEncontrado;
import com.example.demo.mappers.DetalleVentaMapper;
import com.example.demo.repositories.DetalleVentaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DetalleVentaServiceTest {
    @InjectMocks
    private DetalleVentaService detalleVentaService;
    @Mock
    private DetalleVentaRepository detalleVentaRepository;
    @Mock
    DetalleVentaMapper mappers;

    //Test para verificar si funciona bien el método de listar detalleVenta en service.
    @Test
    @DisplayName("Listar todas las ventas")
    void findAllTest() {
        List<DetalleVenta> list = List.of(new DetalleVenta(1L, 21, null, null));

        List<DetalleVentaDTO> listDTO = List.of(new DetalleVentaDTO(1L, LocalDate.now(), 21, null, null));

        when(detalleVentaRepository.findAll()).thenReturn(list);
        when(mappers.toADTO(list.get(0))).thenReturn(listDTO.get(0));

        List<DetalleVentaDTO> result = detalleVentaService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCantidad().equals(21));
        assertThat(result.get(0).getId().equals(1L));

    }

    //Test para verificar si funciona bien el método eliminar detalleVenta en el service.
    @Test
    @DisplayName("Eliminar detalleVenta")
    void deleteTest() {
        Long id = 1L;
        DetalleVenta detalle = new DetalleVenta();

        when(detalleVentaRepository.findById(id)).thenReturn(Optional.of(detalle));

        detalleVentaService.delete(id);

        verify(detalleVentaRepository).deleteById(id);
    }

    //Test para comprobar si lanza el mensaje de excepción al no encontrar el id en base de datos.
    @Test
    @DisplayName("Lanza RecursoNoEncontrado si no existe el detalleVenta a eliminar")
    void deleteNotFoundTest() {
        Long id = 99L;

        // Simulamos que no se encuentra el ID
        when(detalleVentaRepository.findById(id)).thenReturn(Optional.empty());

        // Verificamos que lanza la excepción esperada
        RecursoNoEncontrado ex = assertThrows(
                RecursoNoEncontrado.class,
                () -> detalleVentaService.delete(id)
        );

        assertEquals("El id introducido no se encuentra en la base de datos", ex.getMessage());


    }

}
