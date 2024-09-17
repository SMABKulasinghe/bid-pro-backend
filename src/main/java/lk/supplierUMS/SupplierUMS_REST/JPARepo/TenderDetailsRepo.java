package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.FinacialResponsesVendor;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.User;

@Component
public interface TenderDetailsRepo extends JpaRepository<TenderDetails,Long>{

	@Query("SELECT ten FROM TenderDetails ten WHERE ten.status = '2' AND ten.approvalstatus ='1'  AND ( CAST( ten.trnderid as string ) LIKE %?1% OR ten.bidno LIKE %?1% OR ten.tenderdescription LIKE %?1% OR ten.openingdate LIKE %?1% OR ten.closingdate LIKE %?1%)")
	Page<TenderDetails> findAllTender(String parameter, PageRequest of);
	
	@Query("SELECT ten, rfp, ecat, esub, epro FROM TenderDetails ten "
			+ "INNER JOIN RFP rfp ON ten.rfpId = rfp.rfpID "
			+ "INNER JOIN EligibleCategory ecat ON ten.eligibleCategortID = ecat.eligibleCategortID "
			+ "INNER JOIN EligibleSubCategory esub ON ten.eligibleSubcatId = esub.eligibleSubcatId "
			+ "INNER JOIN EligibleSubCategoryProducts epro ON ten.eligiblesubcatProdid = epro.eligiblesubcatProdid "
			+ "WHERE ten.status = '2' AND ten.approvalstatus ='1' "
			+ "AND ( CAST( ten.trnderid as string ) LIKE %?1% OR ten.bidno LIKE %?1% OR ten.tendername LIKE %?1% OR ten.trnderid LIKE %?1% OR ten.tenderdescription LIKE %?1% OR ten.openingdate LIKE %?1% OR ten.closingdate LIKE %?1%)")
	Page<Object[]> findAllTendersForTenderAuth(String parameter, PageRequest of);
	
	
	
	@Query("SELECT ten, spd, ts FROM TenderDetails ten INNER JOIN SupplierProductDetails spd ON ten.eligibleCategortID = spd.categoryID INNER JOIN TenderStatusDetails ts ON ts.tender_status_id = ten.status  WHERE ten.status = '4' AND ten.approvalstatus ='3' AND spd.supplierID = ?2 AND ( CAST( ten.trnderid as string ) LIKE %?1% OR ten.trnderid LIKE %?1% OR ten.tenderdescription LIKE %?1% OR ten.openingdate LIKE %?1% OR ten.closingdate LIKE %?1%) GROUP BY ten.trnderid ")
    Page<Object[]> findAllViewTender(String parameter, Long supplierID, PageRequest of);
	
	@Query("SELECT ten, spd, ts FROM TenderDetails ten INNER JOIN SupplierProductDetails spd ON ten.eligibleCategortID = spd.categoryID INNER JOIN TenderStatusDetails ts ON ts.tender_status_id = ten.status  WHERE ten.closingdate>=?4 AND ten.closingDateTime>=?3 AND ten.status = '4' AND ten.approvalstatus ='3' AND spd.supplierID = ?2 AND ( CAST( ten.trnderid as string ) LIKE %?1% OR ten.tenderdescription LIKE %?1% OR ten.openingdate LIKE %?1% OR ten.closingdate LIKE %?1%) GROUP BY ten.trnderid ")
    Page<Object[]> findAllViewTender(String parameter, Long supplierID,LocalDateTime NowDateAndTimee,LocalDate nowDate, PageRequest of);
	
	@Query("SELECT ten, ecat, esub,epro FROM TenderDetails ten "
			+ "INNER JOIN EligibleCategory ecat ON ten.eligibleCategortID = ecat.eligibleCategortID "
			+ "INNER JOIN EligibleSubCategory esub ON ten.eligibleSubcatId = esub.eligibleSubcatId "
			+ "INNER JOIN EligibleSubCategoryProducts epro ON ten.eligiblesubcatProdid = epro.eligiblesubcatProdid "
			+ "WHERE ten.status = '3'"
			+ "AND ( CAST( ten.trnderid as string ) LIKE %?1% OR ten.bidno LIKE %?1% OR ten.trnderid LIKE %?1% OR ten.tenderdescription LIKE %?1%)")
	Page<Object[]> getTenderDetailsForTenderOpen(String parameter, PageRequest of);

