package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.CompanySupplierMappingRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ContractDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ContractInvoiceDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ContractInvoiceHeaderRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SubCompanyRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.TempContractInvoiceDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.TempContractInvoiceHeaderRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRepo;
import lk.supplierUMS.SupplierUMS_REST.comman.ConnectAPI;
import lk.supplierUMS.SupplierUMS_REST.comman.DeEnCode;
import lk.supplierUMS.SupplierUMS_REST.entity.CompanySupplierMapping;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractInvoiceDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractInvoiceHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TempContractInvoiceDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TempContractInvoiceHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.service.InvoiceService;
import springfox.documentation.spring.web.json.Json;

@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class InvoiceServiceImpl implements InvoiceService{
	
	@Autowired
	UserRepo userRepo;

	@Autowired
	SubCompanyRepo subcompanyRepo;
	
	@Autowired
	ContractDetailsRepo contractDetailsRepo;
	
	@Autowired
	CompanySupplierMappingRepo companySupplierRepo;
	
	@Autowired
	ContractInvoiceHeaderRepo contractInvoiceHeaderRepo;
	
	@Autowired
	ContractInvoiceDetailsRepo contractInvoiceDetailsRepo;
	
	@Autowired
	TempContractInvoiceHeaderRepo tempConInvoiceHeaderRepo;
	
	@Autowired
	TempContractInvoiceDetailsRepo tempContractInvoiceDetailsRepo;
	

	@Value("${invoice.data.path}")
	private String invPath;

	@Override
	public ResponseEntity issueInvoice(JSONObject data) {
		try {
			
			System.out.println(data);
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			tempConInvoiceHeaderRepo.deleteAll(tempConInvoiceHeaderRepo.findByUpdateduser(user.getUserid()));
			tempContractInvoiceDetailsRepo.deleteAll(tempContractInvoiceDetailsRepo.findByUpdateduser(user.getUserid()));
			
			Reader reader1 = null;
			Reader reader2 = null;
			List<String> uploadedHeaderTitles = new ArrayList<String>();
			List<String> uploadedDetailsTitles = new ArrayList<String>();
			
			ArrayList<String> HeaderTitles = new ArrayList<>();
			ArrayList<String> DetailsTitles = new ArrayList<>();
			
			if (data.containsKey("mdl_ii_uploaded_invoice_copy")) {
				String img = data.get("mdl_ii_uploaded_invoice_copy").toString().split(",")[1];
				String ext = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(img.getBytes()));
				System.out.println("ext--------------"+ext);
				String imageData[]=data.get("mdl_ii_uploaded_invoice_copy").toString().split("base64,");
				String image=imageData[1].substring(0, (imageData[1].length()));
				new DeEnCode().decodeMethod(invPath+data.get("contractId").toString(),LocalDate.now().toString()+"_image", "png", image);
			}
			
			if (data.containsKey("mdl_ii_uploaded_invoice_header")) {
				String headerData[]=data.get("mdl_ii_uploaded_invoice_header").toString().split("base64,");
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(invPath+data.get("contractId").toString(),LocalDate.now().toString()+"_head", "csv", head);
				
				reader1 = Files.newBufferedReader(Paths.get(invPath+data.get("contractId").toString()+"/"+LocalDate.now().toString()+"_head.csv"));
				CSVParser csvParser1 = new CSVParser(reader1, CSVFormat.DEFAULT.withTrim());
				uploadedHeaderTitles = IteratorUtils.toList(csvParser1.getRecords().get(0).iterator());
				
				 HeaderTitles.add("Contract Number");
		         HeaderTitles.add("Invoice Number");
		         HeaderTitles.add("Invoice Date");
		         HeaderTitles.add("Invoice Time");
		         HeaderTitles.add("Location Code");
		         HeaderTitles.add("Location Name");
		         HeaderTitles.add("Total Line discount");
		         HeaderTitles.add("Invoice Value");
		         HeaderTitles.add("Invoice Tax");
		         HeaderTitles.add("Net amount");
		         HeaderTitles.add("NoofLines");
		         HeaderTitles.add("BatchNo");
			}
			
			if (data.containsKey("mdl_ii_uploaded_invoice_body")) {
				String detailsData[]=data.get("mdl_ii_uploaded_invoice_body").toString().split("base64,");
				String details=detailsData[1].substring(0, (detailsData[1].length()));
				new DeEnCode().decodeMethod(invPath+data.get("contractId").toString(),LocalDate.now().toString()+"_details", "csv", details);
				
				reader2 = Files.newBufferedReader(Paths.get(invPath+data.get("contractId").toString()+"/"+LocalDate.now().toString()+"_details.csv"));
				CSVParser csvParser2 = new CSVParser(reader2, CSVFormat.DEFAULT.withTrim());   
				uploadedDetailsTitles = IteratorUtils.toList(csvParser2.getRecords().get(0).iterator());
				
				 DetailsTitles.add("Contract Number");
		         DetailsTitles.add("Invoice Number");
		         DetailsTitles.add("Item Code");
		         DetailsTitles.add("Line Description");
		         DetailsTitles.add("Item Quantity");
		         DetailsTitles.add("Item Amount");
		         DetailsTitles.add("Unit of Mature");
		         DetailsTitles.add("Unit Price");
		         DetailsTitles.add("Service Charge");
		         DetailsTitles.add("Item Line Discount");
		         DetailsTitles.add("Location Code");
		         DetailsTitles.add("Location Name");
		         DetailsTitles.add("Line No");
		         DetailsTitles.add("BatchNo");
		         DetailsTitles.add("Line Amount");
			}
				
		         
		        if (uploadedHeaderTitles.containsAll(HeaderTitles) && data.containsKey("mdl_ii_uploaded_invoice_header")) {
		        	
		        	 reader1 = Files.newBufferedReader(Paths.get(invPath+data.get("contractId").toString()+"/"+LocalDate.now().toString()+"_head.csv"));
		        	 
		        	 
		        	  CSVParser csvParserHead = new CSVParser(reader1, CSVFormat.DEFAULT                       
		                 		 .withFirstRecordAsHeader()
		                         .withIgnoreHeaderCase()
		                         .withTrim());
		        	  
		        	String dateFormat = null;
		        	String timeFormat = null;
		        	
		        	Optional<List<Object[]>> contract = contractDetailsRepo.getContractCompanyDetails(Long.valueOf(user.getCompanyCode()),Long.valueOf(data.get("contractId").toString()));
		        		if (contract.isPresent()) {
							List<Object[]> con = contract.get();
							if (con.get(0)[0] instanceof ContractDetails && con.get(0)[1] instanceof CompanySupplierMapping && con.get(0)[2] instanceof SubCompany) {
								SubCompany sc = (SubCompany) con.get(0)[2];
								dateFormat = sc.getDateformat();
								timeFormat = sc.getTimeformat();
							}
						}
		        		
		        	for (CSVRecord csvRecord : csvParserHead) {

		        		TempContractInvoiceHeader hdata = new TempContractInvoiceHeader();
						
						hdata.setContractno(csvRecord.get("Contract Number"));
						hdata.setInvoicenumber(csvRecord.get("Invoice Number"));
						DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat);
						hdata.setInvoicedate(LocalDate.parse(csvRecord.get("Invoice Date"),format));
//						DateTimeFormatter timeformat = DateTimeFormatter.ofPattern(timeFormat);
						hdata.setInvoicetime(LocalTime.parse(csvRecord.get("Invoice Time")));
						hdata.setLocationcode(csvRecord.get("Location Code"));
						hdata.setLocationname(csvRecord.get("Location Name"));
						hdata.setLinediscount(new BigDecimal(csvRecord.get("Total Line discount")));
						hdata.setTotal(new BigDecimal(csvRecord.get("Invoice Value")));
						hdata.setTotaltax(new BigDecimal(csvRecord.get("Invoice Tax")));
						hdata.setNetamount(new BigDecimal(csvRecord.get("Net amount")));
						hdata.setNumberoflines(Long.valueOf(csvRecord.get("NoofLines")));
						hdata.setBatchnumber(csvRecord.get("BatchNo"));
						hdata.setContractid(Long.valueOf(data.get("contractId").toString()));
						hdata.setMappingid(Long.valueOf(data.get("mapping").toString()));
						hdata.setStatus("P");
						
						TempContractInvoiceHeader savedHeader =  tempConInvoiceHeaderRepo.saveAndFlush(hdata);
						
						if (savedHeader != null && uploadedDetailsTitles.containsAll(DetailsTitles)&& data.containsKey("mdl_ii_uploaded_invoice_body")) {
							reader2 = Files.newBufferedReader(Paths.get(invPath+data.get("contractId").toString()+"/"+LocalDate.now().toString()+"_details.csv"));
							System.out.println("data saved");
							 CSVParser csvParserDetails = new CSVParser(reader2, CSVFormat.DEFAULT                       
			                 		 .withFirstRecordAsHeader()
			                         .withIgnoreHeaderCase()
			                         .withTrim());
							 
							 ArrayList<TempContractInvoiceDetails> det = new ArrayList<TempContractInvoiceDetails>();
							 for (CSVRecord csvRecord2 : csvParserDetails) {
								TempContractInvoiceDetails ddata = new TempContractInvoiceDetails();
								
								ddata.setContractno(csvRecord2.get("Contract Number"));
								ddata.setInvoicenumber(csvRecord2.get("Invoice Number"));
								ddata.setItemcode(csvRecord2.get("Item Code"));
								ddata.setLinedescription(csvRecord2.get("Line Description"));
								ddata.setItemquantity(Long.valueOf(csvRecord2.get("Item Quantity")));
								ddata.setItemamount(new BigDecimal(csvRecord2.get("Item Amount")));
								ddata.setUom(csvRecord2.get("Unit of Mature"));
								ddata.setUnitprice(new BigDecimal(csvRecord2.get("Unit Price")));
								ddata.setServicecharge(new BigDecimal(csvRecord2.get("Service Charge")));
								ddata.setLinediscount(new BigDecimal(csvRecord2.get("Item Line Discount")));
								ddata.setLocationcode(csvRecord2.get("Location Code"));
								ddata.setLocationname(csvRecord2.get("Location Name"));
								ddata.setLinenumber(csvRecord2.get("Line No"));
								ddata.setLineamount(new BigDecimal(csvRecord2.get("Line Amount").equals("")?"0.00":csvRecord2.get("Line Amount")));
								ddata.setInvoiceheaderid(savedHeader.getInvoiceheaderid());
								
								det.add(ddata);
								
							}
							 tempContractInvoiceDetailsRepo.saveAll(det);
							 csvParserDetails.close();
						}
						
		        	}
		        	csvParserHead.close();
		        	Validation(user.getUserid(),data.get("contractNumber").toString());
		        	System.out.println("contract No == "+data.get("contractNumber").toString() );
		        	return new ResponseEntity<>(HttpStatus.OK);
		        }else {
		        	System.out.println("else");
		        	return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		        }
			
			
	         
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public void Validation(String userId,String contractNo) {
		try {
			List<TempContractInvoiceHeader> head = tempConInvoiceHeaderRepo.findByUpdateduser(userId);
			for (TempContractInvoiceHeader tempContractInvoiceHeader : head) {
				System.out.println(tempContractInvoiceHeader.getContractno().trim().equalsIgnoreCase(contractNo.trim()));
				System.out.println(tempContractInvoiceHeader.getContractno().trim());
				System.out.println(contractNo.trim());
				if (!tempContractInvoiceHeader.getContractno().trim().equalsIgnoreCase(contractNo.trim())) {
					tempContractInvoiceHeader.setInvoiceRejectedReason("invalid contract Number");
					tempContractInvoiceHeader.setStatus("R");
				}
				if (contractInvoiceHeaderRepo.alreadyProcessedInvoice(tempContractInvoiceHeader.getMappingid(), tempContractInvoiceHeader.getContractno(), tempContractInvoiceHeader.getInvoicenumber())) {
					tempContractInvoiceHeader.setInvoiceRejectedReason((tempContractInvoiceHeader.getInvoiceRejectedReason()+"").contains("null")?" Invoice Number Already exists":tempContractInvoiceHeader.getInvoiceRejectedReason()+" & Invoice Number Already exists");
					tempContractInvoiceHeader.setStatus("R");
				}
				if (!tempContractInvoiceHeader.getTotal().setScale(2, RoundingMode.CEILING).equals(contractInvoiceHeaderRepo.BodyAmmount(tempContractInvoiceHeader.getMappingid(),  tempContractInvoiceHeader.getContractno(), tempContractInvoiceHeader.getInvoicenumber()).setScale(2, RoundingMode.CEILING))) {
					tempContractInvoiceHeader.setInvoiceRejectedReason((tempContractInvoiceHeader.getInvoiceRejectedReason()+"").contains("null")?"Invoice Ammounts Mismatched":tempContractInvoiceHeader.getInvoiceRejectedReason()+" & Invoice Ammounts Mismatched");
					tempContractInvoiceHeader.setStatus("R");
				}
				
			}
			tempConInvoiceHeaderRepo.saveAll(head);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public JSONObject getAllInvoices(HttpServletRequest request, String parameter) {
		System.out.println("Inside ServiceImpl");
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
				sorting = "contractno";
			    break;
			  case "3":
				sorting = "invoicedate";
			    break;
			  case "4":
				sorting = "invoicenumber";
			    break;
			  case "5":
				  sorting = "total";
				  break;
			  case "6":
				  sorting = "batchnumber";
				  break;
			}
			Page<Object[]> dataList = null;
			
			if(parameter.equals("all")){
				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =contractInvoiceHeaderRepo.getInvoiceHeaders(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =contractInvoiceHeaderRepo.getInvoiceHeaders(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			}else if(parameter.equals("supplier")){
				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =contractInvoiceHeaderRepo.getInvoiceHeadersForSupplier(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =contractInvoiceHeaderRepo.getInvoiceHeadersForSupplier(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			}else if(parameter.equals("company")){
				System.out.println("ELSE FOR COMPANY");

				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =contractInvoiceHeaderRepo.getInvoiceHeadersForCompany(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =contractInvoiceHeaderRepo.getInvoiceHeadersForCompany(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			
			}
			
			
			JSONArray ar = new JSONArray();
//			System.out.println("Size -------------"+dataList.size());
			 for (Object[] contractDetails : dataList.getContent()) {
				 if(contractDetails[0] instanceof ContractInvoiceHeader && contractDetails[1] instanceof SubCompany){
					 ContractInvoiceHeader ph = (ContractInvoiceHeader) contractDetails[0];
					 SubCompany subcompany = (SubCompany) contractDetails[1];
					 JSONObject header = new JSONObject();
					 
					
					 	header.put("supplierID", ph.getInvoiceheaderid());
						header.put("supplierName", subcompany.getScname());
						header.put("ContractNo", ph.getContractno());
						header.put("ContractID", ph.getContractid());
												
						header.put("InvoiceDate", ph.getInvoicedate());
						header.put("InvoiceNumber", ph.getInvoicenumber());
						header.put("BatchNumber", ph.getBatchnumber());
						header.put("InvoiceTime", ph.getInvoicetime());
						header.put("LocationCode", ph.getLocationcode());
						header.put("LocationName", ph.getLocationname());
						header.put("LineDiscount", ph.getLinediscount());
						header.put("TotalTax", ph.getTotaltax());
						header.put("NetAmount", ph.getNetamount());
						
						header.put("TotalAmount", ph.getTotal());
						header.put("PODate", subcompany.getScname());
						
						
						header.put("NumberofLines", ph.getNumberoflines());
						header.put("Status", ph.getStatus());
						header.put("Reason", ph.getApprovedUser1Reason());
						
						
						
						
						header.put("boardApprovalNo", subcompany.getScname());
						header.put("boardpaperDate", subcompany.getScname());
						header.put("boardpaperOriginatedBy", subcompany.getScname());
						header.put("signedByCompany", subcompany.getScname());
						header.put("signedBySupplier", subcompany.getScname());
						
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
	public JSONObject getUploadedInvoices(HttpServletRequest request) {
		try {
			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			data.put("draw", request.getParameter("draw"));
			String sorting="";
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				sorting = "contractno";
			    break;
			  case "1":
				sorting = "invoicenumber";
			    break;
			  case "2":
				sorting = "invoicedate";
			    break;
			  case "3":
				sorting = "linediscount";
			    break;
			  case "4":
				sorting = "total";
			    break;
			  case "5":
				  sorting = "totaltax";
				  break;
			  case "6":
				  sorting = "netamount";
				  break;
			  case "7":
				  sorting = "batchnumber";
				  break;
			}
			Page<TempContractInvoiceHeader> dataList;
			if (request.getParameter("order[0][dir]").equals("asc")) {
				dataList =tempConInvoiceHeaderRepo.getUploadedInvoices(request.getParameter("search[value]"), user.getUserid(), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}else {
				dataList =tempConInvoiceHeaderRepo.getUploadedInvoices(request.getParameter("search[value]"), user.getUserid(), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			JSONArray ar = new JSONArray();
			for (TempContractInvoiceHeader tempContractInvoiceHeader : dataList.getContent()) {
				JSONObject header = new JSONObject();
				header.put("contractNo", tempContractInvoiceHeader.getContractno());
				header.put("invoiceNo", tempContractInvoiceHeader.getInvoicenumber());
				header.put("invoiceDate", tempContractInvoiceHeader.getInvoicedate());
				header.put("discount", tempContractInvoiceHeader.getLinediscount());
				header.put("total", tempContractInvoiceHeader.getTotal().setScale(2, RoundingMode.CEILING));
				header.put("totaltax", tempContractInvoiceHeader.getTotaltax().setScale(2, RoundingMode.CEILING));
				header.put("netAmount", tempContractInvoiceHeader.getNetamount().setScale(2, RoundingMode.CEILING));
				header.put("btch", tempContractInvoiceHeader.getBatchnumber());
				JSONObject status = new JSONObject();
				status.put("status", tempContractInvoiceHeader.getStatus());
				status.put("reason", tempContractInvoiceHeader.getInvoiceRejectedReason());
				
				header.put("status", status);
				header.put("view", tempContractInvoiceHeader.getInvoiceheaderid());
				ar.add(header);
			}
			 data.put("recordsTotal",dataList.getTotalElements());
			 data.put("recordsFiltered",dataList.getTotalElements());
			 data.put("data", ar);
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String approveInvoices(JSONObject data) {
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
				Map address = ((Map)data.get(key)); 
				Iterator<Map.Entry> itr1 = address.entrySet().iterator(); 
		        while (itr1.hasNext()) { 
		            Map.Entry pair = itr1.next(); 
		            System.out.println(pair.getKey() + " : " + pair.getValue()); 
		            
		            if(pair.getKey().equals("statusOfContract")){
		            	contractInvoiceHeader.setApprovedUser1Status(pair.getValue().toString());
		            }
		           
		            if(pair.getKey().equals("statusReason")){
		            	contractInvoiceHeader.setApprovedUser1Reason(pair.getValue().toString());
		            }
		            contractInvoiceHeader.setApprovecomplete("Y");
		            ContractDetails contractDetails =  contractDetailsRepo.findById(contractInvoiceHeader.getContractid()).get();
		            
		           
		            LocalDate reminderDate =  contractDetails.getBoardApprovalDate().plusDays(contractDetails.getRenewalDatePeriod());
		            
		            Date reminderDateDateObj = Date.from(reminderDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		            
		            if(reminderDateDateObj.before(contractDetails.getExpiryDate())){
		            	System.out.println("Fulfilled All date conditions for the contract "+contractDetails.getBoardApprovalDate().plusDays(contractDetails.getRenewalDatePeriod()));
		            	 contractDetails.setReminderDate(contractDetails.getBoardApprovalDate().plusDays(contractDetails.getRenewalDatePeriod()));;
		            	 contractDetailsRepo.save(contractDetails);
		            }
		            
		            /* In order to do the above, 
		             * contract boardApprovalDate(x) plus renewalDatePeriod(y) 
		             * should be less than expireDate then does the update to reminderDate(x+y)
		             * 		              
		             */
		            
		        } 
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
	public JSONObject getInvoiceDetails(long invoiceId, HttpServletRequest request) {
		try {
			System.out.println("invoiceId"+invoiceId);
			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			data.put("draw", request.getParameter("draw"));
			
			String sorting="";
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				sorting = "itemcode";
			    break;
			  case "1":
				sorting = "unitprice";
			    break;
			  case "2":
				sorting = "itemquantity";
			    break;
			  case "3":
				sorting = "itemamount";
			    break;
			  case "4":
				sorting = "servicecharge";
			    break;
			  case "5":
				  sorting = "linediscount";
				  break;
			  case "6":
				  sorting = "lineamount";
				  break;
			}
			Page<TempContractInvoiceDetails> dataList;
			if (request.getParameter("order[0][dir]").equals("asc")) {
				dataList =tempContractInvoiceDetailsRepo.getUploadedInvoicesDetails(request.getParameter("search[value]"), invoiceId, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}else {
				dataList =tempContractInvoiceDetailsRepo.getUploadedInvoicesDetails(request.getParameter("search[value]"), invoiceId, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			JSONArray ar = new JSONArray();
			for (TempContractInvoiceDetails tempContractInvoiceDetails : dataList.getContent()) {
				JSONObject ob = new JSONObject();
				ob.put("itemCode", tempContractInvoiceDetails.getItemcode());
				ob.put("unitPrice", tempContractInvoiceDetails.getUnitprice());
				ob.put("qty", tempContractInvoiceDetails.getItemquantity());
				ob.put("amount", tempContractInvoiceDetails.getItemamount());
				ob.put("serviceCharge", tempContractInvoiceDetails.getServicecharge());
				ob.put("discount", tempContractInvoiceDetails.getLinediscount());
				ob.put("value", tempContractInvoiceDetails.getLinediscount());
				ar.add(ob);
			}
			 data.put("recordsTotal",dataList.getTotalElements());
			 data.put("recordsFiltered",dataList.getTotalElements());
			 data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject supplierApproveInvoice(String action) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			if (action.equalsIgnoreCase("approve")) {
				List<TempContractInvoiceHeader> header = tempConInvoiceHeaderRepo.findByUpdateduser(user.getUserid());
				
				for (TempContractInvoiceHeader tempContractInvoiceHeader : header) {
					if (!tempContractInvoiceHeader.getStatus().equalsIgnoreCase("R")) {
						ContractInvoiceHeader head = new ContractInvoiceHeader();
						
						head.setContractno(tempContractInvoiceHeader.getContractno());
						head.setInvoicenumber(tempContractInvoiceHeader.getInvoicenumber());
						head.setInvoicedate(tempContractInvoiceHeader.getInvoicedate());
						head.setInvoicetime(tempContractInvoiceHeader.getInvoicetime());
						head.setLocationcode(tempContractInvoiceHeader.getLocationcode());
						head.setLocationname(tempContractInvoiceHeader.getLocationname());
						head.setLinediscount(tempContractInvoiceHeader.getLinediscount());
						head.setTotal(tempContractInvoiceHeader.getTotal());
						head.setTotaltax(tempContractInvoiceHeader.getTotaltax());
						head.setNetamount(tempContractInvoiceHeader.getNetamount());
						head.setNumberoflines(tempContractInvoiceHeader.getNumberoflines());
						head.setBatchnumber(tempContractInvoiceHeader.getBatchnumber());
						head.setContractid(tempContractInvoiceHeader.getContractid());
						head.setMappingid(tempContractInvoiceHeader.getMappingid());
						head.setStatus(tempContractInvoiceHeader.getStatus());
						head.setApprovecomplete("N");
						
						ContractInvoiceHeader headSaved = contractInvoiceHeaderRepo.saveAndFlush(head);
						System.out.println("condition--------------"+headSaved != null);
						if (headSaved != null) {
							List<TempContractInvoiceDetails> details = tempContractInvoiceDetailsRepo.findByUpdateduserAndInvoiceheaderid(user.getUserid(), tempContractInvoiceHeader.getInvoiceheaderid());
							System.out.println("Size-----------"+details.size());
							for (TempContractInvoiceDetails tempContractInvoiceDetails : details) {
								
								ContractInvoiceDetails detail = new ContractInvoiceDetails();
								
								detail.setContractno(tempContractInvoiceDetails.getContractno());
								detail.setInvoicenumber(tempContractInvoiceDetails.getInvoicenumber());
								detail.setItemcode(tempContractInvoiceDetails.getItemcode());
								detail.setLinedescription(tempContractInvoiceDetails.getLinedescription());
								detail.setItemquantity(tempContractInvoiceDetails.getItemquantity());
								detail.setItemamount(tempContractInvoiceDetails.getItemamount());
								detail.setUom(tempContractInvoiceDetails.getUom());
								detail.setUnitprice(tempContractInvoiceDetails.getUnitprice());
								detail.setServicecharge(tempContractInvoiceDetails.getServicecharge());
								detail.setLinediscount(tempContractInvoiceDetails.getLinediscount());
								detail.setLocationcode(tempContractInvoiceDetails.getLocationcode());
								detail.setLocationname(tempContractInvoiceDetails.getLocationname());
								detail.setLinenumber(tempContractInvoiceDetails.getLinenumber());
								detail.setLineamount(tempContractInvoiceDetails.getLineamount());
								detail.setInvoiceheaderid(headSaved.getInvoiceheaderid());
								
								contractInvoiceDetailsRepo.saveAndFlush(detail);
							}
						}
						CompanySupplierMapping csm = companySupplierRepo.findById(head.getMappingid()).get();
						
						List<String> tokens = userRepo.getFCMUsersByRoleOption(csm.getCompanyID(), "Invoice Authorization");
						
						JSONObject data = new JSONObject();
						JSONObject body = new JSONObject();
						
						SubCompany sc = subcompanyRepo.findById(csm.getSupplierID()).get();
						
						body.put("type", "Invoice Raised");
						body.put("company", sc.getScname());
						data.put("data", body);
						data.put("registration_ids", tokens);
						HttpURLConnection dataConnection = new ConnectAPI().sendFCMRequest("https://fcm.googleapis.com/fcm/send", data);
						
						
						tempConInvoiceHeaderRepo.deleteAll(tempConInvoiceHeaderRepo.findByUpdateduser(user.getUserid()));
						tempContractInvoiceDetailsRepo.deleteAll(tempContractInvoiceDetailsRepo.findByUpdateduser(user.getUserid()));
						
					}
					
				}
			}else {
				tempConInvoiceHeaderRepo.deleteAll(tempConInvoiceHeaderRepo.findByUpdateduser(user.getUserid()));
				tempContractInvoiceDetailsRepo.deleteAll(tempContractInvoiceDetailsRepo.findByUpdateduser(user.getUserid()));
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public JSONObject getAllInvoicesToBeApprovedForPayment(HttpServletRequest request, String para) {
		// TODO Auto-generated method stub

		System.out.println("Inside ServiceImpl");
		JSONObject data = new JSONObject();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long userCompanyID = new Long(user.getCompanyCode());
		System.out.println("Long----- "+userCompanyID);
		//SubCompany subCompany  = subcompanyRepo.findById(userCompanyID).get();
		try {
			data.put("draw", request.getParameter("draw"));
			String sorting="";
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				sorting = "sub.scompanycode";
			    break;
			  case "1":
				sorting = "sub.scname";
			    break;
			  case "2":
				sorting = "contractno";
			    break;
			  case "3":
				sorting = "invoicedate";
			    break;
			  case "4":
				sorting = "invoicenumber";
			    break;
			  case "5":
				  sorting = "total";
				  break;
			  case "6":
				  sorting = "batchnumber";
				  break;
			}
			Page<Object[]> dataList = null;
			
			if(para.equals("all")){
				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =contractInvoiceHeaderRepo.getInvoiceHeadersForPayment(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =contractInvoiceHeaderRepo.getInvoiceHeadersForPayment(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			}else if(para.equals("outstanding")){
				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =contractInvoiceHeaderRepo.getInvoiceHeadersForOutstandingPaymentForSupplier(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =contractInvoiceHeaderRepo.getInvoiceHeadersForOutstandingPaymentForSupplier(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			}else if(para.equals("outstandingSupplierView")){
				System.out.println("outstandingSupplierView-------- ");
				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =contractInvoiceHeaderRepo.getInvoiceHeadersForOutstandingPaymentForSuppliersView(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =contractInvoiceHeaderRepo.getInvoiceHeadersForOutstandingPaymentForSuppliersView(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}							
			} else{
				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =contractInvoiceHeaderRepo.getInvoiceHeadersForPaymentForSupplier(request.getParameter("search[value]"), userCompanyID, new Long(para), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =contractInvoiceHeaderRepo.getInvoiceHeadersForPaymentForSupplier(request.getParameter("search[value]"), userCompanyID, new Long(para), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			}
			
			
			JSONArray ar = new JSONArray();
//			System.out.println("Size -------------"+dataList.size());
			 for (Object[] contractDetails : dataList.getContent()) {
				 if(contractDetails[0] instanceof ContractInvoiceHeader && contractDetails[1] instanceof SubCompany){
					 ContractInvoiceHeader ph = (ContractInvoiceHeader) contractDetails[0];
					 SubCompany subcompany = (SubCompany) contractDetails[1];
					 JSONObject header = new JSONObject();
					 if (contractDetails.length>2 && contractDetails[2] instanceof ContractDetails ) {
						 ContractDetails contract = (ContractDetails) contractDetails[2];
						 header.put("contractOutstandingAmount", contract.getContractOutstandingAmount()==null ? 0 : contract.getContractOutstandingAmount());
					 }
					 
					 	header.put("supplierID", ph.getInvoiceheaderid());
					 	
					 	/*if(subCompany.isIdentity()){
					 		header.put("supplierName", subcompany.getScname());
					 	}else{
					 		header.put("supplierName", subcompany.getScname());
					 	}
					 	*/
					 	
					 	header.put("supplierName", subcompany.getScname());
						header.put("ContractNo", ph.getContractno());
						header.put("ContractID", ph.getContractid());
						header.put("InvoiceDate", ph.getInvoicedate());
						header.put("InvoiceNumber", ph.getInvoicenumber());
						header.put("BatchNumber", ph.getBatchnumber());
						header.put("InvoiceTime", ph.getInvoicetime());
						header.put("LocationCode", ph.getLocationcode());
						header.put("LocationName", ph.getLocationname());
						header.put("LineDiscount", ph.getLinediscount());
						header.put("TotalTax", ph.getTotaltax());
						header.put("NetAmount", ph.getNetamount());
						
						header.put("TotalAmount", ph.getTotal());
						header.put("PODate", subcompany.getScname());
						
						
						header.put("NumberofLines", ph.getNumberoflines());
						header.put("Status", ph.getStatus());
						
						
						header.put("boardApprovalNo", subcompany.getScname());
						header.put("boardpaperDate", subcompany.getScname());
						header.put("boardpaperOriginatedBy", subcompany.getScname());
						header.put("signedByCompany", subcompany.getScname());
						header.put("signedBySupplier", subcompany.getScname());
						
						
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
	public JSONObject viewCompanyApprovedInvoices(long invoiceId, HttpServletRequest request) {
		// TODO Auto-generated method stub

		try {
			System.out.println("invoiceId"+invoiceId);
			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			data.put("draw", request.getParameter("draw"));
			
			String sorting="";
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				sorting = "itemcode";
			    break;
			  case "1":
				sorting = "unitprice";
			    break;
			  case "2":
				sorting = "itemquantity";
			    break;
			  case "3":
				sorting = "itemamount";
			    break;
			  case "4":
				sorting = "servicecharge";
			    break;
			  case "5":
				  sorting = "linediscount";
				  break;
			  case "6":
				  sorting = "lineamount";
				  break;
			}
			Page<ContractInvoiceDetails> dataList;
			if (request.getParameter("order[0][dir]").equals("asc")) {
				dataList =contractInvoiceDetailsRepo.getApprovedInvoicesDetails(request.getParameter("search[value]"), invoiceId, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}else {
				dataList =contractInvoiceDetailsRepo.getApprovedInvoicesDetails(request.getParameter("search[value]"), invoiceId, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			JSONArray ar = new JSONArray();
			for (ContractInvoiceDetails tempContractInvoiceDetails : dataList.getContent()) {
				JSONObject ob = new JSONObject();
				ob.put("itemCode", tempContractInvoiceDetails.getItemcode());
				ob.put("unitPrice", tempContractInvoiceDetails.getUnitprice());
				ob.put("qty", tempContractInvoiceDetails.getItemquantity());
				ob.put("amount", tempContractInvoiceDetails.getItemamount());
				ob.put("serviceCharge", tempContractInvoiceDetails.getServicecharge());
				ob.put("discount", tempContractInvoiceDetails.getLinediscount());
				ob.put("value", tempContractInvoiceDetails.getLinediscount());
				ar.add(ob);
			}
			 data.put("recordsTotal",dataList.getTotalElements());
			 data.put("recordsFiltered",dataList.getTotalElements());
			 data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
		
		
	
	}
	
}
