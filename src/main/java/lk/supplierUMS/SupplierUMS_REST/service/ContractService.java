package lk.supplierUMS.SupplierUMS_REST.service;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;

public interface ContractService {

	String addContract(JSONObject data);

	JSONObject getAllContracts(HttpServletRequest request, String parameter);

	JSONObject getPartnership(long id);

	JSONObject acceptPartnership(long id, String status);

	String approveContract(JSONObject data);

	JSONObject getCompanyContracts(long id, HttpServletRequest request);
	
	JSONObject getcontractlist(String search, Long page);
	
//	void raisInvoice();

}
