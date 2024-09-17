package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lk.supplierUMS.SupplierUMS_REST.entity.ContractInvoiceHeader;

public interface ContractInvoiceHeaderRepo extends JpaRepository<ContractInvoiceHeader, Long>{

	@Query("SELECT map,con FROM CompanySupplierMapping map INNER JOIN ContractDetails con ON map.mappingID = con.mappingCode WHERE map.companyID = ?1 AND map.supplierID= ?2 AND (con.contractNo LIKE %?3% OR con.contractDetails LIKE %?3% OR con.category LIKE %?3% OR con.paymentType LIKE %?3% OR con.contractAmount LIKE %?3% OR con.annualPaymentAmountLKR LIKE %?3%)")
	Page<Object[]> getContractForSuppliers(long companyId,long supplierId,String search, Pageable page);
	
	@Query(value = "SELECT DATEDIFF(inv.i_date, cd.m_start_date_period) % cd.renewal_date_period FROM `m_contract_invoice_header` AS inv INNER JOIN `m_contract_details` AS cd ON cd.`m_contract_no` = inv.`i_contract_no` WHERE inv.`i_contract_no` = ?1 ORDER BY inv.`i_date` DESC LIMIT 1", nativeQuery = true)
	Optional<Long> getContractForSuppliersRenewalDate(String contractNo);
	
	/*@Query("SELECT cd, sub FROM ContractInvoiceHeader cd INNER JOIN CompanySupplierMapping csm ON cd.mappingid = csm.mappingID INNER JOIN SubCompany sub ON csm.supplierID = sub.subCompanyID WHERE csm.companyID = ?2 AND (sub.scname LIKE %?1%)")	
	Page<Object[]> getInvoiceHeaders(String parameter, Long userCompanyID, PageRequest of);
*/
	@Query("SELECT cd, sub FROM ContractInvoiceHeader cd INNER JOIN CompanySupplierMapping csm ON cd.mappingid = csm.mappingID INNER JOIN SubCompany sub ON csm.supplierID = sub.subCompanyID WHERE csm.companyID = ?2 AND cd.approvecomplete IN('N') AND (sub.scname LIKE %?1% OR cd.contractno LIKE %?1% OR cd.invoicedate LIKE %?1% OR cd.invoicenumber LIKE %?1% OR cd.total LIKE %?1% OR cd.batchnumber LIKE %?1%)")	
	Page<Object[]> getInvoiceHeaders(String parameter, Long userCompanyID, PageRequest of);
	
	@Query("SELECT cd, sub FROM ContractInvoiceHeader cd INNER JOIN CompanySupplierMapping csm ON cd.mappingid = csm.mappingID INNER JOIN SubCompany sub ON csm.companyID = sub.subCompanyID WHERE csm.supplierID = ?2 AND (sub.scname LIKE %?1% OR cd.contractno LIKE %?1% OR cd.invoicedate LIKE %?1% OR cd.invoicenumber LIKE %?1% OR cd.total LIKE %?1% OR cd.batchnumber LIKE %?1%)")	
	Page<Object[]> getInvoiceHeadersForSupplier(String parameter, Long userCompanyID, PageRequest of);
	
	@Query("SELECT cd, sub FROM ContractInvoiceHeader cd INNER JOIN CompanySupplierMapping csm ON cd.mappingid = csm.mappingID INNER JOIN SubCompany sub ON csm.supplierID = sub.subCompanyID WHERE csm.companyID = ?2 AND (sub.scname LIKE %?1% OR cd.contractno LIKE %?1% OR cd.invoicedate LIKE %?1% OR cd.invoicenumber LIKE %?1% OR cd.total LIKE %?1% OR cd.batchnumber LIKE %?1%)")	
	Page<Object[]> getInvoiceHeadersForCompany(String parameter, Long userCompanyID, PageRequest of);
	
	@Query("SELECT cd, sub FROM ContractInvoiceHeader cd INNER JOIN CompanySupplierMapping csm ON cd.mappingid = csm.mappingID INNER JOIN SubCompany sub ON csm.supplierID = sub.subCompanyID WHERE csm.companyID = ?2 AND cd.approvecomplete IN('Y') AND (sub.scname LIKE %?1%)")	
	Page<Object[]> getInvoiceHeadersForPayment(String parameter, Long userCompanyID, PageRequest of);
	