	//Optional <User> findByBidnoAndSupplierID(String bidNo, Long SupplierID);
	Boolean existsByBidno(String bidNo);
	
	//@Query("SELECT ten,spd,ts FROM TenderDetails ten INNER JOIN SupplierProductDetails spd ON ten.eligibleCategortID = spd.categoryID AND ten.eligibleSubcatId = spd.subCategoryID AND ten.eligiblesubcatProdid = spd.productID INNER JOIN TenderSubmissions ts ON ts.supplierId = spd.supplierID WHERE ten.status = '3' AND spd.supplierID = ?2 AND ten.bidno LIKE %?1% AND ten.eligibleCategortID = spd.categoryID AND ten.eligibleSubcatId = spd.subCategoryID AND ten.eligiblesubcatProdid = spd.productID")
	//@Query("SELECT ten,spd,ts FROM TenderDetails ten INNER JOIN SupplierProductDetails spd ON ten.eligibleCategortID = spd.categoryID INNER JOIN TenderSubmissions ts ON spd.supplierID = ts.supplierId WHERE ten.status = '4' AND ts.supplierId NOT IN (?2) AND (ten.bidno LIKE %?1%)")
	//GROUP BY ten.trnderid
	@Query("SELECT ten,spd,ts FROM TenderDetails ten INNER JOIN SupplierProductDetails spd ON ten.eligibleCategortID = spd.categoryID INNER JOIN TenderSubmissions ts ON spd.supplierID = ts.supplierId WHERE ten.closingDateTime>=?4 AND ten.closingdate>=?3 AND ten.status = '4' AND ts.tenderResponse = '5' AND ts.supplierId = ?2 AND ten.trnderid = ts.tenderNo AND (ten.bidno LIKE %?1% OR ten.tendername LIKE %?1% OR ten.tenderdescription LIKE %?1% OR ten.closingDateTime LIKE %?1%) GROUP BY ten.trnderid")
	Page<Object[]> getTenderDetailsForSupplier(String parameter, Long userCompanyID, LocalDate nowDate, LocalDateTime NowDateAndTimee, PageRequest of);
	

/*	@Query("SELECT cd, sub FROM ContractDetails cd "
			+ "INNER JOIN CompanySupplierMapping csm ON cd.mappingCode = csm.mappingID "
			+ "INNER JOIN SubCompany sub ON csm.supplierID = sub.subCompanyID "
			+ "WHERE cd.contractApprovalStatus IN ('P') "
			+ "AND csm.companyID = ?2 "
			+ "AND (sub.scname LIKE %?1% OR cd.contractNo LIKE %?1% OR cd.boardApprovalDate LIKE %?1% OR cd.contractAmount LIKE %?1% OR cd.category LIKE %?1%)")
	*/
	//@Query("SELECT td, tsd FROM TenderDetails td INNER JOIN TenderStatusDetails tsd ON td.status = tsd.tender_status_id AND (td.status LIKE %?1%)")
	//Page<Object[]> getAllTendersForCompany(String parameter, PageRequest of);
	
