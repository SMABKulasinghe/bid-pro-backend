package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.SupplierDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SupplierProductDetails;

@Component
public interface SupplirDetailsRepo extends JpaRepository<SupplierDetails, Long> {

	void save(SupplierProductDetails supProductDetails);
	
	Optional<SupplierDetails> findBySubCompanyID(Long supplierID);

	Optional<SupplierDetails> findBysubCompanyIDAndSupplierID(Long subCompanyID, Long supplierID);

}
