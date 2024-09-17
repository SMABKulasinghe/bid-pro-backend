package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.supplierUMS.SupplierUMS_REST.entity.RFPResponse;

public interface RFPResponseRepo extends JpaRepository<RFPResponse, Long>{

	boolean existsByTenderIdAndUserIdAndCompanyCode(String enteredValue, String userId, Long companyCode);

	//boolean existsBytenderIdAndUserIdAndCompanyCodeAndRfpId(String tenderIdString, String userId, String companyCode, String rfpId);

	Object existsBytenderIdAndUserIdAndCompanyCodeAndRfpId(String tenderIdString, String userId, Long companyCode,
			Long rfpId);
		
}
