package lk.supplierUMS.SupplierUMS_REST.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategoryProducts;
import lk.supplierUMS.SupplierUMS_REST.entity.ProcurementRequest;
import lk.supplierUMS.SupplierUMS_REST.entity.RFP;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;

public interface RfpService {

	String addRfp(JSONObject data, HttpServletRequest request);
	
	Boolean getWetherRfpExists(String enteredValue);

	JSONObject getRfpResponse(HttpServletRequest request);

	JSONObject getRfpDetails(long id);

	JSONObject RfpAuthorization(long id, String approve);

	JSONObject getRfpUpdate(HttpServletRequest request);

	Boolean getWetherTenderExists(String enteredValue);

	String updateSubmitRfp(JSONObject data, HttpServletRequest request);

	JSONObject getRfpView(HttpServletRequest request);

	JSONObject getDownloadRfpData(long id);

	List<RFP> getFileName();

	List<RFP> getRFPForCategory(Long categoryID, Long subCatId);

	JSONObject addRfpComment(JSONObject data);

	JSONObject getRfpViewForComputer(HttpServletRequest request);

	JSONObject procurementRequestDetails(JSONObject data);

	JSONObject getAllTenderDetails(HttpServletRequest request, Long rfpId);
	
	JSONObject viewProcurementDetails(long id);

	List<RFP> rfpIdForRevisedRfpSubmittion();

	JSONObject getRfpDetailsForEdit(Long id);

	JSONObject getRfpDetailsForEditTable(HttpServletRequest request, long id);

	List<TenderDetails> getTenderIdForRfpChangeId();

	String tenderRfpChange(JSONObject data, HttpServletRequest request);

	JSONObject getRfpForSelect(long tenderId);

	List<ProcurementRequest> getprID();

	JSONObject addRfpEveCommittee(JSONObject data, HttpServletRequest request);

	JSONObject getTenderForRfpEvaComCreation();

	JSONObject rfpCommitteeView(HttpServletRequest request, Long tendrID);

	JSONObject getTenderForRfpEvaMaking();

	JSONObject rfpResponseDetails(HttpServletRequest request, Long tendrID, Long supplierID);

	String addRfpMarks(JSONObject data, HttpServletRequest request);

	JSONObject getRfpDetailsForSubmitButton(Long tender_ID, Long supplier_ID);

	JSONObject getRfpEvaluationResults(long tenderId);

	

}
