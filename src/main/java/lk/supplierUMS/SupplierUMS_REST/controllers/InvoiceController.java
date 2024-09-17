package lk.supplierUMS.SupplierUMS_REST.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.core.io.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
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

@CrossOrigin
@RestController
@RequestMapping(value ="invoice")
public class InvoiceController {

	@Autowired
	InvoiceService invoiceService;
	
	@Value("${file.inv.format.path}")
	String fileInvoFormatInPath;
	
	@Value("${file.inv.format.name}")
	String fileInvoFormatFileName;
	
	
	@PostMapping
	public ResponseEntity issueInvoice(@RequestBody JSONObject data) {
		try {
			
			System.out.println(data);
			 ResponseEntity res = invoiceService.issueInvoice(data);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping(value="uploaded")
	public ResponseEntity<JSONObject> getUploadedInvoice(HttpServletRequest request) {
		try {
			
			JSONObject data = invoiceService.getUploadedInvoices(request);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<JSONObject>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="{para}")
	public ResponseEntity<JSONObject> getAllInvoices(HttpServletRequest request, @PathVariable String para) {
		JSONObject data = null;
		System.out.println("Controller Invoice Details");
		
		try {
			
			data = invoiceService.getAllInvoices(request, para);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
		
		
	}
	
	
	@PostMapping(value = "approve")
	public ResponseEntity<String> approveInvoices(@RequestBody JSONObject data) {
		System.out.println("ApproveInvoices "+data);
		
		String returnMsg = invoiceService.approveInvoices(data);
		String msg = "Invoices approval failed";
		
		if(returnMsg.equals("Success")){
			msg = "Success";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping(value="uploaded/invoice/{invoiceId}/details")
	public ResponseEntity<JSONObject> getInvoiceDetails(@PathVariable long invoiceId, HttpServletRequest request) {
		try {
			JSONObject data = null;
			data = invoiceService.getInvoiceDetails(invoiceId,request);
			return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<JSONObject>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="uploaded/invoice/{action}")
	public ResponseEntity<JSONObject> supplierApproveInvoice(@PathVariable String action) {
		try {
			JSONObject data = null;
			data = invoiceService.supplierApproveInvoice(action);
			return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<JSONObject>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping(value = "/downloadinvfileformats")
	public  ResponseEntity<Resource> downloadInvFileFormats(HttpServletRequest request) {
		
		File iFolder = new File(fileInvoFormatInPath);
		if (!iFolder.exists()) {
			iFolder.mkdirs();
		}
		
		// filename is InvFileFormat.zip in src folder 
		String filename = fileInvoFormatFileName;
		File file = new File(fileInvoFormatInPath+"/"+fileInvoFormatFileName);
		InputStreamResource resource = null;
		 
		/*if(filename.equals("InvoiceHeader")){
			System.out.println("Header");
			filename = "InvoiceHeader.csv";
			file = new File("src//InvFileFormat/InvoiceHeader.csv");
		}else if(filename.equals("InvoiceDetails")){
			System.out.println("Details");
			filename = "InvoiceDetails.csv";
			file = new File("src//InvFileFormat/InvoiceDetails.csv");
		}else{
			
		}
		*/
		
		
		 
		
		 try {
			 resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 HttpHeaders h = new HttpHeaders();
		 h.add("Content-Disposition", "filename=" + filename);
		 
		    return ResponseEntity.ok()
		            .headers(h)
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType("application/zip"))
		            .body(resource);
	}
	
	
}
