package lk.supplierUMS.SupplierUMS_REST.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;

public interface TenderDetailsService {
	String createTender(JSONObject data);

	JSONObject getTenders(HttpServletRequest req);

	JSONObject getEligibleVenders(long id);

	Boolean getWetherTenderExistsByBidno(String enteredValue);

	JSONObject getTenderDetailsForSupplier(HttpServletRequest request);

	String tendersubmit(JSONObject data, HttpServletRequest request, String filename);
	
	JSONObject getTenderForView(HttpServletRequest req);

	JSONObject getTenderAuthorization(HttpServletRequest request);

	JSONObject TenderAuthorization(long id, String approve);

	JSONObject updateTender(JSONObject data);

	JSONObject gettenderlist(long id, HttpServletRequest request);

	JSONObject getAllTender(HttpServletRequest request, String parameter);

	JSONObject getTenderOpen(HttpServletRequest req);

	JSONObject TenderStatusOpen(long id, HttpServletRequest request);

	JSONObject getEligibleSuppliers(HttpServletRequest request, Long tenderID);

	JSONObject getTenderIds();

	JSONObject getTenderDetailsForView(Long id);

	JSONObject getTenderDetailsForViewTable(HttpServletRequest request, Long id);

	List<TenderDetails> getTenderForFinancialResponse();

	JSONObject financialResponse(HttpServletRequest request, Long tenderId);

	String financialValuesPerTender(JSONObject data);

	List<TenderDetails> getFinancialCodeDetailsForTender();

	JSONObject getFinancialCodeDetails(HttpServletRequest request, Long tenderId);

	JSONObject getTenderDetailsForSupplierView(HttpServletRequest request);

	JSONObject getTenderDetailsForDetailsViewAll(HttpServletRequest request);

	String financial_code(JSONObject data, HttpServletRequest request);
	JSONObject getFinancialDetailsForCappex(HttpServletRequest request, Long tenderId, Long supplierId);

	JSONObject getFinancialDetailsForOppex(HttpServletRequest request, Long tenderId, Long supplierId);

	JSONObject getFinancialDetailsForCappexOppex(HttpServletRequest request, Long tenderId, Long supplierId);

	JSONObject getTenderDetailsIdsForTenderParticipators();

	JSONObject getTenderDetailsForViewTenderParticipators(Long id);

	JSONObject getTenderDetailsForViewTableForParticipators(HttpServletRequest request, Long id);

	Boolean getWetherUserExistsByCordinator1contactno(String enteredValue);

	Boolean getWetherUserExistsByCordinator2email(String enteredValue);

	Boolean getWetherUserExistsByCordinator2contactno(String enteredValue);

	Boolean getWetherUserExistsByCordinator1email(String enteredValue);
	String testEmails(JSONObject data, HttpServletRequest request);

	JSONObject addTenderAuthReason(JSONObject data, String status);

	String financialCodeCreation(JSONObject data, HttpServletRequest request);

	JSONObject financialCodeView(HttpServletRequest request);

	JSONObject financialCodeAuth(long id, String approve);

	JSONObject editFinancialCode(JSONObject data);

	Boolean getWetherFinancialExists(String enteredValue);

	Boolean getWetherFinancialDesExists(String enteredValue);

	List<TenderDetails> getTenderForFinancialPerTender();

	List<FinancialCodes> getFinancialForFinancialPerTender();

	String financialCodePerTenderSubmit(JSONObject data, HttpServletRequest request);

	JSONObject financialPerTenderView(HttpServletRequest request, Long tenderId);

	JSONObject getFinancialDocForView(HttpServletRequest request, Long id);

	String tendersubmitRfp(JSONObject data, HttpServletRequest request);

	String aditionalFileForTenderParams(JSONObject data, HttpServletRequest request);

	JSONObject aditionalDetailsForEditTble(HttpServletRequest request, Long id);

	JSONObject aditionalFilesStatus(long id, String approve);

	JSONObject editAdditionalFileName(JSONObject data);

	Boolean getWetherAddtionalFilesExists(Long tenderId, String fileName);

	JSONObject getAdditionalFilesToPage(Long tenderId);

	String aditionalFileUpload(JSONObject data, HttpServletRequest request, Long id);

	JSONObject aditionalDetailsForViewTble(HttpServletRequest request, Long tenderId);

	JSONObject lockAdditionalFileUpload(JSONObject data);

	
	List<TenderDetails> getTenderExtendForTenderDetails();

	JSONObject updateTenderExtend(JSONObject data);

	List<TenderDetails> getViewFinancialDetails();

	JSONObject getTenderDetailsForFinancialView(HttpServletRequest request, Long tendrID);

	JSONObject getViewFinancialDetailsForCappex(HttpServletRequest request, Long tenderId, Long supplierId);

	JSONObject getViewFinancialDetailsForOppex(HttpServletRequest request, Long tenderId, Long supplierId);

	JSONObject getViewFinancialDetailsForCappexOppex(HttpServletRequest request, Long tenderId, Long supplierId);

	JSONObject getResubmitFinancialDetailsAllowOption();

	String financialResubmitCreation(JSONObject data, HttpServletRequest request);

	JSONObject getResubmitFinancialDetailsAllowOptionSupplier(Long tenderId);

	List<EligibleSubCategory> getSubCat(Long catId);

	JSONObject closedTenders();

	String additionalFileUploadInvitationForSupplier(JSONObject data, HttpServletRequest request);

	JSONObject getSupplierAdditinalFileUpload(HttpServletRequest request);

	String additinalFileUpload(JSONObject data, HttpServletRequest request);

	Boolean getAdditionalFileExists(String enteredValue);

	JSONObject getSupplierAdditinalFileDownload(HttpServletRequest request, Long tenderId);

	

	

	
	
	//List<TenderDetails> getTenderDetailsForView();

	

	//List<TenderDetails> getTenderDetailsForView();


}
