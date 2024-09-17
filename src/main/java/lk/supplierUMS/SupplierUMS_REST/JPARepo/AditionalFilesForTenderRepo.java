package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.AditionalFilesForTender;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.RFP;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetails;

@Component
public interface AditionalFilesForTenderRepo extends JpaRepository<AditionalFilesForTender, Long>{

	@Query("SELECT af FROM AditionalFilesForTender af WHERE af.tenderId=?2  AND (af.fileName LIKE %?1%)")
	Page<AditionalFilesForTender> aditionalDetailsForEditTble(String parameter, Long id, PageRequest of);

	Boolean existsByTenderIdAndFileName(Long tenderId, String fileName);
	
	@Query("SELECT af FROM AditionalFilesForTender af WHERE af.tenderId=?1 AND af.status='3' AND af.lockStatus='6'")
	List<AditionalFilesForTender> findByTenderIdAndStatus(Long tenderId);

	@Query("SELECT af FROM AditionalFilesForTender af WHERE af.tenderId=?2 AND af.status='3' AND (af.fileName LIKE %?1%)")
	Page<AditionalFilesForTender> aditionalDetailsForViewTble(String parameter, Long tenderId, PageRequest of);

	@Query("SELECT af FROM AditionalFilesForTender af WHERE af.tenderId=?1 AND af.status='3' AND af.lockStatus='23'")
	List<AditionalFilesForTender> getAdditionalFilesToPage(Long tenderId);
	
	
}
