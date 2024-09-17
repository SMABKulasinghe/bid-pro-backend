package lk.supplierUMS.SupplierUMS_REST.service;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

public interface DashBoardService {
	JSONObject getSupliersCompanyContracts(HttpServletRequest request);
	
	JSONObject getCompanysSuplierContracts(HttpServletRequest request);
	
	JSONObject getSummaryBoxValues_sup(HttpServletRequest request);
	
	JSONObject getSummaryBoxValues_com(HttpServletRequest request);
	
	JSONObject getSummaryChartData_sup(HttpServletRequest request);
	
	JSONObject getSupplierNextInvoices(HttpServletRequest request);
	
	JSONObject getSuppliersCompanyes(HttpServletRequest request);
	
	JSONObject getCompanyesSuppliers(HttpServletRequest request);
	
	JSONObject getCompanyespendingInvoice(HttpServletRequest request);

	JSONObject getAllCompanies(HttpServletRequest request,boolean identity);

	JSONObject getAllCounts(String type);
	
	
}
