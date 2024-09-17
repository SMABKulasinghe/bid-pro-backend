package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetails;

@Component
public interface RfpDetailsRepo extends JpaRepository<RFPDetails, Long>{

	/* void add(RFPDetails rfpDetails); */
	@Query("SELECT rfpd FROM RFPDetails rfpd WHERE rfpd.rfpId = ?1 " )
	List<RFPDetails> getRfpDetails(Long rfpId);

	List<RFPDetails> findByRfpId(Long rfpid);
	
	//List<RFPDetails> findByRfpId(Long rfpID);
}