	@Query("SELECT td, rfp, ecat, esub, tsd FROM TenderDetails td "
			+ "INNER JOIN RFP rfp ON td.rfpId = rfp.rfpID "
			+ "INNER JOIN EligibleCategory ecat ON td.eligibleCategortID = ecat.eligibleCategortID "
			+ "INNER JOIN EligibleSubCategory esub ON td.eligibleSubcatId = esub.eligibleSubcatId "
			+ "INNER JOIN TenderStatusDetails tsd ON td.status = tsd.tender_status_id "
			+ "AND ( CAST( td.trnderid as string ) LIKE %?1% OR td.bidno LIKE %?1% OR td.trnderid LIKE %?1% OR td.tendername LIKE %?1% OR td.tenderdescription LIKE %?1% OR td.openingdate LIKE %?1% OR td.closingdate LIKE %?1%)")
	Page<Object[]> getAllTendersForCatergory(String parameter, PageRequest of);
			
		
	@Query("SELECT td, spm, subc FROM TenderDetails td "
			+ "INNER JOIN SupplierProductDetails spm ON td.eligibleSubcatId = spm.subCategoryID " 
			+ "INNER JOIN SubCompany subc ON subc.subCompanyID = spm.supplierID "
			+ "WHERE td.trnderid = ?2 "
			+ "AND (CAST ( td.trnderid as string ) LIKE %?1%) GROUP BY subc.subCompanyID")
	Page<Object[]> getEligibleSuppliersForACategoryANDTenderID(String search, Long tenderID, Pageable page);
	
	@Query("SELECT subc FROM TenderDetails td "
			+ "INNER JOIN SupplierProductDetails spm ON td.eligibleCategortID = spm.categoryID "
			+ "INNER JOIN SubCompany subc ON subc.subCompanyID = spm.supplierID "
			+ "WHERE td.trnderid = ?1 GROUP BY subc.subCompanyID ")
	List<SubCompany> getEligibleSuppliersForACategoryANDTenders(Long tenderID);
	
	//@Query("SELECT cd, sub FROM ContractDetails cd INNER JOIN CompanySupplierMapping csm ON cd.mappingCode = csm.mappingID INNER JOIN SubCompany sub ON csm.supplierID = sub.subCompanyID WHERE cd.contractApprovalStatus IN ('P') AND csm.companyID = ?2 AND (sub.scname LIKE %?1% OR cd.contractNo LIKE %?1% OR cd.boardApprovalDate LIKE %?1% OR cd.contractAmount LIKE %?1% OR cd.category LIKE %?1%)")
	@Query("SELECT td, tsd FROM TenderDetails td INNER JOIN TenderStatusDetails tsd ON td.status = tsd.tender_status_id AND (td.status LIKE %?1%)")
	Page<Object[]> getAllTendersForCompany(String parameter, PageRequest of);

	@Query("SELECT td,ec FROM TenderDetails td INNER JOIN EligibleCategory ec ON td.eligibleCategortID = ec.eligibleCategortID WHERE td.trnderid = ?1")
	List<Object[]> getTenderDetailsForView(Long tenderID);

	

	/*
	 * Page<Object[]> getTenderDetailsForSupplier(String parameter, Long
	 * userCompanyID, LocalDateTime nowDateAndTime, PageRequest of);
	 */

	

	@Query("SELECT td FROM TenderDetails td WHERE td.status = '4'")
	List<TenderDetails> getTenderId();
	
	@Query("SELECT ten FROM TenderDetails ten INNER JOIN TenderSubmissions ts ON ts.tenderNo = ten.trnderid WHERE ts.supplierId=?1  AND ts.tenderResponse = 8")
	List<TenderDetails> getTenderForFinancialDetails(Long supplierID);
	
	@Query("SELECT td, ecat, esub, ts, rfp FROM TenderDetails td "
			+ "INNER JOIN EligibleCategory ecat ON td.eligibleCategortID = ecat.eligibleCategortID "
			+ "INNER JOIN EligibleSubCategory esub ON td.eligibleSubcatId = esub.eligibleSubcatId "
			+ "INNER JOIN TenderSubmissions ts ON td.trnderid = ts.tenderNo "
			+ "INNER JOIN RFP rfp ON td.rfpId = rfp.rfpID "
			+ "WHERE td.trnderid = ?1 AND ts.supplierId =?2")
	List<Object[]> getTenderDetailsForRelatedTenderId(Long tenderID, Long SupplierID);


