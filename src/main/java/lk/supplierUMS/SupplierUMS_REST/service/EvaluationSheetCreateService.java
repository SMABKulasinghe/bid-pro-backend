package lk.supplierUMS.SupplierUMS_REST.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationSheetCreate;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.User;

public interface EvaluationSheetCreateService {
	

	List<TenderDetails> getTenderId();

	String addevaluation(JSONObject data, HttpServletRequest request);

	String addmarksheet(JSONObject data, HttpServletRequest request);
	
	JSONObject getEvaluatioedMarks(long tenderId, String venderId);

	JSONObject getTenderIds();

	JSONObject getEvaluationAllMarks(HttpServletRequest request,Long id);

	List<TenderDetails> getTenderDetailsForMarks();

	List<User> getTenderEvaluationForCommittee();

	JSONObject addevaluationcommittee(JSONObject data, HttpServletRequest request);

	String getsubmitedcounts(Long tenderID, Long vendorID);
	
	JSONObject submitEvaluationAllMarks(JSONObject data);

	JSONObject committeeview(HttpServletRequest request, Long tendrID);

	JSONObject viewMarksheetsForEvaluator(HttpServletRequest request);

	JSONObject viewEvaluationSheetDetails(HttpServletRequest request, Long tenderID);

	List<TenderDetails> getViewForTenderDetails();

	List<TenderDetails> getViewEveluationEditSheet();

	JSONObject editsheet(HttpServletRequest request, Long tendrID);

	JSONObject confirmEditSheet(Long selectedMarkSheetID, JSONObject data);

	JSONObject addNewCriteria(Long selectedTID, JSONObject data);

	List<TenderDetails> getMITDetailsForTenderDetails();

	JSONObject enterMitDetails(JSONObject data, HttpServletRequest request);

	List<TenderDetails> getViewMITDetailsForTenderDetails();
	
	JSONObject viewMitDetails(HttpServletRequest req, Long tendrID);

	List<TenderDetails> getTenderDetailsForAuthorize();

	JSONObject authorizeevaluation(HttpServletRequest request, Long tendrID);

	JSONObject authorizeevcommittee(HttpServletRequest request, Long tendrID);

	JSONObject addAuthorizeComment(JSONObject data, String parameter);
	

}
