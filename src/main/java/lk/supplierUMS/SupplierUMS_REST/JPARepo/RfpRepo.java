package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.RFP;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetails;

@Component
public interface RfpRepo extends JpaRepository<RFP, Long>{
	
	@Query("SELECT rfp,ec,dep FROM RFP rfp INNER JOIN EligibleCategory ec ON rfp.eligibleCatId = ec.eligibleCategortID INNER JOIN Department dep ON rfp.deptId = dep.deptId WHERE rfp.status ='2' AND (rfp.rfpNo LIKE %?1% OR rfp.createdDateTime LIKE %?1% OR rfp.fileName LIKE %?1% OR rfp.createdUser LIKE %?1% OR ec.eligibleCategortName LIKE %?1% OR dep.description LIKE %?1%) ORDER BY rfp.rfpID DESC")
	Page<Object[]> getRfpResponses(String search, PageRequest of);
	
	//boolean existsByRfpNo(String rfpNo);
	
	@Query("SELECT rfp FROM RFP rfp WHERE rfp.status ='3'")
	List<RFP>findByStatus(Long status);
	
	boolean existsByRfpNo(String rfpNo);

	@Query("SELECT rfp,td FROM RFP rfp INNER JOIN TenderDetails td ON rfp.tenderId = td.trnderid WHERE rfp.status ='3' AND (rfp.rfpNo LIKE %?1%)")
	Page<Object[]> getRfpUpdates(String parameter, PageRequest of);

	@Query("SELECT rfp,td,rr FROM RFP rfp INNER JOIN TenderDetails td ON rfp.tenderId = td.trnderid INNER JOIN RFPResponse rr ON rfp.rfpID = rr.rfpId WHERE rr.companyCode =?2 AND rr.status = 'Submitted' AND rfp.status ='3' AND (rfp.rfpNo LIKE %?1% OR td.bidno LIKE %?1% OR rfp.fileName LIKE %?1% OR td.tenderdescription LIKE %?1% OR td.closingdate LIKE %?1% )")
	Page<Object[]> getRfpView(String parameter, Long CompanyCode, PageRequest of);
	
	List<RFP> findByEligibleCatIdAndStatusAndEligibleSubCatId(Long categoryID, String staus,Long subCatId);
	
	@Query("SELECT rfp FROM RFP rfp WHERE (rfp.rfpNo LIKE %?1% OR rfp.fileName LIKE %?1% OR rfp.createdDateTime LIKE %?1% OR rfp.createdUser LIKE %?1%) ORDER BY rfp.rfpID DESC")
	Page<RFP> getRfpViewForComputer(String parameter, PageRequest of);
	
	@Query("SELECT fc FROM FinancialCodes fc WHERE fc.status = '3' OR fc.status = '4'")
	Page<FinancialCodes> getFinancialCodeView(String parameter, PageRequest of);

	@Query("SELECT rfp FROM RFP rfp WHERE rfp.status = '3'")
	List<RFP> rfpIdForRevisedRfpSubmittion();

	@Query("SELECT rfpH, rfpD from RFPHeader rfpH INNER JOIN RFPDetails rfpD ON rfpH.rfpHId = rfpD.rfpHId WHERE rfpH.rfpId = ?2 AND (rfpH.rfpHeaderName LIKE %?1%)")
	Page<Object[]> getRfpDetailsForEditTable(String parameter, long id, PageRequest of);
}