	@Query("SELECT cd, sub, condet FROM ContractInvoiceHeader cd INNER JOIN CompanySupplierMapping csm ON cd.mappingid = csm.mappingID INNER JOIN SubCompany sub ON csm.supplierID = sub.subCompanyID INNER JOIN ContractDetails condet ON condet.contractId = cd.contractid WHERE csm.companyID = ?2 AND cd.approvecomplete IN('YY') AND cd.status IN('PP') AND (sub.scname LIKE %?1%)")	
	Page<Object[]> getInvoiceHeadersForOutstandingPaymentForSupplier(String parameter, Long userCompanyID, PageRequest of);
	
	@Query("SELECT cd, sub, condet FROM ContractInvoiceHeader cd INNER JOIN CompanySupplierMapping csm ON cd.mappingid = csm.mappingID INNER JOIN SubCompany sub ON csm.companyID = sub.subCompanyID INNER JOIN ContractDetails condet ON condet.contractId = cd.contractid WHERE csm.supplierID = ?2 AND cd.approvecomplete IN('YY') AND cd.status IN('PP') AND (sub.scname LIKE %?1%)")	
	Page<Object[]> getInvoiceHeadersForOutstandingPaymentForSuppliersView(String parameter, Long userCompanyID, PageRequest of);
	
	@Query("SELECT cd, sub FROM ContractInvoiceHeader cd INNER JOIN CompanySupplierMapping csm ON cd.mappingid = csm.mappingID INNER JOIN SubCompany sub ON csm.supplierID = sub.subCompanyID WHERE csm.companyID = ?2 AND csm.supplierID = ?3 AND cd.approvecomplete IN('Y') AND (sub.scname LIKE %?1%)")	
	Page<Object[]> getInvoiceHeadersForPaymentForSupplier(String parameter, Long userCompanyID, Long para, PageRequest of);
	
	
	@Query(value = "select case when Count(cih)>0 then true else false end FROM ContractInvoiceHeader cih WHERE cih.mappingid = ?1 AND cih.contractno= ?2 AND cih.invoicenumber = ?3")
	boolean alreadyProcessedInvoice(long mappingId, String contractNo,String invoiceNumber);
	
	@Query(value = "select SUM(cid.itemamount) FROM TempContractInvoiceHeader cih INNER JOIN TempContractInvoiceDetails cid ON cih.invoiceheaderid = cid.invoiceheaderid WHERE cih.mappingid = ?1 AND cih.contractno= ?2 AND cih.invoicenumber = ?3")
	BigDecimal BodyAmmount(long mappingId, String contractNo,String invoiceNumber);
	
	@Query("SELECT ih, map FROM ContractInvoiceHeader ih INNER JOIN CompanySupplierMapping map ON map.mappingID = ih.mappingid WHERE ih.approvecomplete = 'N' AND map.companyID = ?1")
	List<Object[]> findpendingInvoices(long companyid);
	
//	kanishka dashboard get supplier invoice count 2019/12/23
	@Query("SELECT COUNT(invhd) FROM ContractInvoiceHeader invhd JOIN CompanySupplierMapping map ON invhd.mappingid = map.mappingID WHERE map.supplierID=?1")
	long getsuplierinvocount(Long companyid);
	
	@Query("SELECT COUNT(invhd) FROM ContractInvoiceHeader invhd JOIN CompanySupplierMapping map ON invhd.mappingid = map.mappingID WHERE map.companyID=?1")
	long getcompanyinvocount(Long companyid);
	
	@Query("SELECT COUNT(invhd) FROM ContractInvoiceHeader invhd JOIN CompanySupplierMapping map ON invhd.mappingid = map.mappingID WHERE map.supplierID=?1 AND invhd.approvecomplete =  'YY' AND invhd.status='PP' ")
	long getsuplierpaymentcount(Long companyid);
	
	@Query("SELECT COUNT(invhd) FROM ContractInvoiceHeader invhd JOIN CompanySupplierMapping map ON invhd.mappingid = map.mappingID WHERE map.companyID=?1 AND invhd.approvecomplete =  'YY' AND invhd.status='PP' ")
	long getcompanypaymentcount(Long companyid);
	
	@Query("SELECT co_inhd,map,sub FROM ContractInvoiceHeader co_inhd JOIN CompanySupplierMapping map ON co_inhd.mappingid= map.mappingID JOIN SubCompany sub ON map.supplierID =sub.subCompanyID WHERE co_inhd.status='PP' AND co_inhd.approvecomplete='YY' AND map.companyID=?2 AND (sub.scname LIKE %?1% OR co_inhd.contractno LIKE %?1% OR co_inhd.total LIKE %?1% OR co_inhd.createdate LIKE %?1% OR co_inhd.invoicedate LIKE %?1%)")
	Page<Object[]> getCompanyspendingInvoice(String search,long companyId,Pageable page);
}
