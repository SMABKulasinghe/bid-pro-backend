package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.ContractDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ContractInvoiceHeaderRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.PaymentHeaderRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractInvoiceHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.PaymentHeader;
import lk.supplierUMS.SupplierUMS_REST.service.PaymentService;

@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	PaymentHeaderRepo paymentHeaderRepo;
	
	@Autowired
	ContractInvoiceHeaderRepo contractInvoiceHeaderRepo;
	
	@Autowired
	ContractDetailsRepo contractDetailsRepo;
	

	@Override
	public String approveInvoicesForPayment(JSONObject data) {
		// TODO Auto-generated method stub
		String returnMsg="Nothing";
		try {
			
			Set<String> keys = data.keySet();
			
			
			for (String key : keys) {
				System.out.println(key);
				System.out.println(data.get(key));
				
			//	Object composeObj = data.get(key);
				//System.out.println(composeObj);
				
				ContractInvoiceHeader contractInvoiceHeader = contractInvoiceHeaderRepo.getOne(Long.parseLong(key.toString()));
				
				
				PaymentHeader paymentHeader = new PaymentHeader();
				
				
				
				Map address = ((Map)data.get(key)); 
				Iterator<Map.Entry> itr1 = address.entrySet().iterator(); 
		        while (itr1.hasNext()) { 
		            Map.Entry pair = itr1.next(); 
		            System.out.println(pair.getKey() + " : " + pair.getValue()); 
		            
		            if(pair.getKey().equals("statusOfContract")){
		            	paymentHeader.setApprovedUser1Status(pair.getValue().toString());
		            }
		           
		            if(pair.getKey().equals("statusReason")){
		            	paymentHeader.setApprovedUser1Reason(pair.getValue().toString());
		            }
		            paymentHeader.setPaymentstatus("P");
		            paymentHeader.setInvoiceId(Long.parseLong(key.toString()));
		        } 
		        paymentHeaderRepo.save(paymentHeader);
		        
		        contractInvoiceHeader.setStatus("PP");
		        contractInvoiceHeader.setApprovecomplete("YY");
				contractInvoiceHeaderRepo.save(contractInvoiceHeader);
				
		        returnMsg = "Success";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg = "Error";
		}
		
		
		return returnMsg;
	}


	@Override
	public String addForPayment(JSONObject data) {
		// TODO Auto-generated method stub
		String returnMsg="Nothing";
		try {
			
			System.out.println("DATARECEIVED----"+data.get("id"));
			
			 if(data.get("id").equals("1")){
	            	System.out.println("INSIDE");
	            }
			
			ContractInvoiceHeader contractInvoiceHeader = contractInvoiceHeaderRepo.getOne(Long.parseLong(data.get("id").toString()));
		    contractInvoiceHeader.setStatus("P");
		    contractInvoiceHeader.setApprovecomplete("YP");
		    
		    contractInvoiceHeaderRepo.save(contractInvoiceHeader);
				
			
			
			PaymentHeader paymentHeader = new PaymentHeader();
			paymentHeader.setPaymentstatus("P");
            paymentHeader.setInvoiceId(Long.parseLong(data.get("id").toString()));
            
            paymentHeaderRepo.save(paymentHeader);
            
            ContractDetails contractDetails = contractDetailsRepo.findById(contractInvoiceHeader.getContractid()).get();
            
            System.out.println("contractDetails.getContractAmount()>>>>>>>> "+contractDetails.getContractAmount());
            System.out.println("new BigDecimal(data.get) amountPaid > "+new BigDecimal(data.get("amountPaid").toString()));
            
            contractDetails.setContractOutstandingAmount((contractDetails.getContractAmount().subtract(new BigDecimal(data.get("amountPaid").toString()))));
            
            contractDetailsRepo.save(contractDetails);
            
            returnMsg = "Success";
            
		//	Set<String> keys = data.keySet();
			
			
			/*for (String key : keys) {
				System.out.println(key);
				System.out.println(data.get(key));
				
			
					
				
				
				
				
				Map address = ((Map)data.get(key)); 
				Iterator<Map.Entry> itr1 = address.entrySet().iterator(); 
		        while (itr1.hasNext()) { 
		            Map.Entry pair = itr1.next(); 
		            System.out.println(pair.getKey() + " : " + pair.getValue()); 
		            
		            if(pair.getKey().equals("statusOfContract")){
		            	paymentHeader.setApprovedUser1Status(pair.getValue().toString());
		            }
		           
		            if(pair.getKey().equals("statusReason")){
		            	paymentHeader.setApprovedUser1Reason(pair.getValue().toString());
		            }
		            paymentHeader.setPaymentstatus("P");
		            paymentHeader.setInvoiceId(Long.parseLong(key.toString()));
		        } 
		   //     paymentHeaderRepo.save(paymentHeader);
		        
		        contractInvoiceHeader.setStatus("PP");
		        contractInvoiceHeader.setApprovecomplete("YY");
			//	contractInvoiceHeaderRepo.save(contractInvoiceHeader);
				
		        returnMsg = "Success";
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg = "Error";
		}
		
		
		return returnMsg;
	}
	

}
