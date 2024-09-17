package lk.supplierUMS.SupplierUMS_REST;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*@SpringBootApplication
@EnableJpaAuditing
public class SupplierUMSRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupplierUMSRestApplication.class, args);
	}

}
*/

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SupplierUMSRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupplierUMSRestApplication.class, args);
	}

}