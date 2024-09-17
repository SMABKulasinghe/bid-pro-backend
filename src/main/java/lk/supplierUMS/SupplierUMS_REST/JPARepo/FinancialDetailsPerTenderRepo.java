package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.FinancialDetailsPerTender;


@Component
public interface FinancialDetailsPerTenderRepo extends JpaRepository<FinancialDetailsPerTender,Long>{

	@Query("SELECT fdpt, ts, fc FROM FinancialDetailsPerTender fdpt "
//			+ "INNER JOIN TenderDetails td ON fdpt.tenderID = td.trnderid "
			+ "INNER JOIN TenderSubmissions ts ON fdpt.tenderID = ts.tenderNo "
			+ "INNER JOIN FinancialCodes fc ON fdpt.financialCodeId = fc.financialCodeId "
			+ "WHERE fdpt.tenderID = ?1 AND ts.supplierId = ?2 ")
	List<Object[]> getFinancialCodeDetails(Long tenderId, Long vendorID);
	


}