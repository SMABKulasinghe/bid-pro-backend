package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.City;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationMarksAll;
import lk.supplierUMS.SupplierUMS_REST.entity.ProcurementCommittee;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;

@Component
public interface ProcurementCommitteeRepo extends JpaRepository<ProcurementCommittee, Long>{
	
	//INNER JOIN TenderSubmissions ts ON ts.vendorId = pc.supplierId 
	//INNER JOIN TenderSubmissions ts ON ts.tenderNo = pc.tenderId 
	@Query("SELECT pc,td,sc,ts FROM ProcurementCommittee pc INNER JOIN TenderDetails td ON pc.tenderId = td.trnderid INNER JOIN SubCompany sc ON sc.subCompanyID = pc.supplierId INNER JOIN TenderSubmissions ts ON ts.tenderNo = pc.tenderId WHERE ts.tenderResponse ='8' AND pc.tenderId = ?2 AND pc.userId = ?3 AND(td.bidno LIKE %?1% OR sc.scname LIKE %?1%) GROUP BY pc.procComId")
	Page<Object[]> getVotingDetailsForVoting(String parameter, Long id, String userId, PageRequest of);

	@Query("SELECT eve FROM EvaluationMarksAll eve WHERE eve.tenderId = ?1 AND eve.CompanyCode = ?2")
	Optional<EvaluationMarksAll> getEmpDetailsForVoting(long tenderid, String supplierStr);

	@Query("SELECT ts FROM TenderSubmissions ts WHERE ts.tenderNo = ?1 AND ts.tenderResponse = 8")
	List<TenderSubmissions> getTenderDetailsOfSupplier(Long valueOf);
	
	@Query("SELECT pc,td,user,sc FROM ProcurementCommittee pc INNER JOIN TenderDetails td ON pc.tenderId = td.trnderid INNER JOIN User user ON pc.userId = user.userid INNER JOIN SubCompany sc ON sc.subCompanyID = pc.supplierId WHERE pc.tenderId = ?2 AND (td.bidno LIKE %?1% OR td.tendername LIKE %?1% OR sc.scname LIKE %?1%)")
	Page<Object[]> getVotingDetails(String parameter, Long id, PageRequest of);

	@Query("SELECT pc FROM ProcurementCommittee pc WHERE pc.tenderId = ?1 AND pc.userId = ?2")
	List<ProcurementCommittee> getProcComDetailsList(long tenderid, String userId);

	Boolean existsByUserIdAndTenderId(String committeeMember, Long procTenderid);
	
}
