package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;



import lk.supplierUMS.SupplierUMS_REST.entity.FinacialResponsesVendor;

@Component
public interface FinancialResponseRepo extends JpaRepository<FinacialResponsesVendor, Long>{
	
	Optional<FinacialResponsesVendor> findBySubCompanyCodeAndTenderID(Long supplierSubComID, Long tenderID);

	Optional<FinacialResponsesVendor> findByTenderIDAndSubCompanyCode(Long tenderId, Long subCompId);

	void deleteByTenderIDAndSubCompanyCode(Long tenderId, Long subCompId);

	

}