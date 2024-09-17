package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationAvarageMarkSheet;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationCommitee;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;

@Component
public interface EvaluationAvarageMarksRepo extends JpaRepository<EvaluationAvarageMarkSheet,Long>{
	
	Optional<EvaluationAvarageMarkSheet> findByCompanyCodeAndTenderIdAndEvaluationmarksheetID(String companyCode, Long tenderID, Long evemarkshe);
	
	EvaluationAvarageMarkSheet findFirstByTenderIdAndCompanyCode(Long tenderID, String companyCode);
	
	List<EvaluationAvarageMarkSheet> findAllByTenderIdAndCompanyCode(Long tenderID, String companyCode);
	
}
