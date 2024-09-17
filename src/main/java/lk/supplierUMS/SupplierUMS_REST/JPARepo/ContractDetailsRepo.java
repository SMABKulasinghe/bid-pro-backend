package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;

@Component
public interface ContractDetailsRepo  extends JpaRepository<ContractDetails, Long> {
	
	@Query("SELECT cd, sub FROM ContractDetails cd INNER JOIN CompanySupplierMapping csm ON cd.mappingCode = csm.mappingID INNER JOIN SubCompany sub ON csm.supplierID = sub.subCompanyID WHERE cd.contractApprovalStatus IN ('P') AND csm.companyID = ?2 AND (sub.scname LIKE %?1% OR cd.contractNo LIKE %?1% OR cd.boardApprovalDate LIKE %?1% OR cd.contractAmount LIKE %?1% OR cd.category LIKE %?1%)")
	Page<Object[]> getContractDetails(String search, Long userCompanyID, Pageable page);
	
	@Query("SELECT cd, sub FROM ContractDetails cd INNER JOIN CompanySupplierMapping csm ON cd.mappingCode = csm.mappingID INNER JOIN SubCompany sub ON csm.supplierID = sub.subCompanyID WHERE csm.companyID = ?2 AND (sub.scname LIKE %?1% OR cd.contractNo LIKE %?1% OR cd.boardApprovalDate LIKE %?1% OR cd.contractAmount LIKE %?1% OR cd.category LIKE %?1%)")
	Page<Object[]> getContractDetailsForView(String search, Long userCompanyID, Pageable page);
	
	@Query("SELECT cd, sub FROM ContractDetails cd INNER JOIN CompanySupplierMapping csm ON cd.mappingCode = csm.mappingID INNER JOIN SubCompany sub ON csm.companyID = sub.subCompanyID WHERE csm.supplierID = ?2 AND (sub.scname LIKE %?1% OR cd.contractNo LIKE %?1% OR cd.boardApprovalDate LIKE %?1% OR cd.contractAmount LIKE %?1% OR cd.category LIKE %?1%)")
	Page<Object[]> getContractDetailsForSupplierView(String search, Long userCompanyID, Pageable page);
	
	@Query("SELECT cd FROM ContractDetails cd WHERE DATEDIFF(CURDATE(),cd.startDatePeriod)%cd.RenewalDatePeriod= 0")
	List<ContractDetails> getTodayRenewInvoice();
	
	@Query("SELECT cd,map,sc FROM ContractDetails cd INNER JOIN CompanySupplierMapping map ON cd.mappingCode = map.mappingID INNER JOIN SubCompany sc ON sc.subCompanyID = map.companyID WHERE map.supplierID = ?1 AND cd.contractId= ?2")
	Optional<List<Object[]>> getContractCompanyDetails(long supplierId,long contractId);
	
////	kanishka 2019/12/18 get contract details ***** start *****
	@Query("SELECT cod,map,subc FROM ContractDetails cod JOIN CompanySupplierMapping map ON cod.mappingCode = map.mappingID JOIN SubCompany subc ON map.companyID = subc.subCompanyID WHERE map.supplierID=?2 AND (subc.scname LIKE %?1% OR cod.contractNo LIKE %?1% OR cod.contractAmount LIKE %?1% OR cod.createdAt LIKE %?1% OR cod.expiryDate LIKE %?1% OR cod.modifiedBy LIKE %?1% OR cod.paymentType LIKE %?1% OR cod.RenewalDatePeriod LIKE %?1%)")
	Page<Object[]> getSupliresCompanyContract(String search,long companyId,Pageable page);
	
	@Query("SELECT cod,map,subc FROM ContractDetails cod JOIN CompanySupplierMapping map ON cod.mappingCode = map.mappingID JOIN SubCompany subc ON map.supplierID = subc.subCompanyID WHERE map.companyID=?2 AND (subc.scname LIKE %?1% OR cod.contractNo LIKE %?1% OR cod.contractAmount LIKE %?1% OR cod.createdAt LIKE %?1% OR cod.expiryDate LIKE %?1% OR cod.amcusd LIKE %?1% OR cod.paymentType LIKE %?1% OR cod.RenewalDatePeriod LIKE %?1%)")
	Page<Object[]> getCompanysSuplireContract(String search,long companyId,Pageable page);
	
	@Query("SELECT COUNT(cod) FROM ContractDetails cod JOIN CompanySupplierMapping map ON cod.mappingCode=map.mappingID WHERE map.supplierID=?1")
	long getsuplierContractCount(long supplierid);
	
	@Query("SELECT COUNT(cod) FROM ContractDetails cod JOIN CompanySupplierMapping map ON cod.mappingCode=map.mappingID WHERE map.companyID=?1")
	long getcompanyContractCount(long supplierid);
	
	@Query(value="SELECT MONTH(cod.`m_reminder_date`) MONTH,ROUND(SUM(cod.`m_contract_amount`)) subtotal,ROUND(SUM(coinvo.`i_total`)) invototal FROM `m_contract_details` cod INNER JOIN `m_com_sup_map` map ON cod.`m_mapping_code`=map.`m_mapping_id` INNER JOIN `m_contract_invoice_header` coinvo ON coinvo.`m_mapping_id` = map.`m_mapping_id` WHERE map.`m_supplier_id`=?1 AND (cod.`m_reminder_date`) >  DATE_SUB(NOW(), INTERVAL 6 MONTH) GROUP BY MONTH",nativeQuery=true)
	List<Object[]> getSUpplierSummerChart_supplier(long supplierid);
	
	@Query(value="SELECT MONTH(cod.`m_reminder_date`) MONTH,ROUND(SUM(cod.`m_contract_amount`)) subtotal,ROUND(SUM(coinvo.`i_total`)) invototal FROM `m_contract_details` cod INNER JOIN `m_com_sup_map` map ON cod.`m_mapping_code`=map.`m_mapping_id` INNER JOIN `m_contract_invoice_header` coinvo ON coinvo.`m_mapping_id` = map.`m_mapping_id` WHERE map.`m_company_id`=?1 AND (cod.`m_reminder_date`) >  DATE_SUB(NOW(), INTERVAL 6 MONTH) GROUP BY MONTH",nativeQuery=true)
	List<Object[]> getSUpplierSummerChart_cpmpany(long supplierid);
	
	@Query(value="SELECT subcom.`sc_name`,codt.`created_at`,codt.`contract_id`,codt.`m_reminder_date`,invohd.`i_total`,invohd.`i_date` FROM m_com_sup_map map INNER JOIN `m_subcompany` subcom ON map.`m_supplier_id`=subcom.`sub_companyid` INNER JOIN `m_contract_details` codt ON map.`m_mapping_id`=codt.`m_mapping_code` INNER JOIN `m_contract_invoice_header` invohd ON codt.`contract_id`=invohd.`i_contract_id` WHERE codt.`m_contract_approval_status` ='Accept' AND map.`m_supplier_id`=?2 AND codt.`m_reminder_date` < DATE_ADD(NOW(), INTERVAL 7 DAY) AND (subcom.`sc_name` LIKE %?1% OR codt.`created_at` LIKE %?1% OR codt.`contract_id` LIKE %?1% OR codt.`m_reminder_date` LIKE %?1% OR invohd.`i_total` LIKE %?1% OR invohd.`i_date` LIKE %?1%)",nativeQuery=true)
	Page<Object[]> getSupplierNextInvoices(String search,long supplierid,Pageable page);
	
	List<ContractDetails> findByContractNoLike(String search,Pageable page);
	
	Long countByContractNoLike(String search);
// ****** dashboard ******* end **********************************
}
