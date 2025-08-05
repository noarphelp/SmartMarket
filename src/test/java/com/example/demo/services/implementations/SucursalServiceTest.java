package com.example.demo.services.implementations;

import com.example.demo.dtos.SucursalDTO;
import com.example.demo.services.interfaces.ISucursalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SucursalServiceTest {

    @Autowired
    private ISucursalService sucursalService;

    @Test
    void testCreateAndFind() {
        SucursalDTO dto = new SucursalDTO();
        dto.setNombre("Sucursal Test");
        dto.setDireccion("Calle Test 123");
        dto.setCiudad("Ciudad Test");

        sucursalService.create(dto);

        List<SucursalDTO> sucursales = sucursalService.findAll();

        SucursalDTO found = sucursales.stream()
                .filter(s -> "Sucursal Test".equals(s.getNombre()))
                .findFirst()
                .orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getDireccion()).isEqualTo("Calle Test 123");
        assertThat(found.getCiudad()).isEqualTo("Ciudad Test");

        if (found != null) {
            Optional<SucursalDTO> opt = sucursalService.findById(found.getId());
            assertThat(opt).isPresent();
            SucursalDTO byId = opt.get();
            assertThat(byId.getNombre()).isEqualTo("Sucursal Test");
        }
    }
}