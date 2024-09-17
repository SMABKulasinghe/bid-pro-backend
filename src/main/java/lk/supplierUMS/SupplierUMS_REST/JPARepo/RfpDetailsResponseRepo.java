package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.RFP;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetailsResponse;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPHeader;

@Component
public interface RfpDetailsResponseRepo extends JpaRepository<RFPDetailsResponse, Long>{

	@Query("SELECT rr,rfph,rfpd,rem FROM RFPDetailsResponse rr INNER JOIN RFPHeader rfph ON rr.rfpHId = rfph.rfpHId INNER JOIN RFPDetails rfpd ON rr.rfpDId = rfpd.rfpDId INNER JOIN RfpEvaluationMarks rem ON rem.rfpDetailResId = rr.rfpDetailResId WHERE rr.tenderID =?2 AND rr.companyCode=?3 AND rem.evaluatorUserId=?4 AND (rfph.rfpHeaderName LIKE %?1% OR rfpd.rfpDFiledNmae LIKE %?1%) ") //GROUP BY rfpd.rfpDId
	Page<Object[]> rfpResponseDetails(String parameter, Long tendrID, Long supplierID,String userId, PageRequest of);

	List<RFPDetailsResponse> findAllByTenderIDAndCompanyCode(Long tenderID, Long supplierId);
	
}
