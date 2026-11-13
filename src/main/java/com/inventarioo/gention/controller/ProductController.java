package com.inventarioo.gention.controller;

import com.inventarioo.gention.model.Product;
import com.inventarioo.gention.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. GET: Listar todos los productos
    @GetMapping
    public ResponseEntity<List<Product>> listarTodos() {
        return ResponseEntity.ok(productService.listarTodos());
    }

    // 2. GET: Alerta de stock bajo (ej. http://localhost:8080/api/productos/stock-bajo?limite=5)
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Product>> listarStockBajo(@RequestParam(defaultValue = "5") Integer limite) {
        return ResponseEntity.ok(productService.obtenerStockBajo(limite));
    }

    // 3. POST: Registrar un nuevo producto asociado a un proveedor
    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Product product) {
        try {
            Product nuevoProducto = productService.guardarProducto(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 4. PATCH: Registrar entrada (+) o salida (-) de inventario
    // Ejemplo: PATCH http://localhost:8080/api/productos/1/stock?cantidad=-2
    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> actualizarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        try {
            Product productoActualizado = productService.actualizarStock(id, cantidad);
            return ResponseEntity.ok(productoActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
