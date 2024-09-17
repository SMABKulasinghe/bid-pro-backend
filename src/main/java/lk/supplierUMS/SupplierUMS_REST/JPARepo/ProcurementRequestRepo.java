package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.ProcurementRequest;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;

@Component
public interface ProcurementRequestRepo extends JpaRepository<ProcurementRequest, Long>{

	
//	@Query("SELECT prof, rfpD from ProcurementRequest prof INNER JOIN RFP rfpD ON prof.prID = rfpD.prId WHERE prof.prID =?1")
//	List<Object[]> getProcurementDetails(long prID);
	
	@Query("SELECT prof, rfpD, ecat from ProcurementRequest prof INNER JOIN RFP rfpD ON prof.prID = rfpD.prId INNER JOIN EligibleCategory ecat ON prof.serviceCategory = ecat.eligibleCategortID WHERE prof.prID =?1")
	List<Object[]> getProcurementDetails(long prID);
	
	@Query("SELECT pr FROM ProcurementRequest pr WHERE pr.status = '3'")
	List<ProcurementRequest> getprID();
	
}