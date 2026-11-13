package com.inventarioo.gention.service;

import com.inventarioo.gention.model.Product;
import com.inventarioo.gention.model.Supplier;
import com.inventarioo.gention.repository.ProductRepository;
import com.inventarioo.gention.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void actualizarStock_CuandoHaySuficienteInventario_DebeDescontarCorrectamente() {
        // 1. Preparación (Arrange): Simulamos un producto existente con stock de 10
        Supplier proveedor = new Supplier("Tech S.A.S", "900111222", "3000000", "Calle 1");
        Product productoExistente = new Product("Mouse", "Inalambrico", new BigDecimal("50000"), 10, proveedor);
        productoExistente.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(productoExistente));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. Ejecución (Act): Restamos 4 unidades
        Product resultado = productService.actualizarStock(1L, -4);

        // 3. Verificación (Assert): Comprobamos que el stock final sea 6
        assertEquals(6, resultado.getStock());
        verify(productRepository, times(1)).save(productoExistente);
    }

    @Test
    void actualizarStock_CuandoStockEsInsuficiente_DebeLanzarExcepcion() {
        // 1. Preparación (Arrange): Producto con solo 2 unidades
        Supplier proveedor = new Supplier("Tech S.A.S", "900111222", "3000000", "Calle 1");
        Product productoExistente = new Product("Mouse", "Inalambrico", new BigDecimal("50000"), 2, proveedor);
        productoExistente.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(productoExistente));

        // 2. Ejecución y 3. Verificación: Intentamos restar 5 unidades y esperamos un error
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            productService.actualizarStock(1L, -5);
        });

        assertTrue(excepcion.getMessage().contains("Stock insuficiente"));
        // Verificamos que NUNCA se haya guardado en la base de datos
        verify(productRepository, never()).save(any());
    }
}
