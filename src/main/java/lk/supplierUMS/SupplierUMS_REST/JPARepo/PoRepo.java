package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.EPoHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.PoDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.TermsAndConditions;

@Component
public interface PoRepo extends JpaRepository<EPoHeader, Long> {

	/*
	 * @Query("SELECT po FROM EPoHeader po  WHERE po.mStatus = '2' AND (po.mPoNo LIKE %?1% )"
	 * )
	 */
	//AND po.mPoNo LIKE %?1%
	@Query("SELECT pd,td,user,ts,rfp,sc FROM PoDetails pd INNER JOIN TenderDetails td ON pd.mTenderId = td.trnderid INNER JOIN User user ON pd.mSupplierCode = user.companyCode INNER JOIN TenderSubmissions ts ON ts.submission_id = pd.submissionId INNER JOIN RFP rfp ON rfp.rfpID = pd.mRfpId INNER JOIN SubCompany sc ON sc.subCompanyID = ts.supplierId WHERE ts.tenderEligibility = '16' GROUP BY td.trnderid")
	Page<Object[]> getPoAuthorizations(String search,  PageRequest of);
	//WHERE ts.tenderEligibility = '16' 
	
	@Query("SELECT po,sc FROM EPoHeader po INNER JOIN SubCompany sc ON po.mSupplierCode = sc.subCompanyID WHERE po.mStatus = '3' AND po.mPoNo LIKE %?1%")
	Page<Object[]> getPoDetails(String search,  PageRequest of);

	@Query("SELECT td,ts,fr FROM TenderDetails td INNER JOIN TenderSubmissions ts ON td.trnderid = ts.tenderNo INNER JOIN FinacialResponsesVendor fr ON fr.tenderID = td.trnderid WHERE ts.tenderEligibility = '10' GROUP BY td.trnderid")
	List<Object[]> getTenderDetails();

	@Query("SELECT td,rfp,ec,ts,sc,fr,bpu FROM TenderDetails td INNER JOIN RFP rfp ON td.rfpId = rfp.rfpID INNER JOIN EligibleCategory ec ON td.eligibleCategortID = ec.eligibleCategortID INNER JOIN TenderSubmissions ts ON td.trnderid = ts.tenderNo INNER JOIN SubCompany sc ON sc.subCompanyID = ts.supplierId INNER JOIN FinacialResponsesVendor fr ON fr.tenderID = td.trnderid INNER JOIN BoardPaperUpload bpu ON td.trnderid = bpu.tenderId WHERE ts.tenderEligibility = '10' AND td.trnderid = ?1 GROUP BY td.trnderid")
	List<Object[]> getTenderDetailsForPo(Long id);

	@Query("SELECT pd,td,user,ts,rfp,sc FROM PoDetails pd INNER JOIN TenderDetails td ON pd.mTenderId = td.trnderid INNER JOIN User user ON pd.mSupplierCode = user.companyCode INNER JOIN TenderSubmissions ts ON ts.submission_id = pd.submissionId INNER JOIN RFP rfp ON rfp.rfpID = pd.mRfpId INNER JOIN SubCompany sc ON sc.subCompanyID = ts.supplierId GROUP BY td.trnderid")
	Page<Object[]> getPoDetailsForTable(String parameter, PageRequest of);
	//WHERE ts.tenderEligibility = '16' 
	
	@Query("SELECT pd,td,user,ts,rfp,sc FROM PoDetails pd INNER JOIN TenderDetails td ON pd.mTenderId = td.trnderid INNER JOIN User user ON pd.mSupplierCode = user.companyCode INNER JOIN TenderSubmissions ts ON ts.submission_id = pd.submissionId INNER JOIN RFP rfp ON rfp.rfpID = pd.mRfpId INNER JOIN SubCompany sc ON sc.subCompanyID = ts.supplierId WHERE ts.tenderEligibility = '17' OR ts.tenderEligibility = '18' AND (td.tendername LIKE %?1% ) AND pd.mSupplierCode = ?2")
	Page<Object[]> getPoDetailsForSupplierTable(String parameter, String userCompanyID, PageRequest of);

	@Query("SELECT tc,tct FROM TermsAndConditions tc INNER JOIN TermsAndConditionsTrans tct ON tc.termsAndConditionsId = tct.termsConditionsId WHERE tct.tenderId = ?2 AND (tc.termsConditions LIKE %?1% )")
	Page<Object[]> getTermsAndConditionsForTable(String parameter, Long tenderId, PageRequest of);

	@Query("SELECT tc FROM TermsAndConditions tc")
	Page<TermsAndConditions> viewTermsAndConditions(String parameter, PageRequest of);
	

	
}
