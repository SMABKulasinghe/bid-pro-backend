package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lk.supplierUMS.SupplierUMS_REST.entity.ContractInvoiceDetails;

public interface ContractInvoiceDetailsRepo extends JpaRepository<ContractInvoiceDetails, Long>{

	@Query("SELECT tcid FROM ContractInvoiceDetails tcid WHERE tcid.invoiceheaderid = ?2 AND (tcid.itemcode LIKE %?1% OR tcid.unitprice LIKE %?1% OR tcid.itemquantity LIKE %?1% OR tcid.itemamount LIKE %?1% OR tcid.servicecharge LIKE %?1% OR tcid.linediscount LIKE %?1% OR tcid.lineamount LIKE %?1%)")
	Page<ContractInvoiceDetails> getApprovedInvoicesDetails(String parameter, long invoiceId, PageRequest of);

}