	@Query("SELECT td FROM TenderDetails td INNER JOIN TenderSubmissions ts ON td.trnderid = ts.tenderNo WHERE ts.tenderResponse = '8' GROUP BY td.trnderid")
	List<TenderDetails> getSubmittedTender();

	
	@Query("SELECT td, ecat, esub, ts FROM TenderDetails td "
			+ "INNER JOIN EligibleCategory ecat ON td.eligibleCategortID = ecat.eligibleCategortID "
			+ "INNER JOIN EligibleSubCategory esub ON td.eligibleSubcatId = esub.eligibleSubcatId "
			+ "INNER JOIN TenderSubmissions ts ON td.trnderid = ts.tenderNo "
			+ "WHERE td.trnderid = ?1 AND ts.supplierId =?2")
	List<Object[]> getTenderDetailsForFinancialCode(Long tenderID, Long SupplierID);
	@Query("SELECT td,ema from TenderDetails td INNER JOIN EvaluationMarksAll ema ON td.trnderid = ema.tenderId GROUP BY ema.tenderId ORDER BY ema.aveMarks DESC")
	List<Object[]> getTenderIdsForEvaluationSummery();

	@Query("SELECT ten,spd,ts FROM TenderDetails ten INNER JOIN SupplierProductDetails spd ON ten.eligibleCategortID = spd.categoryID INNER JOIN TenderSubmissions ts ON spd.supplierID = ts.supplierId WHERE ten.closingDateTime>=?4 AND ten.closingdate>=?3 AND ten.status = '4' AND ts.supplierId = ?2 AND ten.trnderid = ts.tenderNo AND(ten.bidno LIKE %?1%)")
	Page<Object[]> getTenderDetailsForSupplierView(String parameter, Long userCompanyID, LocalDate nowDate,
			LocalDateTime nowDateAndTimee, PageRequest of);
	
	@Query("SELECT ten,spd,ts FROM TenderDetails ten INNER JOIN SupplierProductDetails spd ON ten.eligibleCategortID = spd.categoryID INNER JOIN TenderSubmissions ts ON spd.supplierID = ts.supplierId WHERE ten.closingDateTime>=?4 AND ten.closingdate>=?3 AND ten.status = '4' AND ts.supplierId = ?2 AND ten.trnderid = ts.tenderNo AND(ten.bidno LIKE %?1%) GROUP BY ts.tenderNo")
	Page<Object[]> getTenderDetailsForDetailsViewAll(String parameter, Long userCompanyID, LocalDate nowDate,
			LocalDateTime nowDateAndTimee, PageRequest of);
	
	@Query("SELECT td FROM TenderDetails td INNER JOIN TenderSubmissions ts ON td.trnderid = ts.tenderNo WHERE ts.tenderResponse = '8' AND td.status = 15 GROUP BY td.trnderid")
	List<TenderDetails> getTenderIDForFinancialCodeCreation();

	
	//@Query("SELECT fvpt,fdpt,fc FROM FinancialValuesPerTender fvpt INNER JOIN FinancialDetailsPerTender fdpt ON fvpt.financialPerTenderParamID = fdpt.id INNER JOIN FinancialCodes fc ON fdpt.financialCodeId = fc.financialCodeId WHERE fvpt.cappex = 'Yes' AND fvpt.tenderId = ?2 AND fvpt.supplierId = ?3 AND(fvpt.currency LIKE %?1% OR fvpt.amount LIKE %?1% OR fvpt.comment LIKE %?1% OR fc.description LIKE %?1%)")
	@Query("SELECT vpt, dpt, fc FROM FinancialValuesPerTender vpt INNER JOIN FinancialDetailsPerTender dpt ON vpt.financialPerTenderParamID = dpt.id INNER JOIN FinancialCodes fc ON dpt.financialCodeId = fc.financialCodeId WHERE vpt.cappex = 'Yes' AND vpt.tenderId = ?2 AND vpt.supplierId = ?3 AND (vpt.currency LIKE %?1% OR vpt.amount LIKE %?1% OR vpt.comment LIKE %?1% OR fc.description LIKE %?1%)")
	Page<Object[]> getFinancialDetailsForCappex(String parameter, Long tenderId, Long supplierId, PageRequest of);

