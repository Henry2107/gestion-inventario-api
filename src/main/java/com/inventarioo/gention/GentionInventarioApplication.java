package com.inventarioo.gention;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "API de Gestión de Inventarios y Proveedores",
				version = "1.0",
				description = "Sistema Backend REST para el control de stock, alertas de inventario y proveedores."
		)
)
public class GentionInventarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(GentionInventarioApplication.class, args);
	}

}
