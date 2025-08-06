package com.example.demo.controllers;

import com.example.demo.dtos.ProductoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@DisplayName("Test Integración de Productos")
public class ProductoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String URL_ENDPOINT = "/api/producto";

    @Test
    @DisplayName("Buscar todos los productos")
    public void buscarTodosTest() throws Exception {
        mockMvc.perform(get(URL_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Consulta exitosa"))
                .andExpect(jsonPath("$.data", hasSize(greaterThan(0))));
    }

    @Test
    @DisplayName("Crear producto")
    public void crearProductoTest() throws Exception {
        ProductoDTO productoDTO = new ProductoDTO(null, "Mouse Gamer", "Tecnología", new BigDecimal("49.99"));

        mockMvc.perform(post(URL_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("El producto se a creado exitosamente.")));
    }

    @Test
    @DisplayName("Actualizar producto")
    public void actualizarProductoTest() throws Exception {
        Long idExistente = 1L;

        ProductoDTO productoActualizado = new ProductoDTO(idExistente, "Mouse Pro", "Tecnología", new BigDecimal("59.99"));

        mockMvc.perform(put(URL_ENDPOINT + "/" + idExistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoActualizado)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("El producto seleccionado se ha actualizado correctamente.")));
    }


    @Test
    @DisplayName("Eliminar producto")
    public void eliminarProductoTest() throws Exception {
        Long idExistente = 1L;


        mockMvc.perform(delete(URL_ENDPOINT + "/" + idExistente))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Producto eliminado exitosamente.")));
    }
}
