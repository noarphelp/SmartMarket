package com.example.demo.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DetalleVentaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final String url = "/api/detalleVenta";

    //Test para verificar si funciona bien el método buscar toda la lista o por parámetros en controller.
    @Test
    void findByParameterTest() throws Exception {

        String fecha = "2025-07-15";
        String sucursal = "Sucursal Central";

        mockMvc.perform(get(url)
                        .param("fecha", fecha)
                        .param("sucursal", sucursal))
                .andExpect(status().isAccepted()) // 202
                .andExpect(jsonPath("$.data", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.data[0].nombreSucursal").value(sucursal))
                .andExpect(jsonPath("$.data[0].fecha").value(fecha));
    }

    //Test para verificar si funciona bien el método eliminar detalleVenta en el controller.

    @Test
    void deleteTest() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete(url + "/" + id))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Venta eliminada correctamente"));
    }
}
