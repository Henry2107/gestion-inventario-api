package com.inventarioo.gention.controller;


import com.inventarioo.gention.model.Supplier;
import com.inventarioo.gention.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // 1. GET: Listar todos los proveedores (http://localhost:8080/api/proveedores)
    @GetMapping
    public ResponseEntity<List<Supplier>> listarTodos() {
        List<Supplier> proveedores = supplierService.listarTodos();
        return ResponseEntity.ok(proveedores);
    }

    // 2. GET por ID: Buscar un proveedor específico (http://localhost:8080/api/proveedores/1)
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> obtenerPorId(@PathVariable Long id) {
        return supplierService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. POST: Crear un nuevo proveedor
    @PostMapping
    public ResponseEntity<?> crearProveedor(@RequestBody Supplier supplier) {
        try {
            Supplier nuevoProveedor = supplierService.guardarProveedor(supplier);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProveedor);
        } catch (IllegalArgumentException e) {
            // Si el NIT ya existe, devolvemos un error 400 (Bad Request) con el mensaje claro
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 4. DELETE: Eliminar un proveedor por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProveedor(@PathVariable Long id) {
        try {
            supplierService.eliminarProveedor(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

