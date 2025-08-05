package com.example.demo.controllers;

import com.example.demo.dtos.SucursalDTO;
import com.example.demo.responses.StandardResponse;
import com.example.demo.services.interfaces.ISucursalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ISucursalService sucursalService;

    @InjectMocks
    private SucursalController sucursalController;


    @Test
    void testListarSucursales() throws Exception {

        SucursalDTO dto = new SucursalDTO(1L, "Sucursal Centro", "Calle Falsa 123", "Springfield", null);
        when(sucursalService.findAll()).thenReturn(Collections.singletonList(dto));


        ResponseEntity<StandardResponse<List<SucursalDTO>>> response = sucursalController.findAll();


        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        List<SucursalDTO> data = response.getBody().getData();
        assertEquals(1, data.size());
        assertEquals("Sucursal Centro", data.get(0).getNombre());
        assertEquals("Springfield", data.get(0).getCiudad());
    }
}