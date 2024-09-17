package lk.supplierUMS.SupplierUMS_REST.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;

import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;

public interface AppzTenderSubmissionsService {

	//JSONObject confirmEligibleSuppliers(JSONObject data, Long tenderId);

//	JSONObject EligibleSupplierNotice(JSONObject data, String string);

	//JSONObject confirmEligibleSuppliers(JSONObject data, Long tenderId);


	JSONObject confirmTender(JSONObject data, Long tenderId);

//	JSONObject responseTender(Long selectedTenderNo, @RequestBody JSONObject data);
	
	JSONObject responseTender(Long selectedTenderID, @RequestBody JSONObject data);


	JSONObject EligibleSupplierNotice(JSONObject data, HttpServletRequest request);

	List<TenderDetails> getSubmittedTender();

	List<SubCompany> getSupplierDetaiilsForTender(Long vendorId);

	JSONObject getMarksheetForSupllierAndTender(HttpServletRequest request, Long venderID, Long tenderID);

	JSONObject getValidatedSupplierDetaiilsForTender(HttpServletRequest request, Long tenderID, Long venderID);

	

	JSONObject confirmEligibleSuppliers(JSONObject data, Long tenderId);


}
