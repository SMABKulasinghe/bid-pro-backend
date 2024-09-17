package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.GlobalStatus;


@Component
public interface GlobalStatusRepo extends JpaRepository<GlobalStatus, Long> {
	
}