	@Query("SELECT vpt, dpt, fc FROM FinancialValuesPerTender vpt INNER JOIN FinancialDetailsPerTender dpt ON vpt.financialPerTenderParamID = dpt.id INNER JOIN FinancialCodes fc ON dpt.financialCodeId = fc.financialCodeId WHERE vpt.cappex = 'No' AND vpt.tenderId = ?2 AND vpt.supplierId = ?3 AND (vpt.currency LIKE %?1% OR vpt.amount LIKE %?1% OR vpt.comment LIKE %?1% OR fc.description LIKE %?1%)")
	Page<Object[]> getFinancialDetailsForOppex(String parameter, Long tenderId, Long supplierId, PageRequest of);

	@Query("SELECT frv FROM FinacialResponsesVendor frv WHERE frv.tenderID = ?1 AND frv.subCompanyCode = ?2")
	Optional<FinacialResponsesVendor> getFinancialDetailsForCappexOppex(Long tenderId, Long supplierId);

	Boolean existsByCordinator1contactno(String ordinator1Contactno);

	Boolean existsByCordinator2email(String cordinator2Email);

	Boolean existsByCordinator2contactno(String cordinator2Contactno);

	Boolean existsByCordinator1email(String cordinator1Email);
	
	@Query("SELECT td FROM TenderDetails td WHERE td.status = '1' OR td.status = '2' OR td.status = '3' GROUP BY td.trnderid")
	List<TenderDetails> getTenderForFinancialPerTender();

	@Query("SELECT fc FROM FinancialCodes fc WHERE fc.status = '3' GROUP BY fc.financialCodeId")
	List<FinancialCodes> getFinancialForFinancialPerTender();
	
	@Query("SELECT fc,fd,td FROM FinancialCodes fc INNER JOIN FinancialDetailsPerTender fd ON fc.financialCodeId = fd.financialCodeId INNER JOIN TenderDetails td ON td.trnderid = fd.tenderID WHERE fc.status = '3' AND fd.tenderID = ?2 AND (fc.code LIKE %?1% OR td.bidno LIKE %?1% OR td.tendername LIKE %?1%)")
	Page<Object[]> financialPerTenderView(String parameter, Long tenderId, PageRequest of);

	@Query("SELECT td FROM TenderDetails td WHERE td.status = '4' ")
	List<TenderDetails> getViewForTenderDetails(); //WHERE td.trnderid = ?1

	@Query("SELECT td FROM TenderDetails td WHERE td.status = '4' ")
	List<TenderDetails> getViewEveluationEditSheet();

	@Query("SELECT td FROM TenderDetails td WHERE td.closingDateTime<=?1 ")
	List<TenderDetails> getTendersForProcCommitteCreation(LocalDateTime nowDateAndTimee);

	@Query("SELECT m FROM User m WHERE m.companyCode= ?1")
	List<User> getTendersForProcCommitteeMembers(String userCompanyID);

	@Query("SELECT td, rfp, ecat, esub, tsd FROM TenderDetails td "
			+ "INNER JOIN RFP rfp ON td.rfpId = rfp.rfpID "
			+ "INNER JOIN EligibleCategory ecat ON td.eligibleCategortID = ecat.eligibleCategortID "
			+ "INNER JOIN EligibleSubCategory esub ON td.eligibleSubcatId = esub.eligibleSubcatId "
			+ "INNER JOIN TenderStatusDetails tsd ON td.status = tsd.tender_status_id "
			+ "AND ( CAST( td.trnderid as string ) LIKE %?1% OR td.bidno LIKE %?1% OR td.trnderid LIKE %?1% OR td.tendername LIKE %?1% OR td.tenderdescription LIKE %?1% OR td.openingdate LIKE %?1% OR td.closingdate LIKE %?1%) WHERE td.rfpId = ?2")
	Page<Object[]> getAllTenderDetails(String parameter, Long rfpId, PageRequest of);

	@Query("SELECT td FROM TenderDetails td WHERE td.status=4 OR td.status=10 OR td.status=16")
	List<TenderDetails> getTenderIdForRfpChangeId();

	
	@Query("SELECT td FROM TenderDetails td WHERE td.status IN ('4','15')")
	List<TenderDetails> getTenderExtendForTenderDetails();

