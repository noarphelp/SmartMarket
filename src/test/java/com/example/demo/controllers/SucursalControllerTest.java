package com.example.demo.controllers;

import com.example.demo.dtos.SucursalDTO;
import com.example.demo.services.interfaces.ISucursalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ISucursalService sucursalService;

    @InjectMocks
    private SucursalController sucursalController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarSucursales() throws Exception {
        // Creo un DTO de ejemplo
        SucursalDTO dto = new SucursalDTO(1L, "Sucursal Centro", "Calle Falsa 123", "Springfield", null);

        // Simulo el comportamiento del servicio
        when(sucursalService.findAll()).thenReturn(Collections.singletonList(dto));

        // Hago la petición GET y valido la respuesta
        mockMvc.perform(get("/api/sucursales")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Sucursal Centro")))
                .andExpect(jsonPath("$[0].direccion", is("Calle Falsa 123")))
                .andExpect(jsonPath("$[0].ciudad", is("Springfield")));
    }
}