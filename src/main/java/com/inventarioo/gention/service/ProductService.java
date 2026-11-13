package com.inventarioo.gention.service;

import com.inventarioo.gention.model.Product;
import com.inventarioo.gention.model.Supplier;
import com.inventarioo.gention.repository.ProductRepository;
import com.inventarioo.gention.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    // Inyectamos ambos repositorios porque ProductService necesita consultar proveedores
    public ProductService(ProductRepository productRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    // 1. Listar todos los productos
    public List<Product> listarTodos() {
        return productRepository.findAll();
    }

    // 2. Buscar producto por ID
    public Optional<Product> buscarPorId(Long id) {
        return productRepository.findById(id);
    }

    // 3. Consultar alerta de inventario bajo (ej. menos de 5 unidades)
    public List<Product> obtenerStockBajo(Integer limite) {
        return productRepository.findByStockLessThan(limite);
    }

    // 4. Guardar un producto validando su proveedor y datos básicos
    public Product guardarProducto(Product product) {
        // Validar que venga un proveedor con ID
        if (product.getSupplier() == null || product.getSupplier().getId() == null) {
            throw new IllegalArgumentException("El producto debe tener un proveedor asignado.");
        }

        // Buscar el proveedor real en la base de datos
        Long supplierId = product.getSupplier().getId();
        Supplier proveedorReal = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalArgumentException("No existe un proveedor con el ID: " + supplierId));

        // Validar precio y stock inicial
        if (product.getPrecio() == null || product.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor a cero.");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException("El stock inicial no puede ser negativo.");
        }

        // Asociamos el objeto completo del proveedor antes de guardar
        product.setSupplier(proveedorReal);
        return productRepository.save(product);
    }

    // 5. Actualizar stock (positivo para entradas/compras, negativo para salidas/ventas)
    public Product actualizarStock(Long productId, Integer cantidadMovimiento) {
        Product producto = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el producto con ID: " + productId));

        int nuevoStock = producto.getStock() + cantidadMovimiento;

        if (nuevoStock < 0) {
            throw new IllegalArgumentException("Stock insuficiente. Stock actual: " + producto.getStock());
        }

        producto.setStock(nuevoStock);
        return productRepository.save(producto);
    }
}
