package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.GlobalDetails;
@Component
public interface PglogRepo extends JpaRepository<GlobalDetails, String> {

	
//	Pglob findByIndex(String id);
	GlobalDetails findByIndexNo(String id);	
}
