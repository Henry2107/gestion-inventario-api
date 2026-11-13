package com.inventarioo.gention.service;



import com.inventarioo.gention.model.Supplier;
import com.inventarioo.gention.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    // Inyección de dependencias por constructor (Buena práctica moderna)
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    // 1. Obtener todos los proveedores
    public List<Supplier> listarTodos() {
        return supplierRepository.findAll();
    }

    // 2. Buscar un proveedor por su ID
    public Optional<Supplier> buscarPorId(Long id) {
        return supplierRepository.findById(id);
    }

    // 3. Guardar un nuevo proveedor aplicando una regla de negocio
    public Supplier guardarProveedor(Supplier supplier) {
        // Regla: Validar que el NIT no esté registrado previamente
        Optional<Supplier> existente = supplierRepository.findByNit(supplier.getNit());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un proveedor registrado con el NIT: " + supplier.getNit());
        }
        return supplierRepository.save(supplier);
    }

    // 4. Eliminar un proveedor
    public void eliminarProveedor(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró un proveedor con el ID: " + id);
        }
        supplierRepository.deleteById(id);
    }
}

