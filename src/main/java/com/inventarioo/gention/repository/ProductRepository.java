package com.inventarioo.gention.repository;

import com.inventarioo.gention.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 1. Buscar todos los productos que tengan un stock menor al número indicado (Alerta de inventario bajo)
    // SQL generado: SELECT * FROM productos WHERE stock < ?
    List<Product> findByStockLessThan(Integer cantidadMinima);

    // 2. Buscar todos los productos que pertenecen a un proveedor específico usando su ID
    // SQL generado: SELECT * FROM productos WHERE proveedor_id = ?
    List<Product> findBySupplierId(Long supplierId);
}
