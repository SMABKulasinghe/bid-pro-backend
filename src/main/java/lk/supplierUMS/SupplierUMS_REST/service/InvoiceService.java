package lk.supplierUMS.SupplierUMS_REST.service;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

public interface InvoiceService {

	ResponseEntity issueInvoice(JSONObject data);

	JSONObject getAllInvoices(HttpServletRequest request, String parameter);

	JSONObject getUploadedInvoices(HttpServletRequest request);
	
	String approveInvoices(JSONObject data);

	JSONObject getInvoiceDetails(long invoiceId, HttpServletRequest request);

	JSONObject supplierApproveInvoice(String action);

	JSONObject getAllInvoicesToBeApprovedForPayment(HttpServletRequest request, String para);

	JSONObject viewCompanyApprovedInvoices(long invoiceId, HttpServletRequest request);

}
