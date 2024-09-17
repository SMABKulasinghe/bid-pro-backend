package lk.supplierUMS.SupplierUMS_REST.controllers;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.supplierUMS.SupplierUMS_REST.service.InvoiceService;
import lk.supplierUMS.SupplierUMS_REST.service.PaymentService;

@CrossOrigin
@RestController
@RequestMapping(value ="payment")
public class PaymentController {
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	PaymentService paymentService;
	
	
	@GetMapping(value="{para}")
	public ResponseEntity<JSONObject> getAllInvoicesToBeApprovedForPayment(@PathVariable String para, HttpServletRequest request) {
		JSONObject data = null;
		System.out.println("Controller Payement Details");
		
		try {
			
			data = invoiceService.getAllInvoicesToBeApprovedForPayment(request, para);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
		
		
	}
	
	@PostMapping(value = "approve")
	public ResponseEntity<String> approveInvoicesForPayment(@RequestBody JSONObject data) {
		System.out.println("ApproveInvoices "+data);
		
		String returnMsg = paymentService.approveInvoicesForPayment(data);
		String msg = "Invoices approval for payment has been failed";
		
		if(returnMsg.equals("Success")){
			msg = "Success";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	@PostMapping(value = "add")
	public ResponseEntity<String> addForPayment(@RequestBody JSONObject data) {
		System.out.println("addForPayment "+data);
		
		String returnMsg = paymentService.addForPayment(data);
		String msg = "addForPayment has been failed";
		
		if(returnMsg.equals("Success")){
			msg = "Success";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	
	
	@GetMapping(value="approved/invoice/{invoiceId}/details")
	public ResponseEntity<JSONObject> getInvoiceDetails(@PathVariable long invoiceId, HttpServletRequest request) {
		System.out.println("approved/invoice>>>>" + invoiceId);
		try {
			JSONObject data = null;
			data = invoiceService.viewCompanyApprovedInvoices(invoiceId, request);
			return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<JSONObject>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	

}
