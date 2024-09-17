package lk.supplierUMS.SupplierUMS_REST.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;

public interface SupplierService {

	JSONObject addSupplier(JSONObject data, String param);

	JSONObject grtSupplierList(String search, Long page);

	JSONObject grtSupplierById(long id);

	JSONObject grtSupplierList(HttpServletRequest req);

	JSONObject grtPartnerSupplierList(String search, Long page);

	JSONObject getSupplierUnAutherized(HttpServletRequest req);

	JSONObject SupplierApprove(long id, String approve);

	JSONObject getSuppliersForPayment(String search, Long page);

	JSONObject getCompanyForAutherization(HttpServletRequest req);

	JSONObject getSubCompanyById(long id);

	Long supplierSelfReg(JSONObject data, String param);

	JSONObject addProductObject(JSONObject data, Long param);

	JSONObject updateSupplier(JSONObject data);

	JSONObject categorySuppliers(HttpServletRequest req, Long supplierID);

	JSONObject getSupplierDetails();

	JSONObject supplierApprove(long id, String approve);

	String createcategory(JSONObject data, HttpServletRequest request);

	JSONObject categoryView(HttpServletRequest request);

	JSONObject categoryAuth(long id, String approve);

	Boolean getWetherCategoryExists(String enteredValue);

	Boolean getWetherCategoryDesExists(String enteredValue);

	Boolean getWetherCategoryCodeExists(String enteredValue);

	JSONObject editCategoryView(JSONObject data);

	Boolean getWetherCategoryFeeExists(String enteredValue);

	JSONObject getSupplierForView(HttpServletRequest req);

	JSONObject viewCategorySuppliers(HttpServletRequest req, Long supplierID);

	JSONObject companyProfile(HttpServletRequest request, Long companyID);

	JSONObject companyViewCategory(HttpServletRequest req, Long companyID);

	JSONObject selectAdditionalCategory(HttpServletRequest req, Long companyID);

	JSONObject submitNewCategory(HttpServletRequest req, JSONObject data);

	JSONObject getSupplierForBlock(HttpServletRequest req);

	JSONObject blockCategorySuppliers(HttpServletRequest req, Long supplierID);

	JSONObject blockSupplier(JSONObject data);

	JSONObject viewBlockSuppliers(HttpServletRequest req);

	JSONObject viewBlockCategorySuppliers(HttpServletRequest req, Long supplierID);

	JSONObject unblockSupplier(JSONObject data);

	JSONObject addSupplierComment(JSONObject data, String parameter);

	Boolean getWetherSupplierExistsByregno(String enteredValue);

	String supplierFileSubmit(JSONObject data, HttpServletRequest request, String filename);

	Boolean getWetherSupplierExistsBycontactno(String enteredValue);

	Boolean getWetherSupplierExistsByconemail(String enteredValue);
	


	
	

}
