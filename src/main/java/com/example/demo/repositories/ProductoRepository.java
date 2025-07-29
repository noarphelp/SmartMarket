package com.example.demo.repositories;


import com.example.demo.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Producto findByNombre(String nombre);

}
