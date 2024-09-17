package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.CompanySupplierMappingRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ContractDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ContractInvoiceHeaderRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SubCompanyRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.CompanySupplierMapping;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.service.ContractService;

@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
@EnableScheduling
public class ContractServiceImpl implements ContractService{
	
	@Autowired
	ContractDetailsRepo contractRepo;
	
	@Autowired
	CompanySupplierMappingRepo comSupMapping;
	
	@Autowired
	SubCompanyRepo subCompanyRepo;
	
	@Autowired
	ContractInvoiceHeaderRepo contractInvoiceHeaderRepo;
	
	@Value("${company.data.path}")
	String companyDataPath;
	
	@Override
	public String addContract(JSONObject contract) {
		// TODO Auto-generated method stub
		ContractDetails contractDetails = new ContractDetails();
		
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			System.out.println("Long----- "+userCompanyID);
			
			 // to find mapping code with company and supplier --- primary key
			CompanySupplierMapping companySupplierMapping = comSupMapping.findTopByCompanyIDAndSupplierID(userCompanyID, Long.valueOf(contract.get("mappingCode").toString())).get();
			
		if (contract.containsKey("upload_board_approval")) {
			String[] boardApprovalData = contract.get("upload_board_approval").toString().split(",");
			byte[] boardApprovalDataBytes = Base64.getDecoder().decode(boardApprovalData[1]);
			String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(boardApprovalDataBytes)).split("/")[1];
			FileUtils.writeByteArrayToFile(new File(companyDataPath+contract.get("contractNo").toString()+"-"+contract.get("category").toString()+"/boardApproval."+mimeType), boardApprovalDataBytes);
			contractDetails.setUploadBoardApproval(companyDataPath+contract.get("contractNo").toString()+"-"+contract.get("category").toString()+"/boardApproval."+mimeType);
		}
		if (contract.containsKey("upload_contract")) {
			String[] uploadContractData = contract.get("upload_contract").toString().split(",");
			byte[] uploadContractDataBytes = Base64.getDecoder().decode(uploadContractData[1]);
			String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(uploadContractDataBytes)).split("/")[1];
			FileUtils.writeByteArrayToFile(new File(companyDataPath+contract.get("contractNo").toString()+"-"+contract.get("category").toString()+"/uploadContract."+mimeType), uploadContractDataBytes);
			contractDetails.setUploadContract(companyDataPath+contract.get("contractNo").toString()+"-"+contract.get("category").toString()+"/uploadContract."+mimeType);
		}
		if (contract.containsKey("upload_purchase_order")) {
			String[] uploadPurchaseOrderData = contract.get("upload_purchase_order").toString().split(",");
			byte[] uploadPurchaseOrderDataBytes = Base64.getDecoder().decode(uploadPurchaseOrderData[1]);
			String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(uploadPurchaseOrderDataBytes)).split("/")[1];
			FileUtils.writeByteArrayToFile(new File(companyDataPath+contract.get("contractNo").toString()+"-"+contract.get("category").toString()+"/uploadPurchaseOrder."+mimeType), uploadPurchaseOrderDataBytes);
			contractDetails.setUploadPurchaseOrder(companyDataPath+contract.get("contractNo").toString()+"-"+contract.get("category").toString()+"/uploadPurchaseOrder."+mimeType);
		}
		
			contractDetails.setContractNo(contract.get("contractNo").toString());
			contractDetails.setBoardApprovalNumber(contract.get("boardApprovalNumber").toString());
			contractDetails.setBoardApprovalOriginatedDateBy(contract.get("boardApprovalOriginatedDateBy").toString());
			
			// Mapping ID
			contractDetails.setMappingCode(companySupplierMapping.getMappingID());
			
			contractDetails.setAnnualPaymentAmountLKR(Double.parseDouble(contract.get("annualPaymentAmountLKR").toString()));
			//Local Date
			contractDetails.setBoardApprovalDate(Instant.ofEpochMilli(new Long(contract.get("boardApprovalDate").toString())).atZone(ZoneId.systemDefault()).toLocalDate()); 
			
			contractDetails.setCategory(contract.get("category").toString());
			contractDetails.setContractAmount(new BigDecimal(contract.get("contractAmount").toString()));
			contractDetails.setAmcusd(new BigDecimal(contract.get("amcusd").toString()));
			contractDetails.setContractDetails(contract.get("contractDetails").toString());
			contractDetails.setContractSignByCompany(contract.get("contractSignByCompany").toString());
			contractDetails.setContractSignBySupplier(contract.get("contractSignBySupplier").toString());
			contractDetails.setConversionRateUSD(new Double(contract.get("conversionRateUSD").toString()));
			contractDetails.setContractSignByCompanyDesignation(contract.get("designationcompany").toString());
			contractDetails.setContractSignBySupplierDesignation(contract.get("designationsupplier").toString());
			// Util Date
			contractDetails.setExpiryDate(new Date(new Long(contract.get("expiryDate").toString()))); 
			contractDetails.setPaymentType(contract.get("paymentType").toString());
			contractDetails.setPurchaseOrderDate(Instant.ofEpochMilli(new Long(contract.get("purchaseOrderDate").toString())).atZone(ZoneId.systemDefault()).toLocalDate()); 
			contractDetails.setBoardPaperDate(Instant.ofEpochMilli(new Long(contract.get("boardPaperDate").toString())).atZone(ZoneId.systemDefault()).toLocalDate()); 
			
			contractDetails.setPurchaseOrderNumber(contract.get("purchaseOrderNumber").toString());
			contractDetails.setSpecialRemarksInTheApprovalPaper(contract.get("specialRemarksInTheApprovalPaper").toString());
			contractDetails.setStartDatePeriod(Instant.ofEpochMilli(new Long(contract.get("purchaseOrderDate").toString())).atZone(ZoneId.systemDefault()).toLocalDate()); 
			
			contractDetails.setContractApprovalStatus("P");
			//contractDetails.setMappingCode(userCompanyID);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * System.out.println("Contract Details IMPL---------------"+contractDetails.
		 * toString());
		 */
		return contractRepo.save(contractDetails).getContractId().toString();
	}

	@Override
	public JSONObject getAllContracts(HttpServletRequest request, String parameter) {
		
		System.out.println("Inside ServiceImpl "+parameter);
		// TODO Auto-generated method stub
		JSONObject data = new JSONObject();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long userCompanyID = new Long(user.getCompanyCode());
		
		System.out.println("Long----- "+userCompanyID);
		try {
			data.put("draw", request.getParameter("draw"));
			String sorting="";
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				sorting = "";
			    break;
			  case "1":
				sorting = "sub.scname";
			    break;
			  case "2":
				sorting = "contractNo";
			    break;
			  case "3":
				sorting = "boardApprovalNumber";
			    break;
			  case "4":
				sorting = "contractAmount";
			    break;
			  case "5":
				  sorting = "boardApprovalDate";
				  break;
			  case "6":
				  sorting = "category";
				  break;
			}
			Page<Object[]> dataList = null;
			
			if(parameter.equals("all")){
				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =contractRepo.getContractDetails(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =contractRepo.getContractDetails(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			}else if(parameter.equals("viewall")){
				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =contractRepo.getContractDetailsForView(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =contractRepo.getContractDetailsForView(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			}else if(parameter.equals("supplier")){ // getContractDetailsForSupplierView
				System.out.println("Inside getContractDetailsForSupplierView >>>>>>>>>");

				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =contractRepo.getContractDetailsForSupplierView(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =contractRepo.getContractDetailsForSupplierView(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			
				
			}
			
			
			JSONArray ar = new JSONArray();
//			System.out.println("Size -------------"+dataList.size());
			 for (Object[] contractDetails : dataList.getContent()) {
				 if(contractDetails[0] instanceof ContractDetails && contractDetails[1] instanceof SubCompany){
					 ContractDetails ph = (ContractDetails) contractDetails[0];
					 SubCompany subcompany = (SubCompany) contractDetails[1];
					 JSONObject header = new JSONObject();
					 
					
						header.put("supplierID", ph.getContractId());
						header.put("supplierName", subcompany.getScname());
						header.put("ContractNo", ph.getContractNo());
						header.put("BoardApprovalDate", ph.getBoardApprovalDate());
						header.put("Amount", ph.getContractAmount());
						header.put("PODate", ph.getPurchaseOrderDate());
						header.put("Category", ph.getCategory());
						
						header.put("expirationDate", ph.getExpiryDate());
						header.put("boardApprovalNo", ph.getBoardApprovalNumber());
						header.put("boardpaperDate", ph.getBoardPaperDate());
						header.put("boardpaperOriginatedBy", ph.getBoardApprovalOriginatedDateBy());
						header.put("signedByCompany", ph.getContractSignByCompany());
						header.put("signedBySupplier", ph.getContractSignBySupplier());
						header.put("poDate", ph.getPurchaseOrderDate());
						header.put("paymentType", ph.getPaymentType());
						header.put("createdBy", ph.getCreatedBy());
						header.put("expiryDate", ph.getExpiryDate());
						header.put("modifiedBy", ph.getModifiedBy());
						
						header.put("ContractID", ph.getContractId());
						header.put("reason", ph.getContractApprovalReason());
						header.put("status", ph.getContractApprovalStatus());
						
						header.put("renewalDate", ph.getRenewalDate());
						
						JSONArray view = new JSONArray();
						view.add(ph.getContractId());
						view.add(ph.getContractId());
						view.add(ph.getContractId());
						view.add(ph.getContractId());
						view.add(ph.getContractId());
						header.put("details", view);
						ar.add(header);
				 }
				
			
/*				 header.put("supplierID", ph.getContractId());
				 header.put("ContractNo", ph.getContractId());
				 header.put("BoardApprovalDate", ph.getBoardApprovalDate());*/
				
				
			}
//			 Optional<Long> count  = poHeaderRepo.getPoDetailsSupplierCount(request.getParameter("search[value]"), code);
			 data.put("recordsTotal",dataList.getTotalElements());
			 data.put("recordsFiltered",dataList.getTotalElements());
			 data.put("data", ar);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	
		}

	@Override
	public JSONObject getPartnership(long id) {
		try {
			Optional<CompanySupplierMapping> data= comSupMapping.findById(id);
			if (data.isPresent()) {
				Optional<SubCompany> com = subCompanyRepo.findById(data.get().getCompanyID());
				Optional<SubCompany> sup = subCompanyRepo.findById(data.get().getSupplierID());
				JSONObject partnershipData = new JSONObject();
				if (com.isPresent() && sup.isPresent()) {
					partnershipData.put("companyName", com.get().getScname());
					partnershipData.put("companyCode", com.get().getScompanycode());
					partnershipData.put("companyAdd1", com.get().getScaddress1());
					partnershipData.put("companyAdd2", com.get().getScaddress2());
					partnershipData.put("companyAdd3", com.get().getScaddress3());
					partnershipData.put("companyContact", com.get().getScphoneno1());
					partnershipData.put("companyEmail", com.get().getScemailadmin());
//					partnershipData.put("companyLogo", com.get().getScomapanylogo());
					partnershipData.put("companyId", com.get().getSubCompanyID());
					
					
					
				}
				
				return partnershipData;
			}else {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject acceptPartnership(long id, String status) {
		try {
		Optional<CompanySupplierMapping> map = comSupMapping.findById(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		if (map.isPresent()) {
			if (map.get().getStatus().equals("P")) {
				map.get().setAccepter(user.getUserid());
				map.get().setAcceptedDateTime(new Date());
				map.get().setStatus(status);
			}
			
		}
		comSupMapping.save(map.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String approveContract(JSONObject data) {
		String returnMsg="Nothing";
		try {
			
			Set<String> keys = data.keySet();
			
			
			for (String key : keys) {
				System.out.println(key);
				System.out.println(data.get(key));
				
			//	Object composeObj = data.get(key);
				//System.out.println(composeObj);
				ContractDetails contractDetail = contractRepo.getOne(Long.parseLong(key.toString()));
				Map address = ((Map)data.get(key)); 
				Iterator<Map.Entry> itr1 = address.entrySet().iterator(); 
		        while (itr1.hasNext()) { 
		            Map.Entry pair = itr1.next(); 
		            System.out.println(pair.getKey() + " : " + pair.getValue()); 
		            
		            if(pair.getKey().equals("statusOfContract")){
		            	 contractDetail.setContractApprovalStatus(pair.getValue().toString());
		            }
		           
		            if(pair.getKey().equals("statusReason")){
		            	contractDetail.setContractApprovalReason(pair.getValue().toString());
		            }
		            
		        } 
		        contractRepo.save(contractDetail);
		        returnMsg = "Success";
			}
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			 returnMsg = "Error";
		}
		
		return returnMsg;
	}

	@Override
	public JSONObject getCompanyContracts(long id, HttpServletRequest request) {
		try {
			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			Page<Object[]> suppliers= null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				sorting = "con.contractNo";
			    break;
			  case "1":
				sorting = "con.contractDetails";
			    break;
			  case "2":
				sorting = "con.category";
			    break;
			  case "3":
				sorting = "con.paymentType";
			    break;
			  case "4":
				sorting = "con.contractAmount";
			    break;
			  case "5":
				sorting = "con.annualPaymentAmountLKR";
				break;
			}
			
			if (request.getParameter("order[0][dir]").equals("asc")) {
				 suppliers = contractInvoiceHeaderRepo.getContractForSuppliers( id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}else {
				suppliers = contractInvoiceHeaderRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			
			
			
			JSONArray ar = new JSONArray();
			long count = suppliers.getTotalElements();
			for (Object[] objects : suppliers.getContent()) {
				
				if (objects[0] instanceof CompanySupplierMapping && objects[1] instanceof ContractDetails) {
					
					JSONObject ob = new JSONObject();
					CompanySupplierMapping mapping = (CompanySupplierMapping) objects[0];
					ContractDetails contract = (ContractDetails) objects[1];
//					ContractInvoice invoice = (ContractInvoice) objects[2];
					
					Optional<Long> daysOb = contractInvoiceHeaderRepo.getContractForSuppliersRenewalDate(contract.getContractNo());
					long days = 0;
							
					if (daysOb.isPresent()) {
						days =daysOb.get().longValue();
					}
					
					if (days == 0) {
						ob.put("contractNo", contract.getContractNo());
						ob.put("desc", contract.getContractDetails());
						ob.put("category", contract.getCategory());
						ob.put("paymentType", contract.getPaymentType());
						ob.put("amount", contract.getContractAmount());
						ob.put("annualPayment", contract.getAnnualPaymentAmountLKR());
						
						JSONObject view = new JSONObject();
						view.put("mapid", mapping.getMappingID());
						view.put("contractId", contract.getContractId());
						view.put("contractNumber", contract.getContractNo());
						view.put("monthly", contract.getMonthlyPaymentAmountLKR());
						view.put("status", 1);
						ob.put("view", view);
						ar.add(ob);
					}else {
						count-=1;
						JSONObject view = new JSONObject();
						view.put("contractId", contract.getContractNo());
						view.put("monthly", contract.getMonthlyPaymentAmountLKR());
						view.put("status", 0);
						view.put("renew", LocalDate.now().plusDays(days));
						ob.put("view", view);
					}
					
					
					
					
			    }
				
			}
			data.put("recordsTotal",  count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	
	@Override
	public JSONObject getcontractlist(String search, Long page) {
		// TODO Auto-generated method stub
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			JSONObject ob = new JSONObject();
			
			int pagevalue;
			if (page == null) {
				pagevalue = 0;
			}else {
				pagevalue = page.intValue();
			}
			//long count = 0;
			//List<ContractDetails> contractdetails = null;
			List<ContractDetails> contractdetails = contractRepo.findByContractNoLike( search+"%",PageRequest.of(pagevalue, 30));
			long count = contractRepo.countByContractNoLike(search+"%");
			ob.put("total_count", count);
			if ((pagevalue*30)<count) {
				ob.put("incomplete_results", true);
			}else {
				ob.put("incomplete_results", false);
			}
			ArrayList data = new ArrayList();
			for (ContractDetails contracsz : contractdetails) {
				
					JSONObject con = new JSONObject();
					con.put("name", contracsz.getContractDetails());
					con.put("id", contracsz.getContractNo());
					data.add(con);
				
				
			}
			ob.put("items", data);
			
			
			return ob;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

////	@Override
//	@Scheduled(cron = "${cron.expression}")
//	public void raisInvoice() {
//		try {
//			List<ContractDetails> cd = contractRepo.getTodayRenewInvoice();
//			
//			List<ContractInvoice> invoices = new ArrayList<ContractInvoice>();
//			for (ContractDetails contractDetails : cd) {
//				ContractInvoice invoice = new ContractInvoice();
//				invoice.setMappingid(contractDetails.getMappingCode());
//				invoice.setContractno(contractDetails.getContractNo());
//				invoice.setInvoicedate(LocalDate.now());
////				invoice.setTotal(contractDetails.getMonthlyPaymentAmountLKR());
////				invoice.setNetamount(contractDetails.getMonthlyPaymentAmountLKR());
//				invoice.setStatus("P");
//				invoices.add(invoice);
//			}
//			
//			contractInvoiceRepo.saveAll(invoices);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	
//	@Override
	/*
	 * @Scheduled(cron = "${cron.expression}") public void raisInvoice() { try {
	 * System.out.println("BB -->"+ LocalDate.now()); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

}
