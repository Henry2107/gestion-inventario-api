package com.inventarioo.gention.repository;


import com.inventarioo.gention.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    // ¡Mira esto! Solo declarando el nombre del método siguiendo las reglas de Spring,
    // el framework crea la consulta SQL: SELECT * FROM proveedores WHERE nit = ?
    Optional<Supplier> findByNit(String nit);
}
