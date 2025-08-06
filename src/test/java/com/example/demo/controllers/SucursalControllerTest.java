package com.example.demo.controllers;

import com.example.demo.dtos.SucursalDTO;
import com.example.demo.responses.StandardResponse;
import com.example.demo.services.interfaces.ISucursalService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


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