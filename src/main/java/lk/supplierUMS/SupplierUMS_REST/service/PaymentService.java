package lk.supplierUMS.SupplierUMS_REST.service;

import org.json.simple.JSONObject;

public interface PaymentService {

	String approveInvoicesForPayment(JSONObject data);

	String addForPayment(JSONObject data);

}
