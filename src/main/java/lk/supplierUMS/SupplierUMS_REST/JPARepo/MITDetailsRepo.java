package lk.supplierUMS.SupplierUMS_REST.JPARepo;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationSheetCreate;
import lk.supplierUMS.SupplierUMS_REST.entity.MITDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;

@Component
public interface MITDetailsRepo extends JpaRepository<MITDetails, Long> {
	

	@Query("SELECT td FROM TenderDetails td WHERE td.status IN ('4', '8', '15')")
	List<TenderDetails> getMITDetailsForTenderDetails();

	@Query("SELECT td FROM TenderDetails td WHERE td.status IN ('4', '8', '15')")
	List<TenderDetails> getViewMITDetailsForTenderDetails();		
	
	@Query("SELECT mitd, td FROM MITDetails mitd INNER JOIN TenderDetails td ON mitd.tenderID = td.trnderid WHERE mitd.tenderID = ?2 AND (mitd.picuturePath LIKE %?1% OR mitd.filePath LIKE %?1% OR mitd.description LIKE %?1% )")
	Page<Object[]> getviewMitDetailsRelatedTenderName(String parameter, Long tenderID, PageRequest of);


}


