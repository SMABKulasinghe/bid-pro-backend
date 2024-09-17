package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.Re_issuePO;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;


@Component
public interface ReIssuePoRepo extends JpaRepository<Re_issuePO, Long> {

	@Query("SELECT td,ts,fr FROM TenderDetails td "
			+ "INNER JOIN TenderSubmissions ts ON td.trnderid = ts.tenderNo "
			+ "INNER JOIN FinacialResponsesVendor fr ON fr.tenderID = td.trnderid "
			+ "WHERE td.status = '15' GROUP BY td.trnderid")
	List<Object[]> getTenderDetailsForReIssuePo();
	
	@Query("SELECT td,ec,ts,sc,fr FROM TenderDetails td "
		//	+ "INNER JOIN RFP rfp ON td.rfpId = rfp.rfpID "
			+ "INNER JOIN EligibleCategory ec ON td.eligibleCategortID = ec.eligibleCategortID "
			+ "INNER JOIN TenderSubmissions ts ON td.trnderid = ts.tenderNo "
			+ "INNER JOIN SubCompany sc ON sc.subCompanyID = ts.supplierId "
			+ "INNER JOIN FinacialResponsesVendor fr ON fr.tenderID = td.trnderid "
		//	+ "INNER JOIN BoardPaperUpload bpu ON td.trnderid = bpu.tenderId "
			+ "WHERE td.status = '15' AND td.trnderid = ?1 GROUP BY td.trnderid")
	List<Object[]> getTenderByIdDetailsForReIssuePo(Long id);
	
}
	