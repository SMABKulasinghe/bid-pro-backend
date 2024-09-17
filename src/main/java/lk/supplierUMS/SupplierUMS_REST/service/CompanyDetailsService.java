package lk.supplierUMS.SupplierUMS_REST.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import lk.supplierUMS.SupplierUMS_REST.entity.CompanyDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;


public interface CompanyDetailsService {
	
	//boolean addcompany(JSONObject data);
	String addcompany(JSONObject companydata);

	JSONObject addPartnership(long company, long supplier);
	
	JSONObject getAllCompany(HttpServletRequest request);

	JSONObject getCompanyList(String search, Long page);

	SubCompany getCompanybyId(long id);

	SubCompany authCompanybyId(long id, String status);

	JSONObject pendingAuthCompanies(HttpServletRequest req);

}
