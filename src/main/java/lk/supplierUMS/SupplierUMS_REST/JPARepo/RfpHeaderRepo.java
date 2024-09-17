package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.RFPHeader;

@Component
public interface RfpHeaderRepo extends JpaRepository<RFPHeader, Long>{

	@Query("SELECT rfpH, rfpD from RFPHeader rfpH INNER JOIN RFPDetails rfpD ON rfpH.rfpHId = rfpD.rfpHId WHERE rfpH.rfpId = ?1")
	List<Object[]> getHeaderDetails(long id);

	@Query("SELECT rfph FROM RFPHeader rfph WHERE rfph.rfpId=?1")
	List<RFPHeader> findByRfpId(Long id);

	
//	Optional<RFPHeader> getHeaderDetails();

	/* RFPHeader add(RFPHeader rfpHeader); */

}
