package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.TempContractInvoiceHeader;

@Component
public interface TempContractInvoiceHeaderRepo extends JpaRepository<TempContractInvoiceHeader, Long>{

	@Query("SELECT CASE WHEN  COUNT(ih)=0 THEN TRUE ELSE FALSE END FROM TempContractInvoiceHeader ih INNER JOIN ContractDetails cd ON ih.contractid = cd.contractId WHERE ih.contractno = ?1 AND ih.invoicenumber = ?2")
	boolean findBySuppliermappingandcontractidandinvoiceid(String contractid, String invoiceId);
	
	List<TempContractInvoiceHeader> findByUpdateduser(String userId);
	
	@Query("SELECT tcih FROM TempContractInvoiceHeader tcih WHERE tcih.updateduser =?2 AND (tcih.contractno LIKE %?1% OR tcih.invoicenumber LIKE %?1% OR tcih.invoicedate LIKE %?1% OR tcih.linediscount LIKE %?1% OR tcih.total LIKE %?1% OR tcih.totaltax LIKE %?1% OR tcih.netamount LIKE %?1% OR tcih.batchnumber LIKE %?1%)")
	Page<TempContractInvoiceHeader> getUploadedInvoices(String search,String user, Pageable page);
}
