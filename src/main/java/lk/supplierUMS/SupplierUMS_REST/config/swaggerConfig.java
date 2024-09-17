package lk.supplierUMS.SupplierUMS_REST.config;

import org.springframework.context.annotation.Configuration;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class swaggerConfig {

	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2);
	}
}
