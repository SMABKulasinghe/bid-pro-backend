package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.AppzTenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.CompanySupplierMapping;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.User;

@Component
public interface AppzTenderSubmissionsRepo extends JpaRepository<TenderSubmissions,Long>{

	Optional<TenderSubmissions> findByTenderNoAndSupplierId(Long tenderId, Long supplierId);
	
	/*@Query("SELECT td, spm, subc FROM TenderDetails td "
			+ "INNER JOIN SupplierProductDetails spm ON td.eligibleCategortID = spm.categoryID "
			+ "INNER JOIN SubCompany subc ON subc.subCompanyID = spm.supplierID "
			+ "WHERE td.trnderid = ?2 "
			+ "AND (CAST ( td.trnderid as string ) LIKE %?1%)")
	*/
	//"INNER JOIN EligibleCategory ecat ON td.eligibleCategortID = ecat.eligibleCategortID "
	
	@Query("SELECT ts,subc FROM TenderSubmissions ts "
			+ "INNER JOIN SubCompany subc ON ts.supplierId = subc.subCompanyID "
			+ "WHERE ts.tenderNo = ?1 "
			+ "AND ts.confirmEligibleSupplier = ?2 ")
	List <Object[]> findByTenderNoAndConfirmEligibleSupplier(Long tenderID, String eligibility);
	
	
//	List <SubCompany> findByTenderNoAndConfirmEligibleSupplier(Long tenderID, String eligibility);
	//List <TenderSubmissions> findByTenderNoAndConfirmEligibleSupplier(Long tenderID, String eligibility);
	
	Boolean existsByTenderNoAndSupplierIdAndTenderResponseIn(long tenderID, Long supplierID, List<String> tenderResponse);

	
	//List<TenderSubmissions> getTenderIds(LocalDate nowDate);

	@Query("SELECT ts,user,sc from TenderSubmissions ts INNER JOIN User user ON ts.vendorId = user.companyCode INNER JOIN SubCompany sc ON sc.subCompanyID = ts.supplierId WHERE ts.tenderResponse = 8 AND ts.tenderNo = ?2 AND (sc.scname LIKE %?1% OR sc.scphoneno1 LIKE %?1% OR sc.scemailadmin LIKE %?1%) GROUP BY sc.subCompanyID")
	Page<Object[]> getTenderSubmissionDataForTender(String parameter, Long id, PageRequest of);
	
	@Query("SELECT ts,f_response,sc from TenderSubmissions ts"
			+ " INNER JOIN FinacialResponsesVendor f_response ON ts.supplierId = f_response.subCompanyCode "
			+ "INNER JOIN SubCompany sc ON sc.subCompanyID = ts.supplierId "
			+ "WHERE ts.tenderResponse = 8 AND f_response.tenderID = ?2 AND (sc.scname LIKE %?1% OR sc.scphoneno1 LIKE %?1% OR sc.scemailadmin LIKE %?1%) GROUP BY sc.subCompanyID")
	Page<Object[]> getTenderSubmissionDataForFinancialDocView(String parameter, Long id, PageRequest of);
	
	
	@Query("SELECT ts,td from TenderSubmissions ts INNER JOIN TenderDetails td ON ts.tenderNo = td.trnderid WHERE td.closingDateTime<=?1 AND ts.tenderResponse = 8 GROUP BY ts.tenderNo")
	List<Object[]> getTenderIds(LocalDateTime DateTime);

	
	
	@Query("SELECT sc from TenderSubmissions ts INNER JOIN SubCompany sc ON ts.vendorId = sc.subCompanyID WHERE ts.tenderResponse = 8 AND ts.tenderNo = ?1")
	List<SubCompany> getSubmittedVender(Long tenderID);

	@Query("SELECT ts FROM TenderSubmissions ts WHERE ts.tenderNo = ?1")
	List<TenderSubmissions> tenderSubmissionDetailsForDesqulified(Long tenderId);

	@Query("SELECT ts FROM TenderSubmissions ts WHERE ts.vendorId =?1 AND ts.tenderNo =?2")
	Optional<TenderSubmissions> findByVendorIdAndTenderNo(String companyCode, Long tenderId);

	@Query("SELECT ts,td from TenderSubmissions ts INNER JOIN TenderDetails td ON ts.tenderNo = td.trnderid WHERE td.closingDateTime>=?1 AND ts.tenderResponse = 8 GROUP BY ts.tenderNo")
	List<Object[]> getTenderDetailsIdsForTenderParticipators(LocalDateTime dateTime);

	@Query("SELECT ts,user,sc from TenderSubmissions ts INNER JOIN User user ON ts.vendorId = user.companyCode INNER JOIN SubCompany sc ON sc.subCompanyID = ts.supplierId WHERE ts.tenderResponse = 8 AND ts.tenderNo = ?2 AND (sc.scname LIKE %?1% OR sc.scphoneno1 LIKE %?1% OR sc.scemailadmin LIKE %?1%) GROUP BY sc.subCompanyID")
	Page<Object[]> getTenderDetailsForViewTableForParticipators(String parameter, Long id, PageRequest of);

	//@Query("SELECT ts,user,sc from TenderSubmissions ts INNER JOIN User user ON ts.vendorId = user.companyCode INNER JOIN SubCompany sc ON sc.subCompanyID = ts.supplierId WHERE ts.tenderResponse = 8 AND ts.tenderNo = ?2 AND (sc.scname LIKE %?1% OR sc.scphoneno1 LIKE %?1% OR sc.scemailadmin LIKE %?1%) GROUP BY sc.subCompanyID")

	//@Query("SELECT ts FROM TenderSubmissions ts WHERE ts.tenderNo =?1")
	
	
	//List<TenderSubmissions> getTenderIds();
	
	Optional<List<TenderSubmissions>> findAllByTenderNoAndTenderResponse(Long tenderID, String tenderResponse);

	@Query("SELECT ts,user,sc from TenderSubmissions ts INNER JOIN User user ON ts.vendorId = user.companyCode INNER JOIN SubCompany sc ON sc.subCompanyID = ts.supplierId WHERE ts.tenderResponse = 8 AND ts.tenderNo = ?2 AND (sc.scname LIKE %?1% OR sc.scphoneno1 LIKE %?1% OR sc.scemailadmin LIKE %?1%) GROUP BY sc.subCompanyID")
	Page<Object[]> getTenderDetailsForFinancialView(String parameter, Long tendrID, PageRequest of);

	List<TenderSubmissions> findByTenderNoAndTenderResponse(Long tenderId, String string);


}
