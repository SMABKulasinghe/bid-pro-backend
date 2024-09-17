package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lk.supplierUMS.SupplierUMS_REST.entity.TempContractInvoiceDetails;

public interface TempContractInvoiceDetailsRepo extends JpaRepository<TempContractInvoiceDetails, Long>{

	List<TempContractInvoiceDetails> findByUpdateduser(String user);

	List<TempContractInvoiceDetails> findByUpdateduserAndInvoiceheaderid(String user, long invoiceHeadeId);

	@Query("SELECT tcid FROM TempContractInvoiceDetails tcid WHERE tcid.invoiceheaderid = ?2 AND (tcid.itemcode LIKE %?1% OR tcid.unitprice LIKE %?1% OR tcid.itemquantity LIKE %?1% OR tcid.itemamount LIKE %?1% OR tcid.servicecharge LIKE %?1% OR tcid.linediscount LIKE %?1% OR tcid.lineamount LIKE %?1%)")
	Page<TempContractInvoiceDetails> getUploadedInvoicesDetails(String search, long invoiceId, PageRequest of);
	
	
}
