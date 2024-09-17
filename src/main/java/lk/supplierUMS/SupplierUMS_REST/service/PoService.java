package lk.supplierUMS.SupplierUMS_REST.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;

public interface PoService {

	/* String addpo(JSONObject data); */

	String addPo(JSONObject data,HttpServletRequest request);

	JSONObject getPoAuthorization(HttpServletRequest request);

	JSONObject PoAuthorization(long id, String approve);

	JSONObject getPoDetails(HttpServletRequest request);

	JSONObject getTenderIds();

	JSONObject getTenderDetailsForPo(Long id);

	JSONObject sendEmailAndAuth(JSONObject data);

	JSONObject poReject(JSONObject data);

	JSONObject getPoDetailsForTable(HttpServletRequest request);

	JSONObject getPoDetailsForSupplierTable(HttpServletRequest request);

	String addTermsAndConditions(JSONObject data, HttpServletRequest request);

	JSONObject getTermsAndConditions();

	String addTermsAndConditionsToTransTable(JSONObject data, HttpServletRequest request);

	JSONObject getTermsAndConditionsForTable(HttpServletRequest request, Long tenderId);

	JSONObject termsAndConditionsStatusTransTbl(long id, String approve);

	JSONObject viewTermsAndConditions(HttpServletRequest request);

	JSONObject termsAndConditionsStatusTbl(long id, String approve);

	Boolean getWetherTermsConExists(String termsAndCon);

	

	Boolean getWetherTermsConTenderExists(Long tenderId, Long termsCon);

	JSONObject editTermsAndConditions(JSONObject data);

	JSONObject getTermsAndConditionsForIssuePoPage(Long tenderId);

	JSONObject getTermsAndConView(long id);

	JSONObject getTenders();

	JSONObject getTenderDetailsForReIssuePo(HttpServletRequest request, Long id);

	JSONObject addReIssuePo(JSONObject data, HttpServletRequest request);


	

	

	

}