	@Query("SELECT td FROM TenderDetails td WHERE td.status = '15' ")
	List<TenderDetails> getViewFinancialForDetails();

	@Query("SELECT vpt, dpt, fc FROM FinancialValuesPerTender vpt INNER JOIN FinancialDetailsPerTender dpt ON vpt.financialPerTenderParamID = dpt.id INNER JOIN FinancialCodes fc ON dpt.financialCodeId = fc.financialCodeId WHERE vpt.cappex = 'Yes' AND vpt.tenderId = ?2 AND vpt.supplierId = ?3 AND (vpt.currency LIKE %?1% OR vpt.amount LIKE %?1% OR vpt.comment LIKE %?1% OR fc.description LIKE %?1%)")
	Page<Object[]> getViewFinancialDetailsForCappex(String parameter, Long tenderId, Long supplierId, PageRequest of);

	@Query("SELECT vpt, dpt, fc FROM FinancialValuesPerTender vpt INNER JOIN FinancialDetailsPerTender dpt ON vpt.financialPerTenderParamID = dpt.id INNER JOIN FinancialCodes fc ON dpt.financialCodeId = fc.financialCodeId WHERE vpt.cappex = 'No' AND vpt.tenderId = ?2 AND vpt.supplierId = ?3 AND (vpt.currency LIKE %?1% OR vpt.amount LIKE %?1% OR vpt.comment LIKE %?1% OR fc.description LIKE %?1%)")
	Page<Object[]> getViewFinancialDetailsForOppex(String parameter, Long tenderId, Long supplierId, PageRequest of);

	@Query("SELECT frv FROM FinacialResponsesVendor frv WHERE frv.tenderID = ?1 AND frv.subCompanyCode = ?2")
	Optional<FinacialResponsesVendor> getViewFinancialDetailsForCappexOppex(Long tenderId, Long supplierId);

	@Query("SELECT td FROM TenderDetails td WHERE td.status = '4'")
	List<TenderDetails> getTenderDetailsForAuthorize();

	@Query("SELECT td,ema from TenderDetails td INNER JOIN EvaluationMarksAll ema ON ema.tenderId = td.trnderid WHERE td.closingDateTime>=?1 OR td.status=15 GROUP BY td.trnderid")
	List<Object[]> getResubmitFinancialDetailsAllowOption(LocalDateTime dateTime);

	@Query("SELECT sc,ema from SubCompany sc INNER JOIN EvaluationMarksAll ema ON sc.subCompanyID = ema.CompanyCode WHERE ema.tenderId=?1")
	List<Object[]> getResubmitFinancialDetailsAllowOptionSupplier(Long tenderId);

	@Query("SELECT ts,td from TenderSubmissions ts INNER JOIN TenderDetails td ON ts.tenderNo = td.trnderid WHERE td.closingDateTime<=?1 AND ts.tenderResponse = 8 GROUP BY ts.tenderNo")
	List<Object[]> closedTenders(LocalDateTime dateTime);

	@Query("SELECT taf,td,rfp FROM AdditionalFileTenderForSupplier taf INNER JOIN TenderDetails td ON td.trnderid = taf.tenderId INNER JOIN RFP rfp ON rfp.rfpID = td.rfpId WHERE taf.supplierId =?2 AND (td.bidno LIKE %?1% OR td.tendername LIKE %?1% OR rfp.rfpNo LIKE %?1% OR rfp.fileName LIKE %?1%)")
	Page<Object[]> getSupplierAdditinalFileUpload(String parameter, Long companyCode, PageRequest of);

	@Query("SELECT taf,sc FROM AdditionalFileTenderForSupplier taf INNER JOIN SubCompany sc ON sc.subCompanyID = taf.supplierId WHERE taf.tenderId =?2 AND (sc.scname LIKE %?1%)")
	Page<Object[]> getSupplierAdditinalFileDownload(String parameter, Long tenderId, PageRequest of);

	//
	

	

	
	
	//ORDER BY em.createdUser ASC
}
