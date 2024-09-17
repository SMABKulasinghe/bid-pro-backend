package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.AdditionalFileTenderForSupplierRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.AditionalFilesForTenderRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.AppzEligibleCodesRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.AppzTenderSubmissionsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EligibleCategoryRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EligibleSubCategoryRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.FinancialCodesRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.FinancialDetailsPerTenderRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.FinancialResponseOldRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.FinancialResponseRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.FinancialValuesPerTenderOldRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.FinancialValuesPerTenderRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.PoDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RfpDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RfpDetailsResponseRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RfpRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SubCompanyRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.TenderDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.TenderExtendRepo;
import lk.supplierUMS.SupplierUMS_REST.comman.Common;
import lk.supplierUMS.SupplierUMS_REST.comman.CommonEmail;
import lk.supplierUMS.SupplierUMS_REST.comman.DeEnCode;
import lk.supplierUMS.SupplierUMS_REST.entity.AdditionalFileTenderForSupplier;
import lk.supplierUMS.SupplierUMS_REST.entity.AditionalFilesForTender;
import lk.supplierUMS.SupplierUMS_REST.entity.AppzEligibleCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.AppzTenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.City;
import lk.supplierUMS.SupplierUMS_REST.entity.EPoHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.Province;
import lk.supplierUMS.SupplierUMS_REST.entity.RFP;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetailsResponse;
import lk.supplierUMS.SupplierUMS_REST.entity.RfpEvaluationCommitee;
import lk.supplierUMS.SupplierUMS_REST.entity.RfpEvaluationMarks;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.SupplierDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategoryProducts;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationCommitee;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationMarksAll;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationSheetCreate;
import lk.supplierUMS.SupplierUMS_REST.entity.FinacialResponsesVendor;
import lk.supplierUMS.SupplierUMS_REST.entity.FinacialResponsesVendorOld;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialDetailsPerTender;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialValuesPerTender;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialValuesPerTenderOld;
import lk.supplierUMS.SupplierUMS_REST.entity.PoDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.SupplierProductDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderExtend;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.TermsAndConditions;
import lk.supplierUMS.SupplierUMS_REST.entity.TermsAndConditionsTrans;
import lk.supplierUMS.SupplierUMS_REST.entity.CompanySupplierMapping;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.Department;
import lk.supplierUMS.SupplierUMS_REST.entity.District;
import lk.supplierUMS.SupplierUMS_REST.entity.EPoHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;

import lk.supplierUMS.SupplierUMS_REST.entity.SupplierProductDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderStatusDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.service.TenderDetailsService;


import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class TenderDetailsServiceImpl implements TenderDetailsService {

	@Value("${tender.doc.path}")
	String tenderDataPath;
	
	@Value("${tender.financialdoc.path}")
	String financialDocPath;

	@Value("${tendersubmit.doc.path}")
	String tenderSubmitDataPath;
	
	@Value("${tenderadditional.doc.path}")
	String tenderAdditinalPath;
	
	@Value("${financialview.doc.path}")
	String financialViewDocPath;
	
	@Value("${tenderadditionalsupplier.doc.path}")
	String tenderAdditinalSupplierPath;
	
	@Autowired
	RfpRepo rfpRepo;
	
	@Autowired
	FinancialValuesPerTenderRepo financialValuesPerTenderRepo;
	
	@Autowired
	FinancialResponseRepo financialResponseRepo;
	
	@Autowired
	FinancialDetailsPerTenderRepo financialDetailsPerTenderRepo;
	
	@Autowired
	RfpDetailsRepo rfpDetailRepo;

	@Autowired
	TenderDetailsRepo tenderDetailsRepo;

	@Autowired
	EligibleCategoryRepo eligibleCategoryRepo;
	
	@Autowired
	AppzEligibleCodesRepo appzEligibleCodesRepo;

	@Autowired
	AppzTenderSubmissionsRepo appzTenderSubmissionsRepo;
	
	@Autowired
	RfpDetailsResponseRepo rfpDetailsResponseRepo;
	
	@Autowired
	FinancialCodesRepo financialCodesRepo;
	
	@Autowired
	AditionalFilesForTenderRepo aditionalFilesForTenderRepo;
	
	@Autowired
	TenderExtendRepo tenderExtendRepo;
	
	@Autowired
	PoDetailsRepo poDetailsRepo;
	
	@Autowired
	FinancialResponseOldRepo financialResponseOldRepo;
	
	@Autowired
	FinancialValuesPerTenderOldRepo financialValuesPerTenderOldRepo;
	
	@Autowired
	Common common;
	
	@Autowired
	EligibleSubCategoryRepo eligibleSubCategoryRepo;
	
	@Autowired
	AdditionalFileTenderForSupplierRepo additionalFileTenderForSupplierRepo;

	@Autowired
	SubCompanyRepo subCompanyRepo;
	/*
	 * @Autowired CommonEmail commonEmail;
	 */
	
	@Override
	public String createTender(JSONObject tender) {
		// TODO Auto-generated method stub
		String returnMsg = null;
		TenderDetails tenderDetails = new TenderDetails();
		
		
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			System.out.println("Long----- " + user.getCompanyCode());
			
		/*
		  Boolean isExists =	tenderDetailsRepo.existsByBidno(tender.get("bidno").toString());
		  System.out.println("already exists-- "+isExists);
			
			if (isExists) {
				System.out.println("have");
				 return returnMsg = "already exists";
			} else {
				System.out.println("no");
	    */
	   
				//tenderDetails.setBidno(tender.get("bidno").toString());
				tenderDetails.setTendername(tender.get("tendername").toString());
				tenderDetails.setTenderdescription(tender.get("tenderdescription").toString());
				tenderDetails.setCordinator1name(tender.get("cordinatename").toString());
				tenderDetails.setCordinator1email(tender.get("cordinatemail").toString());
				tenderDetails.setCordinator1designation(tender.get("cordinatedestination").toString());
				tenderDetails.setCordinator1contactno(tender.get("cordinatephon").toString());
				//tenderDetails.setCordinator1contactno(Integer.parseInt(tender.get("cordinatephon").toString()));
				tenderDetails.setEligibleCategortID(Long.valueOf(tender.get("eligiblecategory").toString()));
				tenderDetails.setEligibleSubcatId(Long.valueOf(tender.get("subcategory").toString()));
				//tenderDetails.setEligiblesubcatProdid(Long.valueOf(tender.get("product").toString()));
				tenderDetails.setRfpId(Long.valueOf(tender.get("rfpid").toString()));
				tenderDetails.setQuantity(Integer.parseInt(tender.get("qty").toString()));
				
//				tenderDetails.setApprovalStatus(String.valueOf(tender.get("ApprovalStatus").toString()));
//				tenderDetails.setStatus(String.valueOf(tender.get("status").toString()));

				tenderDetails.setApprovalstatus("1");
				tenderDetails.setStatus("2");

				// tenderDetails.setApprovalStatus(status_approved);
				//tenderDetails.setCordinator2contactno(Integer.parseInt(tender.get("cordinate2phon").toString()));
				
				tenderDetails.setCordinator2contactno(tender.get("cordinate2phon").toString());
				tenderDetails.setCordinator2designation(tender.get("cordinate2destination").toString());
				tenderDetails.setCordinator2name(tender.get("cordinate2name").toString());
				tenderDetails.setCordinator2email(tender.get("cordinate2email").toString());
				
				 LocalDate createdDATE = Instant.ofEpochMilli(new Long(tender.get("closingdate").toString()))
				.atZone(ZoneId.systemDefault()).toLocalDate();
				 LocalTime createdTime = LocalTime.parse(tender.get("closingtime").toString());

				tenderDetails.setClosingdate(Instant.ofEpochMilli(new Long(tender.get("closingdate").toString()))
						.atZone(ZoneId.systemDefault()).toLocalDate());
				// tenderDetails.setClosingtime(LocalTime.parse(tender.get("closingtime").toString()));
				// tenderDetails.setClosingtime(LocalTime.parse(tender.get("closingtime").toString()));

				System.out.println("Closing time " + tender.get("closingtime").toString());
				//DateTimeFormatter parser = DateTimeFormatter.ofPattern("HH:mm a");
				//DateTimeFormatter parser = DateTimeFormatter.ofPattern("H:mm:s");
				//LocalTime localTime = LocalTime.parse("10:31 AM", parser);
				tenderDetails.setClosingtime(LocalTime.parse(tender.get("closingtime").toString()));
				tenderDetails.setClosingDateTime(LocalDateTime.of(createdDATE, createdTime));
				
				
				
				TenderExtend tenderExtend = new TenderExtend();
				
//				tenderExtend.setTrnderid(Long.valueOf(tender.get("trnderid").toString()));
//				tenderExtend.setBidno(tender.get("bidno").toString());
				tenderExtend.setTendername(tender.get("tendername").toString());

				tenderExtend.setClosingdate(Instant.ofEpochMilli(new Long(tender.get("closingdate").toString()))
							.atZone(ZoneId.systemDefault()).toLocalDate());

				System.out.println("Closing time " + tender.get("closingtime").toString());
				tenderExtend.setClosingtime(LocalTime.parse(tender.get("closingtime").toString()));
			      
				tenderDetails = tenderDetailsRepo.save(tenderDetails);
				
				tenderExtend = tenderExtendRepo.save(tenderExtend);
				
				tenderExtend.setTrnderid(tenderDetails.getTrnderid());
				tenderExtendRepo.save(tenderExtend);
				
				
	
				
				if (tender.containsKey("upload_support1")) {
					String headerData[]=tender.get("upload_support1").toString().split("base64,");
					String extention[]=tender.get("upload_support1").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(tenderDataPath, tenderDetails.getTrnderid() + "/supportDoc1" ,exten, head);
					
					tenderDetails.setSupportdoc1name(
							tenderDataPath + tenderDetails.getTrnderid() + "/supportDoc1." + exten);
				}
				
				if (tender.containsKey("upload_support2")) {
					String headerData[]=tender.get("upload_support2").toString().split("base64,");
					String extention[]=tender.get("upload_support2").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(tenderDataPath, tenderDetails.getTrnderid() + "/supportDoc2" ,exten, head);
					
					tenderDetails.setSupportdoc2name(
							tenderDataPath + tenderDetails.getTrnderid() + "/supportDoc2." + exten);
				}
				
				tenderDetails = tenderDetailsRepo.save(tenderDetails);
				
				
				
				
				//   T/Year/Cat/TenderId --> Tender number Format --> Issue after Tender registration
					
					Long trnderid = tenderDetails.getTrnderid();
					Long catIdForFind = tenderDetails.getEligibleCategortID();
					
					Optional<TenderDetails> tenderIdForUser = tenderDetailsRepo.findById(trnderid);
					Optional<EligibleCategory> catIdForUser = eligibleCategoryRepo.findById(catIdForFind);
					
					if (tenderIdForUser.isPresent()) {
						LocalDateTime createdDateTime = tenderIdForUser.get().getCreatedAt();
						int year = createdDateTime.getYear();
						
						tenderIdForUser.get().setBidno("T/"+year+"/"+catIdForUser.get().getEligibleCategortType()+"/"+tenderIdForUser.get().getTrnderid());
						tenderIdForUser.get().setCreatedBy(user.getUserid());
						tenderDetailsRepo.save(tenderIdForUser.get());	
						
					}	
				
				Optional<RFP> refpoptional = rfpRepo.findById(Long.valueOf(tender.get("rfpid").toString()));
			//	Boolean isExists =	rfpRepo.existsByBidno(tender.get("rfpid").toString());
			//	System.out.println("already exists-- "+isExists);
					
					if (refpoptional.isPresent()) {
						System.out.println("have");
						refpoptional.get().setLogedUserId(user.getUserid());
						refpoptional.get().setTenderId(String.valueOf(tenderDetails.getTrnderid()));
						rfpRepo.save(refpoptional.get());
					} else {
						System.out.println("no");
					}	
					Optional<TenderDetails> tenderIdForUserReturn = tenderDetailsRepo.findById(trnderid);
					String tenderNo= tenderIdForUserReturn.get().getBidno();
				return returnMsg = tenderNo;

			

	

			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return returnMsg= null;
		}

		
	}

	@Override
	public JSONObject getTenders(HttpServletRequest req) {
		try {

			JSONObject data = new JSONObject();

			data.put("draw", req.getParameter("draw"));
			String sorting = "";

			switch (req.getParameter("order[0][column]")) {
			case "0":
				sorting = "trnderid";
				break;
			case "1":
				sorting = "scname";
				break;
			case "2":
				sorting = "scregistrationno";
				break;
			case "3":
				sorting = "scemailadmin";
				break;
			case "4":
				sorting = "scphoneno1";
				break;
			}

			Page<TenderDetails> tenders = tenderDetailsRepo.findAllTender(req.getParameter("search[value]"),
					PageRequest.of(
							Integer.parseInt(req.getParameter("start")) / Integer.parseInt(req.getParameter("length")),
							Integer.parseInt(req.getParameter("length")),
							Sort.by(req.getParameter("order[0][dir]").equals("asc") ? Sort.Direction.ASC
									: Sort.Direction.DESC, sorting)));

			JSONArray ar = new JSONArray();
			for (TenderDetails tenderDetails : tenders.getContent()) {
				JSONObject ob = new JSONObject();

				ob.put("tenderno", tenderDetails.getTrnderid());
				ob.put("tenderdesc", tenderDetails.getTenderdescription());
				ob.put("open", tenderDetails.getOpeningdate());
				ob.put("close", tenderDetails.getClosingdate());
				ob.put("close", tenderDetails.getClosingtime());
				ob.put("view", tenderDetails.getTrnderid());

				ob.put("tendername", tenderDetails.getTendername());
				ob.put("status", tenderDetails.getStatus());

				ob.put("trnderid", tenderDetails.getTrnderid());
				ob.put("bidno", tenderDetails.getBidno());

				ar.add(ob);
			}
			data.put("recordsTotal", tenders.getTotalElements());
			data.put("recordsFiltered", tenders.getTotalElements());
			data.put("data", ar);
			return data;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject getEligibleVenders(long id) {
		try {

			List<AppzEligibleCodes> codes = appzEligibleCodesRepo.findByTenderNo(Long.valueOf(id));

			for (AppzEligibleCodes appzEligibleCodes : codes) {

			}

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}


	@Override
	public Boolean getWetherTenderExistsByBidno(String enteredValue) {
		// TODO Auto-generated method stub
		return tenderDetailsRepo.existsByBidno(enteredValue);
	}


	@Override
	public JSONObject getTenderDetailsForSupplier(HttpServletRequest request) {
		try {

			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			// Long userCompanyID = new Long(139);
			LocalDate NowDate = LocalDate.now();
			LocalTime NowTime = LocalTime.now();
			String NowDateTime = NowDate+"T"+NowTime;
			LocalDateTime NowDateAndTime = LocalDateTime.parse(NowDateTime);
			LocalDateTime NowDateAndTimee = LocalDateTime.now();
			
			Page<Object[]> get_tender_details = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "bidno";
				break;
			case "1":
				sorting = "tendername";
				break;
			case "2":
				sorting = "tenderdescription";
				break;
			case "3":
				sorting = "closingDateTime";
				break;

			}

			// cod.createdAt LIKE %?1% OR cod.expiryDate LIKE %?1% OR cod.modifiedBy LIKE
			// %?1% OR cod.paymentType LIKE %?1% OR cod.RenewalDatePeriod
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				get_tender_details = tenderDetailsRepo.getTenderDetailsForSupplier(request.getParameter("search[value]"),
						userCompanyID,NowDate,NowDateAndTimee,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));

				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				get_tender_details = tenderDetailsRepo.getTenderDetailsForSupplier(request.getParameter("search[value]"),
						userCompanyID,NowDate,NowDateAndTimee,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
				// contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id,
				// Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"),
				// PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")),
				// Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			long count = get_tender_details.getTotalElements();
			System.out.println("get cn--->" + get_tender_details.getContent().size());
			for (Object[] objects : get_tender_details.getContent()) {
				if (objects[0] instanceof TenderDetails && objects[1] instanceof SupplierProductDetails
						&& objects[2] instanceof TenderSubmissions) {
					TenderDetails td = (TenderDetails) objects[0];
					SupplierProductDetails sp = (SupplierProductDetails) objects[1];
					TenderSubmissions ts = (TenderSubmissions) objects[2];
					JSONObject ob = new JSONObject();
					
					LocalDateTime datetime = td.getClosingDateTime();
					String datetimeval = String.valueOf(datetime);
					String[] parts = datetimeval.split("T");
					
					String date = parts[0];
					String time = parts[1];
					
					String dateTime = date+" "+time;
					
					ob.put("bidNo", td.getBidno());
					ob.put("tenderNumber", td.getTrnderid());
					ob.put("closingDateTime", dateTime);
					ob.put("tenderName", td.getTendername());
					ob.put("tenderDescription", td.getTenderdescription());
					//ob.put("closingTime", td.getClosingtime());
					
					//ob.put("designation", td.getCordinator1designation());
					ob.put("rfpId", td.getRfpId());
					ar.add(ob);

				}

			}
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			System.out.println("data ds--->" + data);
			return data;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	@Override
	public String tendersubmit(JSONObject data, HttpServletRequest request, String filename) {
		// TenderSubmissions appzTenderSubmission = new TenderSubmissions();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String status = "2";
		String tenderResponse = "8";
		String tenderResponseParticipate = "5";
		String tenderClose = "15";
		String msg = null;
		Long RfpId = Long.valueOf(data.get("rfpId").toString());
		try {
			
			Optional<TenderDetails> tds = tenderDetailsRepo.findById(Long.valueOf(data.get("tId").toString()));
			
			LocalDate ClosingDate = tds.get().getClosingdate();
			LocalTime ClosingTime = tds.get().getClosingtime();
			
			LocalDateTime ClosingDateAndTimeTable = tds.get().getClosingDateTime();
			
			String ClosingDateTime = ClosingDate+"T"+ClosingTime;
			LocalDateTime ClosingDateAndTime = LocalDateTime.parse(ClosingDateTime);
			System.out.println("closing date time --------------->"+ClosingDateAndTime);
			
			LocalDate NowDate = LocalDate.now();
			LocalTime NowTime = LocalTime.now();
			String NowDateTime = NowDate+"T"+NowTime;
			LocalDateTime NowDateAndTime = LocalDateTime.parse(NowDateTime);
			System.out.println("now date time --------------->"+NowDateAndTime);
			
			LocalDateTime NowDateAndTimee = LocalDateTime.now();
			System.out.println("LocalDateTime ------------------->>>>"+NowDateAndTimee);
			System.out.println("ClosingDateAndTimeTable ------------------->>>>"+ClosingDateAndTimeTable);
			
			Optional<TenderSubmissions> optTs = appzTenderSubmissionsRepo.findByTenderNoAndSupplierId(
					Long.valueOf(data.get("tId").toString()), Long.valueOf(user.getCompanyCode()));
			
			if(NowDateAndTimee.isBefore(ClosingDateAndTimeTable)) {
				
			//Reader reader1 = null;
			
			if (optTs.isPresent()) {
				System.out.println("optTs.isPresent()");
				TenderSubmissions newOb =  optTs.get();
				if (filename.equals("companyProfile")) {
					
					Optional<TenderSubmissions> companyProfileTs = appzTenderSubmissionsRepo.findByTenderNoAndSupplierId(
							Long.valueOf(data.get("tId").toString()), Long.valueOf(user.getCompanyCode()));
					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(tenderSubmitDataPath,  data.get("tId").toString()+"-"+user.getCompanyCode().toString()+ "/companyProfile",exten, head);
					
					//newOb.setUploadCompanyProfileName(
							//tenderSubmitDataPath + data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/companyProfile." + exten);
					
					//newOb.setUploadCompanyProfileName("setUploadCompanyProfileName");
					System.out.println("setUploadCompanyProfileName");
				//	newOb = appzTenderSubmissionsRepo.saveAndFlush(newOb);
					
					msg = "Success";
				}
				
				if (filename.equals("financialfile")) {
					
					Optional<TenderSubmissions> financialfileTs = appzTenderSubmissionsRepo.findByTenderNoAndSupplierId(
							Long.valueOf(data.get("tId").toString()), Long.valueOf(user.getCompanyCode()));
					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(tenderSubmitDataPath,  data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/financialFile",exten, head);
					
					//newOb.setUploadFinancialsFileName(
							//tenderSubmitDataPath + data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/financialFile." + exten);
					//newOb.setUploadFinancialsFileName("setUploadFinancialsFileName");
					System.out.println("setUploadFinancialsFileName");
				//	appzTenderSubmissionsRepo.saveAndFlush(newOb);
					
					msg = "Success";
				}

				
				if (filename.equals("supportdoc1")) {
					
					Optional<TenderSubmissions> supportdoc1Ts = appzTenderSubmissionsRepo.findByTenderNoAndSupplierId(
							Long.valueOf(data.get("tId").toString()), Long.valueOf(user.getCompanyCode()));
					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(tenderSubmitDataPath,  data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/supportDoc1",exten, head);
					
					//newOb.setUploadSupportDocOne(
							//tenderSubmitDataPath + data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/supportDoc1." + exten);
					//newOb.setUploadSupportDocOne("setUploadSupportDocOne");
					System.out.println("setUploadSupportDocOne");
				//	appzTenderSubmissionsRepo.saveAndFlush(newOb);
					
					msg = "Success";
				}
				
				if (filename.equals("supportdoc2")) {
					
					Optional<TenderSubmissions> supportdoc2Ts = appzTenderSubmissionsRepo.findByTenderNoAndSupplierId(
							Long.valueOf(data.get("tId").toString()), Long.valueOf(user.getCompanyCode()));
					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(tenderSubmitDataPath,  data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/supportDoc2",exten, head);
					
					//newOb.setUploadSupportDocTwo(
							//tenderSubmitDataPath + data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/supportDoc2." + exten);
					//newOb.setUploadSupportDocTwo("setUploadSupportDocTwo");
					System.out.println("setUploadSupportDocTwo");
				//	appzTenderSubmissionsRepo.saveAndFlush(newOb);
					
					msg = "Success";
				}
				
				if (filename.equals("supportdoc3")) {
					
					Optional<TenderSubmissions> supportdoc3Ts = appzTenderSubmissionsRepo.findByTenderNoAndSupplierId(
							Long.valueOf(data.get("tId").toString()), Long.valueOf(user.getCompanyCode()));
					
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					String exten = extention[1];
					System.out.println("split------------------->"+exten);
					
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(tenderSubmitDataPath,  data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/supportDoc3",exten, head);
					
					//newOb.setUploadSupportDocThree(
							//tenderSubmitDataPath + data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/supportDoc3." + exten);
					//newOb.setUploadSupportDocThree("setUploadSupportDocThree");
					System.out.println("setUploadSupportDocThree");
				//	appzTenderSubmissionsRepo.saveAndFlush(newOb);
					
					msg = "Success";
				}
				
				newOb.setUploadCompanyProfileName(
				 tenderSubmitDataPath + data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/companyProfile." + "pdf");
				newOb.setUploadFinancialsFileName(
				 tenderSubmitDataPath + data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/financialFile." + "pdf");
				newOb.setUploadSupportDocOne(
				 tenderSubmitDataPath + data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/supportDoc1." + "pdf");
				newOb.setUploadSupportDocTwo(
				 tenderSubmitDataPath + data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/supportDoc2." + "pdf");
				newOb.setUploadSupportDocThree(
				 tenderSubmitDataPath + data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/supportDoc3." + "pdf");
				
				optTs.get().setUserId(user.getUsername());
				optTs.get().setSubmittedDate(LocalDate.now());
				optTs.get().setSubmittedTime(LocalTime.now());
				optTs.get().setUserId(user.getUserid());
				optTs.get().setTenderResponse(tenderResponse);
				optTs.get().setTenderEligibility("8");
				optTs.get().setVendorId(user.getCompanyCode());
				optTs.get().setStatus(status);
				appzTenderSubmissionsRepo.saveAndFlush(newOb);
				appzTenderSubmissionsRepo.saveAndFlush(optTs.get());
				msg = "Success";
			}
				
			}else {
				if (optTs.isPresent()) {
					optTs.get().setTenderResponse(tenderClose);
					appzTenderSubmissionsRepo.save(optTs.get());
				}
				System.out.println("False");
				msg = "TenderExpired";
			}
		
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = "Error";
		}

		return msg;
	}

	@Override
	public JSONObject getTenderForView(HttpServletRequest req) {
		// TODO Auto-generated method stub

		JSONObject data = new JSONObject();
		try {
			// JSONObject data= new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			LocalDateTime NowDateAndTimee = LocalDateTime.now();
			LocalDate NowDate = LocalDate.now();

			Page<Object[]> tenders = null;
			data.put("draw", req.getParameter("draw"));
			String sorting = "";

			switch (req.getParameter("order[0][column]")) {
			case "0":
				sorting = "bidno";
				break;
			case "1":
				sorting = "tendername";
				break;
			case "2":
				sorting = "tenderdesc";
				break;
			case "3":
				sorting = "closingDateandTime";
				break;
			case "4":
				sorting = "displayRemainingTimetoUpload";
				break;
			case "5":
				sorting = "status";
				break;
			}

			if (req.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC");
				tenders = tenderDetailsRepo.findAllViewTender(req.getParameter("search[value]"), userCompanyID,NowDateAndTimee,NowDate,
						PageRequest.of(
								Integer.parseInt(req.getParameter("start"))
										/ Integer.parseInt(req.getParameter("length")),
								Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				tenders = tenderDetailsRepo.findAllViewTender(req.getParameter("search[value]"), userCompanyID,NowDateAndTimee,NowDate,
						PageRequest.of(
								Integer.parseInt(req.getParameter("start"))
										/ Integer.parseInt(req.getParameter("length")),
								Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			List<String> gfg = new ArrayList<String>();

			// Initialize an ArrayList with add()
			gfg.add("6");
			gfg.add("5");
			gfg.add("14");
			gfg.add("7");

			// long count = tenders.getTotalElements();
			System.out.println("get cn--->" + tenders.getContent().size());
			int i =0;
			for (Object[] objects : tenders.getContent()) {

				if (objects[0] instanceof TenderDetails && objects[1] instanceof SupplierProductDetails
						&& objects[2] instanceof TenderStatusDetails) {
					TenderDetails tenderDetails = (TenderDetails) objects[0];
					SupplierProductDetails sp = (SupplierProductDetails) objects[1];
					TenderStatusDetails ts = (TenderStatusDetails) objects[2];
					JSONObject ob = new JSONObject();

					LocalDateTime dateTime = LocalDateTime.of(tenderDetails.getClosingdate(),
							tenderDetails.getClosingtime() == null ? LocalTime.of(17, 30, 0) :  tenderDetails.getClosingtime());
					Instant instant = dateTime.atZone(ZoneId.of("Europe/Paris")).toInstant();
					System.out.println(instant);

					Instant start = Instant.now();
					// your code
					Instant end = instant;
					Duration timeElapsed = Duration.between(start, end);
					System.out.println("Time taken: " + timeElapsed.toMillis() + " milliseconds");
					System.out.println("Time taken: " + timeElapsed.toDays() + " days");

					ob.put("index", ++i);
					ob.put("trnderid", tenderDetails.getTrnderid());
					ob.put("bidno", tenderDetails.getBidno());
					ob.put("tendername", tenderDetails.getTendername());
					ob.put("tenderdesc", tenderDetails.getTenderdescription());
					ob.put("closingDateandTime", tenderDetails.getClosingdate() + " " + tenderDetails.getClosingtime());
					ob.put("displayRemainingTimetoUpload", timeElapsed.toDays()<0 ? Math.abs(timeElapsed.toDays()) +" days ago" : timeElapsed.toDays());
					ob.put("status", ts.getTender_status_name());
					
					ob.put("coordinator1Name", tenderDetails.getCordinator1name());
					ob.put("designation1", tenderDetails.getCordinator1designation());
					ob.put("email1", tenderDetails.getCordinator1email());
					ob.put("contactNumber1", tenderDetails.getCordinator1contactno());
					
					ob.put("coordinator2Name", tenderDetails.getCordinator2name());
					ob.put("designation2", tenderDetails.getCordinator2designation());
					ob.put("email2", tenderDetails.getCordinator2email());
					ob.put("contactNumber2", tenderDetails.getCordinator2contactno());
										
					ob.put("supportDoc1", tenderDetails.getSupportdoc1name());
					ob.put("supportDoc2",tenderDetails.getSupportdoc2name());
					ob.put("rfpId",tenderDetails.getRfpId());

					ob.put("isResponded", appzTenderSubmissionsRepo.existsByTenderNoAndSupplierIdAndTenderResponseIn(
							tenderDetails.getTrnderid(), userCompanyID, gfg));

					JSONArray view = new JSONArray();
					view.add(tenderDetails.getTrnderid());
					view.add(tenderDetails.getTrnderid());
					ar.add(ob);
				}
			}
				 data.put("recordsTotal",tenders.getTotalElements());
				 data.put("recordsFiltered",tenders.getTotalElements());
				 data.put("data", ar);
				return data;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
}
	

      @Override
      public JSONObject getTenderAuthorization(HttpServletRequest req) {
	// TODO Auto-generated method stub
	try {

		JSONObject data = new JSONObject();
		
		Page<Object[]> tenderdetails  = null;
		data.put("draw", req.getParameter("draw"));
		String sorting = "";
		
		switch (req.getParameter("order[0][column]")) {
		//case "0":
		//	sorting = "trnderid";
		//	break;
		case "0":
			sorting = "bidno";
			break;
		case "1":
			sorting = "tendername";
			break;
		case "2":
			sorting = "tenderdescription";
			break;
		case "3":
			sorting = "closingdate";
			break;
		case "4":
			sorting = "esub.eligibleSubcatname";
			break;
		case "5":
			sorting = "epro.eligibleSubcatProdname";
			break;
		case "6":
			sorting = "rfp.fileName";
			break;
		}

		if (req.getParameter("order[0][dir]").equals("asc")) {
			System.out.println("ASC ");
			tenderdetails = tenderDetailsRepo.findAllTendersForTenderAuth(req.getParameter("search[value]"),
					PageRequest.of(
							Integer.parseInt(req.getParameter("start")) / Integer.parseInt(req.getParameter("length")),
							Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));

			System.out.println("end ");
		} else {
			System.out.println("DESC ");
			tenderdetails = tenderDetailsRepo.findAllTendersForTenderAuth(req.getParameter("search[value]"),
					PageRequest.of(
							Integer.parseInt(req.getParameter("start")) / Integer.parseInt(req.getParameter("length")),
							Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
//				contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
		}

		JSONArray ar = new JSONArray();
		for (Object[] tenderDetails : tenderdetails.getContent()) {
			if (tenderDetails[0] instanceof TenderDetails 
					&& tenderDetails[1] instanceof RFP
					&& tenderDetails[2] instanceof EligibleCategory 				 
					&& tenderDetails[3] instanceof EligibleSubCategory
					&& tenderDetails[4] instanceof EligibleSubCategoryProducts){

				TenderDetails td = (TenderDetails) tenderDetails[0];
				RFP rfp = (RFP) tenderDetails[1];
				EligibleCategory eligibleCategory = (EligibleCategory) tenderDetails[2];
				EligibleSubCategory eligiblesubcategory = (EligibleSubCategory) tenderDetails[3];
				EligibleSubCategoryProducts eligiblesubcategoryproducts = (EligibleSubCategoryProducts) tenderDetails[4];
				JSONObject ob = new JSONObject();

				ob.put("tenderNumber", td.getTrnderid());
				ob.put("bidno", td.getBidno());
				ob.put("tendername", td.getTendername());
				ob.put("tenderdescription", td.getTenderdescription());
				ob.put("rfp", rfp.getFileName());
				// ob.put("eligibleCategortID", td.getEligibleCategortID());
				// ob.put("eligibleSubcatId", td.getEligibleSubcatId());
				// ob.put("eligiblesubcatProdid", td.getEligiblesubcatProdid());
				ob.put("CategortName", eligibleCategory.getEligibleCategortName());
				ob.put("eligibleSubcatName", eligiblesubcategory.getEligibleSubcatname());
				ob.put("eligiblesubcatProdname", eligiblesubcategoryproducts.getEligibleSubcatProdname());
				ob.put("approvalStatus", td.getApprovalstatus());
				ob.put("tenderID", td.getTrnderid());
				
				ob.put("created_date", td.getCreatedAt());
				ob.put("closingdate", td.getClosingdate());
				ob.put("closingtime", td.getClosingtime());

				ob.put("cordinator1name", td.getCordinator1name());
				ob.put("cordinator1designation", td.getCordinator1designation());
				ob.put("cordinator1email", td.getCordinator1email());
				ob.put("cordinator1contactno", td.getCordinator1contactno());

				ob.put("cordinator2name", td.getCordinator2name());
				ob.put("cordinator2designation", td.getCordinator2designation());
				ob.put("cordinator2email", td.getCordinator2email());
				ob.put("cordinator2contactno", td.getCordinator2contactno());
				
				ob.put("supportdoc1", td.getSupportdoc1name());
				ob.put("supportdoc2", td.getSupportdoc2name());
				
				//ob.put("isApproved",td.getApprovalstatus());
				//ob.put("isApproved",td.getStatus());
				
				JSONArray view = new JSONArray();
				view.add(td.getTrnderid());
				view.add(td.getTrnderid());
				view.add(td.getTrnderid());
				view.add(td.getTrnderid());
				view.add(td.getTrnderid());
				ob.put("view", view);
				ar.add(ob);
			}
		}

		data.put("recordsTotal", tenderdetails.getTotalElements());
		data.put("recordsFiltered", tenderdetails.getTotalElements());

		data.put("data", ar);
		return data;

	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

/*	@Override
	public JSONObject TenderAuthorization(long id, String status) {
		JSONObject res = new JSONObject();

		try {
			String statusApproved = "3";
			String statusRejected = "1";

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();

			Optional<TenderDetails> tender = tenderDetailsRepo.findById(id);
			if (tender.isPresent()) {
				TenderDetails tenderDetails = tender.get();				

				if (status.equals("approve")) {

					// tender.get().setTrnderid(user.getTrnderid());
					tenderDetails.setApprovalstatus(statusApproved);
					tenderDetails.setStatus(statusApproved);
					tenderDetails = tenderDetailsRepo.save(tenderDetails);
					res.put("msg", "success");
					res.put("code", "01");
					res.put("name", tenderDetails.getTendername());

				} else {
					// tender.get().setTrnderid(user.getTrnderid());
					tenderDetails.setApprovalstatus(statusRejected);
					tenderDetails.setStatus(statusRejected);
					tenderDetails = tenderDetailsRepo.save(tenderDetails);
					res.put("msg", "success");
					res.put("code", "02");
					res.put("name", tenderDetails.getTendername());
				}
				//tenderDetails.setApproveReason(res.get("reason").toString());
				//tenderDetails = tenderDetailsRepo.save(tenderDetails);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			res.put("msg", "error");
			res.put("code", "04");
			return res;
		}
		return res;

	}
	*/

	@Override
	public JSONObject updateTender(JSONObject data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject gettenderlist(long id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getAllTender(HttpServletRequest request, String parameter) {
		// TODO Auto-generated method stub

		System.out.println("Inside ServiceImpl " + parameter);
		JSONObject data = new JSONObject();

		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			System.out.println("Long----- " + userCompanyID);

			Page<Object[]> dataList = null;

			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "trnderid";
				break;
			case "1":
				sorting = "bidno";
				break;
			case "2":
				sorting = "tendername";
				break;
			case "3":
				sorting = "tenderdescription";
				break;
			case "4":
				sorting = "closingdate";
				break;
			case "5":
				sorting = "esub.eligibleSubcatname";
				break;
			case "6":
				sorting = "tsd.tender_status_name";
				break;
				

			}

			if (request.getParameter("order[0][dir]").equals("asc")) {
				dataList = tenderDetailsRepo.getAllTendersForCatergory(request.getParameter("search[value]"),
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				dataList = tenderDetailsRepo.getAllTendersForCatergory(request.getParameter("search[value]"),
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
//				System.out.println("Size -------------"+dataList.size());
			for (Object[] tenderDetails : dataList.getContent()) {
				if (tenderDetails[0] instanceof TenderDetails 
						&& tenderDetails[1] instanceof RFP
						&& tenderDetails[2] instanceof EligibleCategory
						&& tenderDetails[3] instanceof EligibleSubCategory
						&& tenderDetails[4] instanceof TenderStatusDetails) {

					TenderDetails ph = (TenderDetails) tenderDetails[0];
					RFP rfp = (RFP) tenderDetails[1];
					EligibleCategory eligibleCategory = (EligibleCategory) tenderDetails[2];
					EligibleSubCategory eligiblesubcategory = (EligibleSubCategory) tenderDetails[3];
					TenderStatusDetails tenderstatusdetails = (TenderStatusDetails) tenderDetails[4];

					JSONObject header = new JSONObject();

					header.put("tenderNumber", ph.getTrnderid());
					header.put("Tender_status_name", tenderstatusdetails.getTender_status_name());
					header.put("bidno", ph.getBidno());
					header.put("tendername", ph.getTendername());
					header.put("tenderdescription", ph.getTenderdescription());
					//header.put("closingdate", ph.getClosingdate());

					// header.put("eligibleCategortID", ph.getEligibleCategortID());
					// header.put("eligibleSubcatId", ph.getEligibleSubcatId());
					// header.put("eligiblesubcatProdid", ph.getEligiblesubcatProdid());
					header.put("status", ph.getStatus());
					header.put("tenderID", ph.getTrnderid());
					
					header.put("rfp", rfp.getFileName());
					header.put("reason", ph.getApproveReason());
					header.put("created_date", ph.getCreatedAt());
					header.put("closingdate", ph.getClosingdate());
					header.put("closingtime", ph.getClosingtime());
					header.put("authorizedby", ph.getApprovedBy());
					header.put("authorizedate", ph.getApproveDateTime());


					header.put("cordinator1name", ph.getCordinator1name());
					header.put("cordinator1designation", ph.getCordinator1designation());
					header.put("cordinator1email", ph.getCordinator1email());
					header.put("cordinator1contactno", ph.getCordinator1contactno());

					header.put("cordinator2name", ph.getCordinator2name());
					header.put("cordinator2designation", ph.getCordinator2designation());
					header.put("cordinator2email", ph.getCordinator2email());
					header.put("cordinator2contactno", ph.getCordinator2contactno());

					header.put("CategortName", eligibleCategory.getEligibleCategortName());
					header.put("eligibleSubcatName", eligiblesubcategory.getEligibleSubcatname());
					// header.put("eligiblesubcatProdName",
					// eligiblesubcategoryproducts.getEligibleSubcatProdname());

					// header.put("reason", ph.getContractApprovalReason());
					// header.put("status", ph.getContractApprovalStatus());

					// header.put("renewalDate", ph.getRenewalDate());
					header.put("supportdoc1", ph.getSupportdoc1name());
					header.put("supportdoc2", ph.getSupportdoc2name());
					
					JSONArray view = new JSONArray();
					view.add(ph.getTrnderid());
					view.add(ph.getTrnderid());
					view.add(ph.getTrnderid());
					view.add(ph.getTrnderid());
					view.add(ph.getTrnderid());
					header.put("view", view);

					ar.add(header);
				}
			}

//			 Optional<Long> count  = poHeaderRepo.getPoDetailsSupplierCount(request.getParameter("search[value]"), code);
			data.put("recordsTotal", dataList.getTotalElements());
			data.put("recordsFiltered", dataList.getTotalElements());
			data.put("data", ar);
			System.out.println("ar " + ar);
			System.out.println(data);
			return data;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public JSONObject getTenderOpen(HttpServletRequest req) {
		try {

			JSONObject data = new JSONObject();

			data.put("draw", req.getParameter("draw"));
			String sorting = "";
			Page<Object[]> tenderdetails = null;
			switch (req.getParameter("order[0][column]")) {
			case "0":
				sorting = "trnderid";
				break;
			case "1":
				sorting = "bidno";
				break;
			case "2":
				sorting = "tendername";
				break;
			case "3":
				sorting = "tenderdescription";
				break;
			case "4":
				sorting = "closingdate";
				break;
			case "5":
				sorting = "esub.eligibleSubcatname";
				break;
			case "6":
				sorting = "epro.eligibleSubcatProdname";
				break;
			}

			if (req.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				tenderdetails = tenderDetailsRepo.getTenderDetailsForTenderOpen(req.getParameter("search[value]"),
						PageRequest.of(
								Integer.parseInt(req.getParameter("start"))
										/ Integer.parseInt(req.getParameter("length")),
								Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));

				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				tenderdetails = tenderDetailsRepo.getTenderDetailsForTenderOpen(req.getParameter("search[value]"),
						PageRequest.of(
								Integer.parseInt(req.getParameter("start"))
										/ Integer.parseInt(req.getParameter("length")),
								Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
//				contract_detail = tenderDetailsRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			for (Object[] tenderDetails : tenderdetails.getContent()) {
				if (tenderDetails[0] instanceof TenderDetails && tenderDetails[1] instanceof EligibleCategory
						&& tenderDetails[2] instanceof EligibleSubCategory && tenderDetails[3] instanceof EligibleSubCategoryProducts) {

					TenderDetails td = (TenderDetails) tenderDetails[0];
					EligibleCategory eligibleCategory = (EligibleCategory) tenderDetails[1];
					EligibleSubCategory eligiblesubcategory = (EligibleSubCategory) tenderDetails[2];
					EligibleSubCategoryProducts eligiblesubcategoryproducts = (EligibleSubCategoryProducts) tenderDetails[3];
					JSONObject ob = new JSONObject();

					ob.put("tenderNumber", td.getTrnderid());
					ob.put("bidno", td.getBidno());
					ob.put("tendername", td.getTendername());
					ob.put("tenderdescription", td.getTenderdescription());
					//ob.put("eligibleCategortID", td.getEligibleCategortID());
					//ob.put("eligibleSubcatId", td.getEligibleSubcatId());
					ob.put("CategortName", eligibleCategory.getEligibleCategortName());
					ob.put("eligibleSubcatName", eligiblesubcategory.getEligibleSubcatname());
					ob.put("eligiblesubcatProdname", eligiblesubcategoryproducts.getEligibleSubcatProdname());
					ob.put("approvalStatus", td.getApprovalstatus());
					ob.put("tenderID", td.getTrnderid());
					
					ob.put("created_date", td.getCreatedAt());
					ob.put("closingdate", td.getClosingdate());
					ob.put("closingtime", td.getClosingtime());
					
					ob.put("supportdoc1", td.getSupportdoc1name());
					ob.put("supportdoc2", td.getSupportdoc2name());

					ob.put("cordinator1name", td.getCordinator1name());
					ob.put("cordinator1designation", td.getCordinator1designation());
					ob.put("cordinator1email", td.getCordinator1email());
					ob.put("cordinator1contactno", td.getCordinator1contactno());

					ob.put("cordinator2name", td.getCordinator2name());
					ob.put("cordinator2designation", td.getCordinator2designation());
					ob.put("cordinator2email", td.getCordinator2email());
					ob.put("cordinator2contactno", td.getCordinator2contactno());

					JSONArray view = new JSONArray();
					view.add(td.getTrnderid());
					view.add(td.getTrnderid());
					view.add(td.getTrnderid());
					view.add(td.getTrnderid());
					view.add(td.getTrnderid());
					ob.put("view", view);
					ar.add(ob);

				}
			}

			data.put("recordsTotal", tenderdetails.getTotalElements());
			data.put("recordsFiltered", tenderdetails.getTotalElements());

			data.put("data", ar);
			return data;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
		
		

	@Override
	public JSONObject TenderStatusOpen(long id, HttpServletRequest request) {
		JSONObject res = new JSONObject();

		try {
			String statusOpen = "4";

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();

			Optional<TenderDetails> tender = tenderDetailsRepo.findById(id);
			if (tender.isPresent()) {
				TenderDetails tenderDetails = tender.get();
				// if (status.equals("open")) {

				// tender.get().setTrnderid(user.getTrnderid());
				tenderDetails.setApprovalstatus(statusOpen);
				tenderDetails.setStatus(statusOpen);
				tenderDetails = tenderDetailsRepo.save(tenderDetails);
				res.put("msg", "success");
				res.put("code", "01");
				res.put("name", tenderDetails.getTendername());

			} /*
				 * else { // tender.get().setTrnderid(user.getTrnderid());
				 * tenderDetails.setApprovalstatus(statusOpen);
				 * tenderDetails.setStatus(statusOpen); tenderDetails =
				 * tenderDetailsRepo.save(tenderDetails); res.put("msg", "success");
				 * res.put("code", "02"); res.put("name", tenderDetails.getTendername()); }
				 */
			// }

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			res.put("msg", "error");
			res.put("code", "04");
			return res;
		}
		return res;

	}

	@Override
	public JSONObject getEligibleSuppliers(HttpServletRequest request, Long tenderID) {
		// TODO Auto-generated method stub

		System.out.println("Inside ServiceImpl");

		JSONObject data = new JSONObject();

		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long companyID = new Long(user.getCompanyCode());
			System.out.println("Long----- " + companyID);
			
			
			Optional<TenderSubmissions> eligibleSup = appzTenderSubmissionsRepo.findByTenderNoAndSupplierId(tenderID, Long.valueOf(user.getCompanyCode()));
			Boolean isPresent = false;
			if (eligibleSup.isPresent()) {
			System.out.println("Test "+eligibleSup.get().getTenderNo());
				isPresent = true;
			}

			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "spm.supplierID";
				break;
			
			  case "1": 
				  sorting = "subc.scname"; 
				  break; 
			  case "2": 
				  sorting = "subc.scemailadmin";
				  break;
			 
		
			}

			Page<Object[]> supplier = null;

			if (request.getParameter("order[0][dir]").equals("asc")) {
				supplier = tenderDetailsRepo.getEligibleSuppliersForACategoryANDTenderID(
						request.getParameter("search[value]"), tenderID,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				supplier = tenderDetailsRepo.getEligibleSuppliersForACategoryANDTenderID(
						request.getParameter("search[value]"), tenderID,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
//				System.out.println("Size -------------"+supplier.size());
			for (Object[] tenderDetails : supplier.getContent()) {
				if (tenderDetails[0] instanceof TenderDetails && tenderDetails[1] instanceof SupplierProductDetails
						&& tenderDetails[2] instanceof SubCompany) {

					TenderDetails ph = (TenderDetails) tenderDetails[0];
					SupplierProductDetails supplierproductdetails = (SupplierProductDetails) tenderDetails[1];
					SubCompany subcompany = (SubCompany) tenderDetails[2];

					JSONObject header = new JSONObject();

					// header.put("EcategoryID", ph.getEligibleCategortID());
					// header.put("categoryID", supplierproductdetails.getCategoryID());
					header.put("tenderID", ph.getTrnderid());
					header.put("supplierID", supplierproductdetails.getSupplierID());
					header.put("subcompanyName", subcompany.getScname());
					header.put("scemailadmin", subcompany.getScemailadmin());
					header.put("isPresent", isPresent);
					// header.put("scname", subcompany.getScname());

					ar.add(header);
				}
			}

//			 Optional<Long> count  = poHeaderRepo.getPoDetailsSupplierCount(request.getParameter("search[value]"), code);
			data.put("recordsTotal", supplier.getTotalElements());
			data.put("recordsFiltered", supplier.getTotalElements());
			data.put("data", ar);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public JSONObject getTenderIds() {
		//LocalDate NowDate = LocalDate.now();
		LocalDateTime DateTime = LocalDateTime.now();
		JSONObject data = new JSONObject();
		try {
			List<Object[]> tenderDetails = appzTenderSubmissionsRepo.getTenderIds(DateTime);
			JSONArray ar = new JSONArray();
			for (Object[] objects : tenderDetails) {
				if (objects[1] instanceof TenderDetails && objects[0] instanceof TenderSubmissions) {
					TenderSubmissions ts = (TenderSubmissions) objects[0];
					TenderDetails td = (TenderDetails) objects[1];
					
					JSONObject tenderIDs = new JSONObject();
					
					tenderIDs.put("tenderId", ts.getTenderNo());	
					tenderIDs.put("tenderName", td.getTendername());
					tenderIDs.put("tBidNumber", td.getBidno());
					
					ar.add(tenderIDs);
				}
			}
			data.put("data", ar);
			return data;
			//return tenderDetails;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

	@Override
	public JSONObject getTenderDetailsForView(Long tenderID) {
		JSONObject tenderDetailsAndEligibleCat = new JSONObject();
		try {
			
			Optional<TenderDetails> tds = tenderDetailsRepo.findById(tenderID);
			
			LocalDate ClosingDate = tds.get().getClosingdate();
			LocalTime ClosingTime = tds.get().getClosingtime();
			LocalDateTime ClosingDateAndTimeTable = tds.get().getClosingDateTime();
			System.out.println("closing date time from table ------------------->>>>"+ClosingDateAndTimeTable);
			
			String ClosingDateTime = ClosingDate+"T"+ClosingTime;
			LocalDateTime ClosingDateAndTime = LocalDateTime.parse(ClosingDateTime);
			System.out.println("closing date time --------------->"+ClosingDateAndTime);
			
			LocalDate NowDate = LocalDate.now();
			LocalTime NowTime = LocalTime.now();
			String NowDateTime = NowDate+"T"+NowTime;
			LocalDateTime NowDateAndTime = LocalDateTime.parse(NowDateTime);
			System.out.println("now date time --------------->"+NowDateAndTime);
			
			LocalDateTime NowDateAndTimee = LocalDateTime.now();
			System.out.println("LocalDateTime ------------------->>>>"+LocalDateTime.now());
			
			if(ClosingDateAndTimeTable.isBefore(NowDateAndTimee)) {
				System.out.println("Tender ID"+tenderID); 
				  List<Object[]> tenderDetails =  tenderDetailsRepo.getTenderDetailsForView(tenderID); 
				  //Optional<TenderDetails> tenderDetails =  tenderDetailsRepo.findById(tenderID); 
					for (Object[] objects : tenderDetails) {
						if (objects[0] instanceof TenderDetails && objects[1] instanceof EligibleCategory) {
							TenderDetails td = (TenderDetails) objects[0];
							EligibleCategory ec = (EligibleCategory) objects[1];
							
							tenderDetailsAndEligibleCat.put("TenderName", td.getTendername());
							tenderDetailsAndEligibleCat.put("BidNo", td.getBidno());
							tenderDetailsAndEligibleCat.put("CreatedBy", td.getCreatedBy());
							tenderDetailsAndEligibleCat.put("Categories", ec.getEligibleCategortName());
							tenderDetailsAndEligibleCat.put("ClosingDate", td.getClosingdate());
							tenderDetailsAndEligibleCat.put("ClosingTime", td.getClosingtime());
							tenderDetailsAndEligibleCat.put("Description", td.getComments());
							tenderDetailsAndEligibleCat.put("RfpID", td.getRfpId());
							
							tenderDetailsAndEligibleCat.put("CordinatorName1", td.getCordinator1name());
							tenderDetailsAndEligibleCat.put("CordinatorDesignation1", td.getCordinator1designation());
							tenderDetailsAndEligibleCat.put("CordinatorEmail1", td.getCordinator1email());
							tenderDetailsAndEligibleCat.put("CordinatorTP1", td.getCordinator1contactno());
							
							tenderDetailsAndEligibleCat.put("CordinatorName2", td.getCordinator2name());
							tenderDetailsAndEligibleCat.put("CordinatorDesignation2", td.getCordinator2designation());
							tenderDetailsAndEligibleCat.put("CordinatorEmail2", td.getCordinator2email());
							tenderDetailsAndEligibleCat.put("CordinatorTP2", td.getCordinator2contactno());
							
						}
					}
			}else {
				tenderDetailsAndEligibleCat.put("reponseText", "Already_Exists");
			}
				
				
			  
				  
				 
				/*
				 * if(tenderDetails.isPresent()) { return tenderDetails.get(); }else { return
				 * null; }
				 */
			  
			}
			 catch (Exception e) {
				 e.printStackTrace(); 
				 return null;
			 }
		return tenderDetailsAndEligibleCat;
	}

	@Override
	public JSONObject financialResponse(HttpServletRequest request, Long tenderId) {
		
		JSONObject data = new JSONObject();

		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long companyID = new Long(user.getCompanyCode());
			System.out.println("Long----- " + companyID);
			
			List<Object[]> FinancialDetail = financialDetailsPerTenderRepo.getFinancialCodeDetails(tenderId, companyID);
			
			JSONArray ar = new JSONArray();
			for (Object[] tenderDetails : FinancialDetail) {
				if (tenderDetails[0] instanceof FinancialDetailsPerTender){
					System.out.println("FinancialDetailsPerTender----- " + companyID);
					FinancialDetailsPerTender financialdetailspertender = (FinancialDetailsPerTender)tenderDetails[0]; 
					TenderSubmissions tendersubmissions = (TenderSubmissions) tenderDetails[1];
					FinancialCodes financialcodes = (FinancialCodes) tenderDetails[2];
					
					JSONObject obj = new JSONObject();
					obj.put("financialCodeId", financialdetailspertender.getFinancialCodeId());
					obj.put("isCappex", financialdetailspertender.getCappex());
					obj.put("decription", financialcodes.getDescription());
					obj.put("financialCode", financialcodes.getCode());
					
					ar.add(obj);
					
				    
				        
				}
			}

	
			List<Object[]> tender = tenderDetailsRepo.getTenderDetailsForRelatedTenderId(tenderId, companyID);
		
	
			JSONObject ob = new JSONObject();

			for (Object[] tenderDetails : tender) {
				if (tenderDetails[0] instanceof TenderDetails && tenderDetails[1] instanceof EligibleCategory
						&& tenderDetails[2] instanceof EligibleSubCategory && tenderDetails[3] instanceof TenderSubmissions 
						&& tenderDetails[4] instanceof RFP  ) 
				{

					TenderDetails td = (TenderDetails) tenderDetails[0];
					EligibleCategory eligibleCategory = (EligibleCategory) tenderDetails[1];
					EligibleSubCategory eligiblesubcategory = (EligibleSubCategory) tenderDetails[2];
					RFP rfp = (RFP) tenderDetails[4];
					TenderSubmissions ts = (TenderSubmissions) tenderDetails[3];
					
					

					ob.put("tenderNumber", td.getTrnderid());
					ob.put("bidno", td.getBidno());
					ob.put("tendername", td.getTendername());
					ob.put("tenderdesc", td.getTenderdescription());
					ob.put("CategortName", eligibleCategory.getEligibleCategortName());
					ob.put("eligibleSubcatName", eligiblesubcategory.getEligibleSubcatname());
					ob.put("rfpNo", rfp.getRfpNo());
					ob.put("vendorId", ts.getVendorId());
					ob.put("fileName", rfp.getFileName());
					
				}
			}


			data.put("data", ob);
			data.put("fieldList", ar);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
		
	}

	@Override
	public List<TenderDetails> getTenderForFinancialResponse() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long companyID = new Long(user.getCompanyCode());
		System.out.println("Long----- " + companyID);
		try {

			List<TenderDetails> data = tenderDetailsRepo.getTenderForFinancialDetails(companyID);

			return data;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public JSONObject getTenderDetailsForViewTable(HttpServletRequest request, Long id) {
		
		
		JSONObject data = new JSONObject();

		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long companyID = new Long(user.getCompanyCode());
			System.out.println("Long----- " + companyID);

			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			switch (request.getParameter("order[0][column]")) {
			case "1":
				sorting = "sc.scname";
				break;
			
			case "2": 
				sorting = "sc.scphoneno1"; 
				break;
			 
			
			case "3": 
				sorting = "sc.scemailadmin"; 
				break;
			 
			}

			Page<Object[]> tenderSubmissions = null;
			System.out.println("Suppiler Id----------->"+id);
			if (request.getParameter("order[0][dir]").equals("asc")) {
				tenderSubmissions = appzTenderSubmissionsRepo.getTenderSubmissionDataForTender(
						request.getParameter("search[value]"), id,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				tenderSubmissions = appzTenderSubmissionsRepo.getTenderSubmissionDataForTender(
						request.getParameter("search[value]"), id,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
//				System.out.println("Size -------------"+supplier.size());
			int n = 1;
			for (Object[] tenderSubmission : tenderSubmissions.getContent()) {
				if (tenderSubmission[0] instanceof TenderSubmissions && tenderSubmission[1] instanceof User && tenderSubmission[2] instanceof SubCompany) 
				{

					TenderSubmissions ts = (TenderSubmissions) tenderSubmission[0];
					User users = (User) tenderSubmission[1];
					SubCompany subCompany = (SubCompany) tenderSubmission[2];
					
					JSONObject tenderSubmissionDetails = new JSONObject();

					// header.put("EcategoryID", ph.getEligibleCategortID());
					// header.put("categoryID", supplierproductdetails.getCategoryID());
					tenderSubmissionDetails.put("CompanyCode", users.getCompanyCode());
					//tenderSubmissionDetails.put("UserName", users.getUsername());
					tenderSubmissionDetails.put("UserName", subCompany.getScname());
					tenderSubmissionDetails.put("SubmittedDate", ts.getSubmittedDate());
					tenderSubmissionDetails.put("SubmittedTime", ts.getSubmittedTime());
					tenderSubmissionDetails.put("TenderId", ts.getTenderNo());
					tenderSubmissionDetails.put("SupplierId", ts.getSupplierId());
					tenderSubmissionDetails.put("FinancialFile", ts.getUploadFinancialsFileName());
					tenderSubmissionDetails.put("CompanyProfile", ts.getUploadCompanyProfileName());
					tenderSubmissionDetails.put("RfpFile", ts.getUploadRfpFileName());
					tenderSubmissionDetails.put("SupportDoc1", ts.getUploadSupportDocOne());
					tenderSubmissionDetails.put("SupportDoc2", ts.getUploadSupportDocTwo());
					tenderSubmissionDetails.put("SupportDoc3", ts.getUploadSupportDocThree());
					tenderSubmissionDetails.put("LogedUser", ts.getUserId());
					
					
					tenderSubmissionDetails.put("TpNumber",subCompany.getScphoneno1());
					tenderSubmissionDetails.put("Email",subCompany.getScemailadmin());
					tenderSubmissionDetails.put("Index",n);
					ar.add(tenderSubmissionDetails);
					n++;
				}
			}

//			 Optional<Long> count  = poHeaderRepo.getPoDetailsSupplierCount(request.getParameter("search[value]"), code);
			data.put("recordsTotal", tenderSubmissions.getTotalElements());
			data.put("recordsFiltered", tenderSubmissions.getTotalElements());
			data.put("data", ar);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
		
	}


/*	@Override
	public String tenderFinancialResponse(JSONObject tendersub) {
		// TODO Auto-generated method stub
		String msg = null;
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			System.out.println("Long----- " + user.getCompanyCode()+" "+user.getUserid());

			FinacialResponsesVendor td = new FinacialResponsesVendor();
			

			td.setTenderID(Long.valueOf(tendersub.get("tenderno").toString()));
			td.setSubCompanyCode(Long.valueOf(user.getCompanyCode().toString()));
			td.setUserID(user.getUserid());
			td.setAmountlkr(new BigDecimal(tendersub.get("amount_lkr").toString()));
			td.setAmountusd(new BigDecimal(tendersub.get("amount_usd").toString()));
			td.setDescription(tendersub.get("comment").toString());
			td.setSubmitDateTime(LocalDateTime.now()); 
			
		  
			financialResponseRepo.save(td);
			
			msg = "Success";
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	return msg;
	}

*/
	
	@Override
	public String financialValuesPerTender(JSONObject tendersub) {
		// TODO Auto-generated method stub
		String msg = null;
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			System.out.println("Long----- " + user.getCompanyCode()+" "+user.getUserid());

			Long tenderID = Long.valueOf(tendersub.get("tenderID").toString());
			String comment = tendersub.get("comment").toString();
			System.out.println("comment----- " + comment+" tenderID---> "+tenderID);
			
		Optional<FinacialResponsesVendor> isSubmited = financialResponseRepo.findBySubCompanyCodeAndTenderID(Long.valueOf(user.getCompanyCode().toString()), Long.valueOf(tendersub.get("tenderID").toString()));
			
		if(isSubmited.isPresent()) {
			//msg = "Already submited";
			//System.out.println("Already submited");
			//boolean tender_invite_to_submit = false;
			//count
			//status - invite to resubmit
			System.out.println("inside-isPresent");
			Optional<TenderDetails> tenderDetails = tenderDetailsRepo.findById(isSubmited.get().getTenderID());
			LocalDateTime ClosingDateAndTimeTable = tenderDetails.get().getClosingDateTime();
			LocalDateTime NowDateAndTimee = LocalDateTime.now();
			
			if(isSubmited.get().getStatus()==24 || isSubmited.get().getStatus()==25) {
				System.out.println("inside-inside");
				if(NowDateAndTimee.isBefore(ClosingDateAndTimeTable)) {
					System.out.println("inside-closing");
					ArrayList<JSONObject> itemList = (ArrayList) tendersub.get("items");
					
					Double grandTotal = 0.0;
					
					List<FinancialValuesPerTender> financialPerTenderss = financialValuesPerTenderRepo.findByTenderIdAndSupplierId(Long.valueOf(tendersub.get("tenderID").toString()),Long.valueOf(user.getCompanyCode().toString()));
					for(FinancialValuesPerTender financialPerTender : financialPerTenderss) {
						financialValuesPerTenderRepo.deleteById(financialPerTender.getFinancialId());
					}
					
					
					for (Iterator iterator = itemList.iterator(); iterator.hasNext();) {
						HashMap<String, Object> items = (HashMap) iterator.next();
						// System.out.println(items);
						LinkedHashMap pro = (LinkedHashMap) items;
						System.out.println("ID--> "+pro.get("ID").toString()+" cost---> "+pro.get("cost").toString());
						
						FinancialValuesPerTender td = new FinancialValuesPerTender();
						  
						  td.setUserId(user.getUserid()); td.setCreatedDateTime(LocalDateTime.now());
						  td.setSupplierId(Long.valueOf(user.getCompanyCode().toString()));
						  td.setTenderId(Long.valueOf(tenderID));
						  td.setCappex(pro.get("cappex").toString());
						  td.setAmount(Double.valueOf(pro.get("cost").toString()));
						  td.setCostDescription(pro.get("cost_decription").toString());
						  td.setComment(tendersub.get("comment").toString());
						  td.setCurrency(tendersub.get("currency").toString());
						  td.setFinancialPerTenderParamID(Long.valueOf(pro.get("ID").toString()));
						  grandTotal += Double.valueOf(pro.get("cost").toString());
						  
						  financialValuesPerTenderRepo.save(td);
						
					}
					
						FinacialResponsesVendor finacialresponse = new FinacialResponsesVendor();
						String subComID = user.getCompanyCode().toString();
						  
						isSubmited.get().setTenderID(Long.valueOf(tendersub.get("tenderID").toString()));
						isSubmited.get().setSubCompanyCode(Long.valueOf(user.getCompanyCode().toString()));
						isSubmited.get().setUserID(user.getUserid());
						 if(tendersub.get("currency").toString().equals("LKR")) {
							 isSubmited.get().setAmountlkr(grandTotal);
						 }else {
							 isSubmited.get().setAmountusd(grandTotal);
						 }
						 isSubmited.get().setDescription(tendersub.get("comment").toString());
						 isSubmited.get().setSubmitDateTime(LocalDateTime.now());
						 isSubmited.get().setStatus(Long.valueOf(25));
						 Long count = isSubmited.get().getCount()+1;
						 isSubmited.get().setCount(count);
						  financialResponseRepo.save(isSubmited.get());
						  
						  if (tendersub.containsKey("upload_doc")) {
								String headerData[]=tendersub.get("upload_doc").toString().split("base64,");
								String extention[]=tendersub.get("upload_doc").toString().split("[/;]");
								String exten = extention[1];
								System.out.println("split------------------->"+exten);
								
								String head=headerData[1].substring(0, (headerData[1].length()));
								new DeEnCode().decodeMethod(financialDocPath, isSubmited.get().getTenderID()+"_"+subComID + "/uploadDoc" ,exten, head);
								
								isSubmited.get().setFinancialDocument(
										financialDocPath + isSubmited.get().getTenderID()+"_"+subComID + "/uploadDoc." + exten);
							}
						 
						  financialResponseRepo.save(isSubmited.get());
							
						
						  msg = "Success";
						  System.out.println("Success");
				}else if(ClosingDateAndTimeTable.isBefore(NowDateAndTimee)) {
					msg = "TenderExpired";
				}else {
					msg = "Error";
				}
				
				
			}else if(isSubmited.get().getStatus()==26){
				ArrayList<JSONObject> itemList = (ArrayList) tendersub.get("items");
				
				Double grandTotal = 0.0;
				
				List<FinancialValuesPerTender> financialPerTenderss = financialValuesPerTenderRepo.findByTenderIdAndSupplierId(Long.valueOf(tendersub.get("tenderID").toString()),Long.valueOf(user.getCompanyCode().toString()));
				for(FinancialValuesPerTender financialPerTender : financialPerTenderss) {
					financialValuesPerTenderRepo.deleteById(financialPerTender.getFinancialId());
				}
				
				
				for (Iterator iterator = itemList.iterator(); iterator.hasNext();) {
					HashMap<String, Object> items = (HashMap) iterator.next();
					// System.out.println(items);
					LinkedHashMap pro = (LinkedHashMap) items;
					System.out.println("ID--> "+pro.get("ID").toString()+" cost---> "+pro.get("cost").toString());
					
					FinancialValuesPerTender td = new FinancialValuesPerTender();
					  
					  td.setUserId(user.getUserid()); td.setCreatedDateTime(LocalDateTime.now());
					  td.setSupplierId(Long.valueOf(user.getCompanyCode().toString()));
					  td.setTenderId(Long.valueOf(tenderID));
					  td.setCappex(pro.get("cappex").toString());
					  td.setAmount(Double.valueOf(pro.get("cost").toString()));
					  td.setCostDescription(pro.get("cost_decription").toString());
					  td.setComment(tendersub.get("comment").toString());
					  td.setCurrency(tendersub.get("currency").toString());
					  td.setFinancialPerTenderParamID(Long.valueOf(pro.get("ID").toString()));
					  grandTotal += Double.valueOf(pro.get("cost").toString());
					  
					  financialValuesPerTenderRepo.save(td);
					
				}
				
					FinacialResponsesVendor finacialresponse = new FinacialResponsesVendor();
					String subComID = user.getCompanyCode().toString();
					  
					isSubmited.get().setTenderID(Long.valueOf(tendersub.get("tenderID").toString()));
					isSubmited.get().setSubCompanyCode(Long.valueOf(user.getCompanyCode().toString()));
					isSubmited.get().setUserID(user.getUserid());
					 if(tendersub.get("currency").toString().equals("LKR")) {
						 isSubmited.get().setAmountlkr(grandTotal);
					 }else {
						 isSubmited.get().setAmountusd(grandTotal);
					 }
					 isSubmited.get().setDescription(tendersub.get("comment").toString());
					 isSubmited.get().setSubmitDateTime(LocalDateTime.now());
					 isSubmited.get().setStatus(Long.valueOf(27));
					 Long count = isSubmited.get().getCount()+1;
					 isSubmited.get().setCount(count);
					  financialResponseRepo.save(isSubmited.get());
					  
					  if (tendersub.containsKey("upload_doc")) {
							String headerData[]=tendersub.get("upload_doc").toString().split("base64,");
							String extention[]=tendersub.get("upload_doc").toString().split("[/;]");
							String exten = extention[1];
							System.out.println("split------------------->"+exten);
							
							String head=headerData[1].substring(0, (headerData[1].length()));
							new DeEnCode().decodeMethod(financialDocPath, isSubmited.get().getTenderID()+"_"+subComID + "/uploadDoc" ,exten, head);
							
							isSubmited.get().setFinancialDocument(
									financialDocPath + isSubmited.get().getTenderID()+"_"+subComID + "/uploadDoc." + exten);
						}
					 
					  financialResponseRepo.save(isSubmited.get());
						
					
					  msg = "Success";
					  System.out.println("Success");
			}else if(isSubmited.get().getStatus()==27){
				 msg = "Already submited";
			}
			
		}else {
			ArrayList<JSONObject> itemList = (ArrayList) tendersub.get("items");
			
			Double grandTotal = 0.0;
			
			for (Iterator iterator = itemList.iterator(); iterator.hasNext();) {
				HashMap<String, Object> items = (HashMap) iterator.next();
				// System.out.println(items);
				LinkedHashMap pro = (LinkedHashMap) items;
				System.out.println("ID--> "+pro.get("ID").toString()+" cost---> "+pro.get("cost").toString());
				
				FinancialValuesPerTender td = new FinancialValuesPerTender();
				  
				  td.setUserId(user.getUserid()); td.setCreatedDateTime(LocalDateTime.now());
				  td.setSupplierId(Long.valueOf(user.getCompanyCode().toString()));
				  td.setTenderId(Long.valueOf(tenderID));
				  td.setCappex(pro.get("cappex").toString());
				  td.setAmount(Double.valueOf(pro.get("cost").toString()));
				  td.setCostDescription(pro.get("cost_decription").toString());
				  td.setComment(tendersub.get("comment").toString());
				  td.setCurrency(tendersub.get("currency").toString());
				  td.setFinancialPerTenderParamID(Long.valueOf(pro.get("ID").toString()));
				  grandTotal += Double.valueOf(pro.get("cost").toString());
				  
				  financialValuesPerTenderRepo.save(td);
				
			}
			
				FinacialResponsesVendor finacialresponse = new FinacialResponsesVendor();
				String subComID = user.getCompanyCode().toString();
				  
				  finacialresponse.setTenderID(Long.valueOf(tendersub.get("tenderID").toString()));
				  finacialresponse.setSubCompanyCode(Long.valueOf(user.getCompanyCode().toString()));
				  finacialresponse.setUserID(user.getUserid());
				 if(tendersub.get("currency").toString().equals("LKR")) {
					 finacialresponse.setAmountlkr(grandTotal);
				 }else {
					 finacialresponse.setAmountusd(grandTotal);
				 }
				  finacialresponse.setDescription(tendersub.get("comment").toString());
				  finacialresponse.setSubmitDateTime(LocalDateTime.now());
				  finacialresponse.setStatus(Long.valueOf(24));
				  finacialresponse.setCount(Long.valueOf(1));
				  financialResponseRepo.save(finacialresponse);
				  
				  if (tendersub.containsKey("upload_doc")) {
						String headerData[]=tendersub.get("upload_doc").toString().split("base64,");
						String extention[]=tendersub.get("upload_doc").toString().split("[/;]");
						String exten = extention[1];
						System.out.println("split------------------->"+exten);
						
						String head=headerData[1].substring(0, (headerData[1].length()));
						new DeEnCode().decodeMethod(financialDocPath, finacialresponse.getTenderID()+"_"+subComID + "/uploadDoc" ,exten, head);
						
						finacialresponse.setFinancialDocument(
								financialDocPath + finacialresponse.getTenderID()+"_"+subComID + "/uploadDoc." + exten);
					}
				 
				  finacialresponse = financialResponseRepo.save(finacialresponse);
					
				
				  msg = "Success";
				  System.out.println("Success");
			  
		}

			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "error";
		}
		System.out.println("msg=--->"+ msg);
	return msg;
	}


	@Override
	public List<TenderDetails> getFinancialCodeDetailsForTender() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long companyID = new Long(user.getCompanyCode());
		System.out.println("Long----- " + companyID);
		try {

			List<TenderDetails> data = tenderDetailsRepo.getTenderIDForFinancialCodeCreation();

			return data;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public JSONObject getTenderDetailsForSupplierView(HttpServletRequest request) {
		try {

			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			// Long userCompanyID = new Long(139);
			LocalDate NowDate = LocalDate.now();
			LocalTime NowTime = LocalTime.now();
			String NowDateTime = NowDate+"T"+NowTime;
			LocalDateTime NowDateAndTime = LocalDateTime.parse(NowDateTime);
			LocalDateTime NowDateAndTimee = LocalDateTime.now();
			
			Page<Object[]> get_tender_details = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "trnderid";
				break;
			case "1":
				sorting = "closingdate";
				break;
			case "2":
				sorting = "cordinator1designation";
				break;

			}

			// cod.createdAt LIKE %?1% OR cod.expiryDate LIKE %?1% OR cod.modifiedBy LIKE
			// %?1% OR cod.paymentType LIKE %?1% OR cod.RenewalDatePeriod
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				get_tender_details = tenderDetailsRepo.getTenderDetailsForSupplierView(request.getParameter("search[value]"),
						userCompanyID,NowDate,NowDateAndTimee,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));

				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				get_tender_details = tenderDetailsRepo.getTenderDetailsForSupplierView(request.getParameter("search[value]"),
						userCompanyID,NowDate,NowDateAndTimee,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
				// contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id,
				// Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"),
				// PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")),
				// Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			long count = get_tender_details.getTotalElements();
			System.out.println("get cn--->" + get_tender_details.getContent().size());
			for (Object[] objects : get_tender_details.getContent()) {
				if (objects[0] instanceof TenderDetails && objects[1] instanceof SupplierProductDetails
						&& objects[2] instanceof TenderSubmissions) {
					TenderDetails td = (TenderDetails) objects[0];
					SupplierProductDetails sp = (SupplierProductDetails) objects[1];
					TenderSubmissions ts = (TenderSubmissions) objects[2];
					JSONObject ob = new JSONObject();

					ob.put("bidNo", td.getBidno());
					ob.put("tenderNumber", td.getTrnderid());
					ob.put("closingDate", td.getClosingdate());
					ob.put("closingTime", td.getClosingtime());
					ob.put("designation", td.getCordinator1designation());
					ob.put("rfpId", td.getRfpId());
					ob.put("tenderRespon", ts.getTenderResponse());
					ob.put("tenderEligibility", ts.getTenderEligibility());
					ar.add(ob);

				}

			}
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			System.out.println("data ds--->" + data);
			return data;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	
/*	@Override
	public JSONObject getFinancialCodeDetails(HttpServletRequest request, Long tenderId) {
		
		JSONObject data = new JSONObject();

		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long companyID = new Long(user.getCompanyCode());
			System.out.println("Long----- " + companyID);
	
			List<Object[]> tender = tenderDetailsRepo.getTenderDetailsForFinancialCode(tenderId, companyID);
	
			JSONObject ob = new JSONObject();

			for (Object[] tenderDetails : tender) {
				if (tenderDetails[0] instanceof TenderDetails && tenderDetails[1] instanceof EligibleCategory
						&& tenderDetails[2] instanceof EligibleSubCategory && tenderDetails[3] instanceof TenderSubmissions ) 
				{

					TenderDetails td = (TenderDetails) tenderDetails[0];
					EligibleCategory eligibleCategory = (EligibleCategory) tenderDetails[1];
					EligibleSubCategory eligiblesubcategory = (EligibleSubCategory) tenderDetails[2];
					TenderSubmissions ts = (TenderSubmissions) tenderDetails[3];

					ob.put("tenderNumber", td.getTrnderid());
					ob.put("bidno", td.getBidno());
					ob.put("tendername", td.getTendername());
					ob.put("tenderdesc", td.getTenderdescription());
					ob.put("CategortName", eligibleCategory.getEligibleCategortName());
					ob.put("eligibleSubcatName", eligiblesubcategory.getEligibleSubcatname());
					
				}
			}

			data.put("data", ob);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
		
	}
*/

	@Override
	public JSONObject getTenderDetailsForDetailsViewAll(HttpServletRequest request) {
		try {

			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			// Long userCompanyID = new Long(139);
			LocalDate NowDate = LocalDate.now();
			LocalTime NowTime = LocalTime.now();
			String NowDateTime = NowDate+"T"+NowTime;
			LocalDateTime NowDateAndTime = LocalDateTime.parse(NowDateTime);
			LocalDateTime NowDateAndTimee = LocalDateTime.now();
			
			Page<Object[]> get_tender_details = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "trnderid";
				break;
			case "1":
				sorting = "closingdate";
				break;
			case "2":
				sorting = "cordinator1designation";
				break;

			}

			// cod.createdAt LIKE %?1% OR cod.expiryDate LIKE %?1% OR cod.modifiedBy LIKE
			// %?1% OR cod.paymentType LIKE %?1% OR cod.RenewalDatePeriod
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				get_tender_details = tenderDetailsRepo.getTenderDetailsForDetailsViewAll(request.getParameter("search[value]"),
						userCompanyID,NowDate,NowDateAndTimee,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));

				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				get_tender_details = tenderDetailsRepo.getTenderDetailsForDetailsViewAll(request.getParameter("search[value]"),
						userCompanyID,NowDate,NowDateAndTimee,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
				// contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id,
				// Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"),
				// PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")),
				// Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			long count = get_tender_details.getTotalElements();
			System.out.println("get cn--->" + get_tender_details.getContent().size());
			for (Object[] objects : get_tender_details.getContent()) {
				if (objects[0] instanceof TenderDetails && objects[1] instanceof SupplierProductDetails
						&& objects[2] instanceof TenderSubmissions) {
					TenderDetails td = (TenderDetails) objects[0];
					SupplierProductDetails sp = (SupplierProductDetails) objects[1];
					TenderSubmissions ts = (TenderSubmissions) objects[2];
					JSONObject ob = new JSONObject();

					ob.put("bidNo", td.getBidno());
					ob.put("tenderNumber", td.getTrnderid());
					ob.put("closingDate", td.getClosingdate());
					ob.put("closingTime", td.getClosingtime());
					ob.put("designation", td.getCordinator1designation());
					ob.put("rfpId", td.getRfpId());
					ob.put("tenderRespon", ts.getTenderResponse());
					ob.put("tenderEligibility", ts.getTenderEligibility());
					ar.add(ob);

				}

			}
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			System.out.println("data ds--->" + data);
			return data;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	@Override
	public JSONObject getFinancialDetailsForCappex(HttpServletRequest request, Long tenderId, Long supplierId) {
		//System.out.println("Hi hi" +tenderId+supplierId);
		JSONObject data = new JSONObject();

		try {

			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "currency";
				break;
			
			
			  case "1": sorting = "amount"; break;
			  
			  
			  case "2": sorting = " comment"; break;
			  
			  case "3": sorting = "fc.description"; break;
			 
			 
			}

			Page<Object[]> cappexes = null;
			
			if (request.getParameter("order[0][dir]").equals("asc")) {
			System.out.println("blaa blaaaa");
				cappexes = tenderDetailsRepo.getFinancialDetailsForCappex(
						request.getParameter("search[value]"), tenderId,supplierId,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				cappexes = tenderDetailsRepo.getFinancialDetailsForCappex(
						request.getParameter("search[value]"), tenderId,supplierId,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();

			for (Object[] cappex : cappexes.getContent()) {
				if (cappex[0] instanceof FinancialValuesPerTender && cappex[1] instanceof FinancialDetailsPerTender && cappex[2] instanceof FinancialCodes) 
				{

					FinancialValuesPerTender finValPerTender = (FinancialValuesPerTender) cappex[0];
					FinancialDetailsPerTender finDetailPerTender = (FinancialDetailsPerTender) cappex[1];
					FinancialCodes finCode = (FinancialCodes) cappex[2];
					
					JSONObject financialDetails = new JSONObject();

					
					financialDetails.put("CurrencyType", finValPerTender.getCurrency());
					financialDetails.put("Amount", finValPerTender.getAmount());
					financialDetails.put("Comments", finValPerTender.getComment());
					financialDetails.put("FinacialCodeDes", finCode.getDescription());
					
					
					ar.add(financialDetails);
					
				}
			}

			data.put("recordsTotal", cappexes.getTotalElements());
			data.put("recordsFiltered", cappexes.getTotalElements());
			data.put("data", ar);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
		//return null;
	}

	@Override
	public JSONObject getFinancialDetailsForOppex(HttpServletRequest request, Long tenderId, Long supplierId) {
		JSONObject data = new JSONObject();

		try {

			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "currency";
				break;
			
			
			  case "1": sorting = "amount"; break;
			  
			  
			  case "2": sorting = " comment"; break;
			  
			  case "3": sorting = "fc.description"; break;
			 
			 
			}

			Page<Object[]> cappexes = null;
			
			if (request.getParameter("order[0][dir]").equals("asc")) {
			System.out.println("blaa blaaaa");
				cappexes = tenderDetailsRepo.getFinancialDetailsForOppex(
						request.getParameter("search[value]"), tenderId,supplierId,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				cappexes = tenderDetailsRepo.getFinancialDetailsForOppex(
						request.getParameter("search[value]"), tenderId,supplierId,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();

			for (Object[] cappex : cappexes.getContent()) {
				if (cappex[0] instanceof FinancialValuesPerTender && cappex[1] instanceof FinancialDetailsPerTender && cappex[2] instanceof FinancialCodes) 
				{

					FinancialValuesPerTender finValPerTender = (FinancialValuesPerTender) cappex[0];
					FinancialDetailsPerTender finDetailPerTender = (FinancialDetailsPerTender) cappex[1];
					FinancialCodes finCode = (FinancialCodes) cappex[2];
					
					JSONObject financialDetails = new JSONObject();

					
					financialDetails.put("CurrencyType", finValPerTender.getCurrency());
					financialDetails.put("Amount", finValPerTender.getAmount());
					financialDetails.put("Comments", finValPerTender.getComment());
					financialDetails.put("FinacialCodeDes", finCode.getDescription());
					
					
					ar.add(financialDetails);
					
				}
			}

			data.put("recordsTotal", cappexes.getTotalElements());
			data.put("recordsFiltered", cappexes.getTotalElements());
			data.put("data", ar);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public JSONObject getFinancialDetailsForCappexOppex(HttpServletRequest request, Long tenderId, Long supplierId) {
		JSONObject data = new JSONObject();
		try {
			
			Optional<FinacialResponsesVendor> dataOfAmounts = tenderDetailsRepo.getFinancialDetailsForCappexOppex(tenderId,supplierId);
			data.put("Amountlkr", dataOfAmounts.get().getAmountlkr());
			data.put("AmountUsd", dataOfAmounts.get().getAmountusd());
			//data.put("recordsFiltered", cappexes.getTotalElements());
			//data.put("data", ar);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	
	public JSONObject getFinancialCodeDetails(HttpServletRequest request, Long tenderId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String financial_code(JSONObject data, HttpServletRequest request) {
		String msg = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long userCompanyID = new Long(user.getCompanyCode());
		
		try {
			HashMap<String, Object> table = (HashMap<String, Object>) data.get("table");
			System.out.println(table);
		
			ArrayList<Object> evaArray = (ArrayList<Object>) data.get("evaArray");
		

		for (Iterator iterator = evaArray.iterator(); iterator.hasNext();) {
			HashMap<String, Object> items = (HashMap) iterator.next();
			LinkedHashMap costingChemicalDetail = (LinkedHashMap) items;
			
			FinancialCodes codeCreate = new FinancialCodes();
		
		codeCreate.setCode(data.get("ev_financial_code").toString());
		codeCreate.setDescription(data.get("description").toString());
		
		//evCreate.setTenderId(Long.valueOf(data.get("trnderid").toString()));
		//evCreate.setCriteriaName(costingChemicalDetail.get("evcrname").toString());
		//evCreate.setCriteriamaxName(Double.valueOf(costingChemicalDetail.get("evmaxmark").toString()));
		//evCreate.setCreatedUser(user.getUserid());
		financialCodesRepo.save(codeCreate);
		
		msg = "Success";
		}
		
		} catch (Exception e) {
		  e.printStackTrace();
		  msg = "Error";
		}
		return msg;
	}



	@Override
	public JSONObject getTenderDetailsIdsForTenderParticipators() {
		//LocalDate NowDate = LocalDate.now();
				LocalDateTime DateTime = LocalDateTime.now();
				JSONObject data = new JSONObject();
				try {
					List<Object[]> tenderDetails = appzTenderSubmissionsRepo.getTenderDetailsIdsForTenderParticipators(DateTime);
					JSONArray ar = new JSONArray();
					for (Object[] objects : tenderDetails) {
						if (objects[1] instanceof TenderDetails && objects[0] instanceof TenderSubmissions) {
							TenderSubmissions ts = (TenderSubmissions) objects[0];
							TenderDetails td = (TenderDetails) objects[1];
							
							JSONObject tenderIDs = new JSONObject();
							
							tenderIDs.put("tenderId", ts.getTenderNo());	
							tenderIDs.put("tenderName", td.getTendername());
							tenderIDs.put("tBidNumber", td.getBidno());
							
							ar.add(tenderIDs);
						}
					}
					data.put("data", ar);
					return data;
					//return tenderDetails;
				}  catch (Exception e) {
					e.printStackTrace();
					return null;
				}
	}

	@Override
	public JSONObject getTenderDetailsForViewTenderParticipators(Long id) {
		JSONObject tenderDetailsAndEligibleCat = new JSONObject();
		try {
			
			Optional<TenderDetails> tds = tenderDetailsRepo.findById(id);
			
			LocalDate ClosingDate = tds.get().getClosingdate();
			LocalTime ClosingTime = tds.get().getClosingtime();
			LocalDateTime ClosingDateAndTimeTable = tds.get().getClosingDateTime();
			System.out.println("closing date time from table ------------------->>>>"+ClosingDateAndTimeTable);
			
			String ClosingDateTime = ClosingDate+"T"+ClosingTime;
			LocalDateTime ClosingDateAndTime = LocalDateTime.parse(ClosingDateTime);
			System.out.println("closing date time --------------->"+ClosingDateAndTime);
			
			LocalDate NowDate = LocalDate.now();
			LocalTime NowTime = LocalTime.now();
			String NowDateTime = NowDate+"T"+NowTime;
			LocalDateTime NowDateAndTime = LocalDateTime.parse(NowDateTime);
			System.out.println("now date time --------------->"+NowDateAndTime);
			
			LocalDateTime NowDateAndTimee = LocalDateTime.now();
			System.out.println("LocalDateTime ------------------->>>>"+LocalDateTime.now());
			
			if(NowDateAndTimee.isBefore(ClosingDateAndTimeTable)) {
				System.out.println("Tender ID"+id); 
				  List<Object[]> tenderDetails =  tenderDetailsRepo.getTenderDetailsForView(id); 
				  //Optional<TenderDetails> tenderDetails =  tenderDetailsRepo.findById(tenderID); 
					for (Object[] objects : tenderDetails) {
						if (objects[0] instanceof TenderDetails && objects[1] instanceof EligibleCategory) {
							TenderDetails td = (TenderDetails) objects[0];
							EligibleCategory ec = (EligibleCategory) objects[1];
							
							tenderDetailsAndEligibleCat.put("TenderName", td.getTendername());
							tenderDetailsAndEligibleCat.put("BidNo", td.getBidno());
							tenderDetailsAndEligibleCat.put("CreatedBy", td.getCreatedBy());
							tenderDetailsAndEligibleCat.put("Categories", ec.getEligibleCategortName());
							tenderDetailsAndEligibleCat.put("ClosingDate", td.getClosingdate());
							tenderDetailsAndEligibleCat.put("ClosingTime", td.getClosingtime());
							tenderDetailsAndEligibleCat.put("Description", td.getComments());
							tenderDetailsAndEligibleCat.put("RfpID", td.getRfpId());
							
							tenderDetailsAndEligibleCat.put("CordinatorName1", td.getCordinator1name());
							tenderDetailsAndEligibleCat.put("CordinatorDesignation1", td.getCordinator1designation());
							tenderDetailsAndEligibleCat.put("CordinatorEmail1", td.getCordinator1email());
							tenderDetailsAndEligibleCat.put("CordinatorTP1", td.getCordinator1contactno());
							
							tenderDetailsAndEligibleCat.put("CordinatorName2", td.getCordinator2name());
							tenderDetailsAndEligibleCat.put("CordinatorDesignation2", td.getCordinator2designation());
							tenderDetailsAndEligibleCat.put("CordinatorEmail2", td.getCordinator2email());
							tenderDetailsAndEligibleCat.put("CordinatorTP2", td.getCordinator2contactno());
							
						}
					}
			}else {
				tenderDetailsAndEligibleCat.put("reponseText", "Already_Exists");
			}
				
				
			  
				  
				 
				/*
				 * if(tenderDetails.isPresent()) { return tenderDetails.get(); }else { return
				 * null; }
				 */
			  
			}
			 catch (Exception e) {
				 e.printStackTrace(); 
				 return null;
			 }
		return tenderDetailsAndEligibleCat;
	}

	@Override
	public JSONObject getTenderDetailsForViewTableForParticipators(HttpServletRequest request, Long id) {

		JSONObject data = new JSONObject();

		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long companyID = new Long(user.getCompanyCode());
			System.out.println("Long----- " + companyID);

			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			switch (request.getParameter("order[0][column]")) {
			case "1":
				sorting = "sc.scname";
				break;
			
			case "2": 
				sorting = "sc.scphoneno1"; 
				break;
			 
			
			case "3": 
				sorting = "sc.scemailadmin"; 
				break;
			 
			}

			Page<Object[]> tenderSubmissions = null;
			System.out.println("Suppiler Id----------->"+id);
			if (request.getParameter("order[0][dir]").equals("asc")) {
				tenderSubmissions = appzTenderSubmissionsRepo.getTenderDetailsForViewTableForParticipators(
						request.getParameter("search[value]"), id,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));
			} else {
				tenderSubmissions = appzTenderSubmissionsRepo.getTenderDetailsForViewTableForParticipators(
						request.getParameter("search[value]"), id,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
//				System.out.println("Size -------------"+supplier.size());
			int n = 1;
			for (Object[] tenderSubmission : tenderSubmissions.getContent()) {
				if (tenderSubmission[0] instanceof TenderSubmissions && tenderSubmission[1] instanceof User && tenderSubmission[2] instanceof SubCompany) 
				{

					TenderSubmissions ts = (TenderSubmissions) tenderSubmission[0];
					User users = (User) tenderSubmission[1];
					SubCompany subCompany = (SubCompany) tenderSubmission[2];
					
					JSONObject tenderSubmissionDetails = new JSONObject();

					// header.put("EcategoryID", ph.getEligibleCategortID());
					// header.put("categoryID", supplierproductdetails.getCategoryID());
					tenderSubmissionDetails.put("CompanyCode", users.getCompanyCode());
					//tenderSubmissionDetails.put("UserName", users.getUsername());
					tenderSubmissionDetails.put("UserName", subCompany.getScname());
					tenderSubmissionDetails.put("SubmittedDate", ts.getSubmittedDate());
					tenderSubmissionDetails.put("SubmittedTime", ts.getSubmittedTime());
					tenderSubmissionDetails.put("TenderId", ts.getTenderNo());
					tenderSubmissionDetails.put("SupplierId", ts.getSupplierId());
					tenderSubmissionDetails.put("FinancialFile", ts.getUploadFinancialsFileName());
					tenderSubmissionDetails.put("CompanyProfile", ts.getUploadCompanyProfileName());
					tenderSubmissionDetails.put("RfpFile", ts.getUploadRfpFileName());
					tenderSubmissionDetails.put("SupportDoc1", ts.getUploadSupportDocOne());
					tenderSubmissionDetails.put("SupportDoc2", ts.getUploadSupportDocTwo());
					tenderSubmissionDetails.put("SupportDoc3", ts.getUploadSupportDocThree());
					tenderSubmissionDetails.put("LogedUser", ts.getUserId());
					tenderSubmissionDetails.put("status", ts.getTenderResponse());
					
					tenderSubmissionDetails.put("TpNumber",subCompany.getScphoneno1());
					tenderSubmissionDetails.put("Email",subCompany.getScemailadmin());
					tenderSubmissionDetails.put("Index",n);
					
					ar.add(tenderSubmissionDetails);
					n++;
				}
			}

//			 Optional<Long> count  = poHeaderRepo.getPoDetailsSupplierCount(request.getParameter("search[value]"), code);
			data.put("recordsTotal", tenderSubmissions.getTotalElements());
			data.put("recordsFiltered", tenderSubmissions.getTotalElements());
			data.put("data", ar);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public Boolean getWetherUserExistsByCordinator1contactno(String enteredValue) {
		// TODO Auto-generated method stub
		return tenderDetailsRepo.existsByCordinator1contactno(enteredValue);
	}

	@Override
	public Boolean getWetherUserExistsByCordinator2email(String enteredValue) {
		// TODO Auto-generated method stub
		return tenderDetailsRepo.existsByCordinator2email(enteredValue);
	}

	@Override
	public Boolean getWetherUserExistsByCordinator2contactno(String enteredValue) {
		// TODO Auto-generated method stub
		return tenderDetailsRepo.existsByCordinator2contactno(enteredValue);
	}

	@Override
	public Boolean getWetherUserExistsByCordinator1email(String enteredValue) {
		// TODO Auto-generated method stub
		return tenderDetailsRepo.existsByCordinator1email(enteredValue);
	}





@Override
	public String testEmails(JSONObject data, HttpServletRequest request) {
		String subject = "Common";
		String Email = "smab.kulasinghe@gmail.com";
		
		
		String content = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Hello,</span></p>";
		CommonEmail comEmail = new CommonEmail("Test Mail", content);
		
		System.out.println(comEmail.getHeadingName());
		System.out.println(comEmail.getContent());
		System.out.println("getContentOfEmail"+comEmail.getContentOfEmail());
		System.out.println("getHeadingOfEmail"+comEmail.getHeadingOfEmail());
		
		String body = comEmail.getFirstPart()+comEmail.getHeadingOfEmail()+comEmail.getSecondPart()+comEmail.getContentOfEmail()+comEmail.getThirdPart();
		
		
		 new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				List<String> to = new ArrayList<String>();
				to.add(Email);
				
				common.sendEMailHtml(to, subject, body);
			}
		}).start();
		 return null;
	}

@Override
public JSONObject addTenderAuthReason(JSONObject data, String status) {
	JSONObject ob = new JSONObject();
	String msg = null;
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	User us = (User) authentication.getPrincipal();
	String userId = (us.getUserid());
	
	try {
		Long id = Long.valueOf(data.get("selectedTenderID").toString());
		
		Optional<TenderDetails> tender = tenderDetailsRepo.findById(id);
		if(tender.isPresent()) {
			System.out.println("Add tender reason");
			TenderDetails tenderItem = tender.get();
			tenderItem.setApproveReason(data.get("comment").toString());
			tenderItem.setApprovedBy(us.getUserid());
			tenderItem.setApproveDateTime(LocalDateTime.now());
			
			if (status.equals("approve")) {
			//tender.setApprovalstatus(Long.valueOf(data.get("true").toString()));
				tenderItem.setApprovalstatus("3");
				tenderItem.setStatus("3");
				tenderDetailsRepo.save(tenderItem);			
			 
			ob.put("responseText", "Success");	
			ob.put("code", "01");
		}else {
			// tender.get().setTrnderid(user.getTrnderid());
			tenderItem.setApprovalstatus("1");
			tenderItem.setStatus("19");
			tenderDetailsRepo.save(tenderItem);
			ob.put("responseText", "Success");	
			ob.put("code", "02");
		}
		}
	}catch (Exception e) {
	     e.printStackTrace();
	     return null;
	}
	return ob;
}

@Override
public JSONObject TenderAuthorization(long id, String approve) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String financialCodeCreation(JSONObject data, HttpServletRequest request) {
	FinancialCodes fc = new FinancialCodes();

	String msg;
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	User user = (User) authentication.getPrincipal();
	String userId = user.getUserid();
	Long deptID = user.getDeptID();

	try {
		Long active = (long) 3;
		fc.setCode(data.get("financialCode").toString());
		fc.setDescription(data.get("description").toString());
		fc.setStatus(active);
		fc = financialCodesRepo.save(fc);
		msg = "Success";
	} catch (Exception e) {
		e.printStackTrace();
		msg = "Error";
	}
	return msg;
}

@Override
public JSONObject financialCodeView(HttpServletRequest request) {
	try {

		JSONObject data = new JSONObject();
		Page<FinancialCodes> get_Financial_Code_View = null;
		data.put("draw", request.getParameter("draw"));
		String sorting = "";

		switch (request.getParameter("order[0][column]")) {
		case "1":
			sorting = "code";
			break;
		case "2":
			sorting = "description";
			break;


		}

		if (request.getParameter("order[0][dir]").equals("asc")) {
			System.out.println("ASC ");
			get_Financial_Code_View = rfpRepo.getFinancialCodeView(request.getParameter("search[value]"),						
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.ASC, sorting)));

			System.out.println("end ");
		} else {
			System.out.println("DESC ");
			get_Financial_Code_View = rfpRepo.getFinancialCodeView(request.getParameter("search[value]"),						
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.DESC, sorting)));
//			contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
		}

		JSONArray ar = new JSONArray();
		long count = get_Financial_Code_View.getTotalElements();
		long n = 1;
		for (FinancialCodes objects : get_Financial_Code_View.getContent()) {

				JSONObject ob = new JSONObject();

				FinancialCodes fc = (FinancialCodes) objects;

				ob.put("index", n);
				ob.put("financialCode", fc.getCode());
				ob.put("description", fc.getDescription());
				ob.put("financialId", fc.getFinancialCodeId());
				ob.put("status", fc.getStatus());
				ar.add(ob);
				n++;

		}


		data.put("recordsTotal", count);
		data.put("recordsFiltered", count);
		data.put("data", ar);
		System.out.println("data ds--->" + data);
		return data;


	}catch(Exception e) {
		e.printStackTrace();
		return null;
	}
}

@Override
public JSONObject financialCodeAuth(long id, String approve) {
	Optional<FinancialCodes> fc = financialCodesRepo.findById(id);
	JSONObject ob = new JSONObject();
	Long active = (long) 3;
	Long deactive = (long) 4;
	Long disable = (long) 6;
	try {

		if (fc.isPresent()) {

			if (approve.equals("active")) {

				fc.get().setStatus(active);
				financialCodesRepo.save(fc.get());

			}else if(approve.equals("deactive")) {

				fc.get().setStatus(deactive);
				financialCodesRepo.save(fc.get());

			}else {
				fc.get().setStatus(disable);
				financialCodesRepo.save(fc.get());
			}
		}

	} catch (Exception e) {
		e.printStackTrace();
		return ob;
	}

	return ob;
}

@Override
public JSONObject editFinancialCode(JSONObject data) {
	JSONObject ob = new JSONObject();
	String msg = null;
	try {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		//System.out.println(data.get("rfpId").toString());
		Long financialId = Long.valueOf(data.get("financialId").toString());

		Optional<FinancialCodes> fc = financialCodesRepo.findById(financialId);
		if (fc.isPresent()) {

			fc.get().setCode(data.get("financialCodeVal").toString());
			fc.get().setDescription(data.get("descriptionVal").toString());
			financialCodesRepo.save(fc.get());

		}
		ob.put("responseText", "Success");
	}catch(Exception e) {
		e.printStackTrace();

		return null;
	}
	return ob;
}

@Override
public Boolean getWetherFinancialExists(String enteredValue) {
	return financialCodesRepo.existsByCodeAndStatus(enteredValue,Long.valueOf(3));
}

@Override
public Boolean getWetherFinancialDesExists(String enteredValue) {
	return financialCodesRepo.existsByDescriptionAndStatus(enteredValue,Long.valueOf(3));
}

@Override
public List<TenderDetails> getTenderForFinancialPerTender() {
	try {
		List<TenderDetails> TenderDetails = tenderDetailsRepo.getTenderForFinancialPerTender();

		return TenderDetails;
	}  catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

@Override
public List<FinancialCodes> getFinancialForFinancialPerTender() {
	try {
		List<FinancialCodes> TenderDetails = tenderDetailsRepo.getFinancialForFinancialPerTender();

		return TenderDetails;
	}  catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

@Override
public String financialCodePerTenderSubmit(JSONObject data, HttpServletRequest request) {
	FinancialDetailsPerTender fd = new FinancialDetailsPerTender();

	String msg;
	//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	//User user = (User) authentication.getPrincipal();
	//String userId = user.getUserid();
	//Long deptID = user.getDeptID();

	try {
		//Long active = (long) 3;
		fd.setTenderID(Long.valueOf(data.get("tenderId").toString()));
		fd.setFinancialCodeId(Long.valueOf(data.get("financialCodeId").toString()));
		fd.setCappex(data.get("cappex").toString());
		financialDetailsPerTenderRepo.save(fd);
		msg = "Success";
	} catch (Exception e) {
		e.printStackTrace();
		msg = "Error";
	}
	return msg;
}

@Override
public JSONObject financialPerTenderView(HttpServletRequest request, Long tenderId) {
	try {

		JSONObject data = new JSONObject();
		Page<Object[]> get_Financial_Code_View = null;
		data.put("draw", request.getParameter("draw"));
		String sorting = "";

		switch (request.getParameter("order[0][column]")) {
		case "1":
			sorting = "code";
			break;
		case "2":
			sorting = "td.bidno";
			break;
		case "3":
			sorting = "td.tendername";
			break;

		}

		if (request.getParameter("order[0][dir]").equals("asc")) {
			System.out.println("ASC ");
			get_Financial_Code_View = tenderDetailsRepo.financialPerTenderView(request.getParameter("search[value]"),tenderId,						
					PageRequest.of( 
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.ASC, sorting)));

			System.out.println("end "); 
		} else {
			System.out.println("DESC ");
			get_Financial_Code_View = tenderDetailsRepo.financialPerTenderView(request.getParameter("search[value]"),tenderId,						
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.DESC, sorting)));
//			contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
		}

		JSONArray ar = new JSONArray();
		long count = get_Financial_Code_View.getTotalElements();
		long n = 1;
		for (Object[] objects : get_Financial_Code_View.getContent()) {
			if (objects[0] instanceof FinancialCodes && objects[1] instanceof FinancialDetailsPerTender && objects [2] instanceof TenderDetails) 
			{

				FinancialCodes fc = (FinancialCodes) objects[0];
				FinancialDetailsPerTender fd = (FinancialDetailsPerTender) objects[1];
				TenderDetails td = (TenderDetails) objects[2];

				JSONObject ob = new JSONObject();



				ob.put("index", n);
				ob.put("financialCode", fc.getCode());
				ob.put("cappex", fd.getCappex());
				ob.put("tenderNumber", td.getBidno());
				ob.put("tenderName", td.getTendername());

				ar.add(ob);
				n++;
			}
		}


		data.put("recordsTotal", count);
		data.put("recordsFiltered", count);
		data.put("data", ar);
		System.out.println("data ds--->" + data);
		return data;


	}catch(Exception e) {
		e.printStackTrace();
		return null;
	}
}

@Override
public JSONObject getFinancialDocForView(HttpServletRequest request, Long id) {

	JSONObject data = new JSONObject();

	try {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long companyID = new Long(user.getCompanyCode());
		System.out.println("Long----- " + companyID);

		data.put("draw", request.getParameter("draw"));
		String sorting = "";
		switch (request.getParameter("order[0][column]")) {
		case "1":
			sorting = "sc.scname";
			break; 
		
		case "2": 
			sorting = "sc.scemailadmin"; 
			break;
		 
		}

		Page<Object[]> tenderSubmissions = null;
		System.out.println("Suppiler Id----------->"+id);
		if (request.getParameter("order[0][dir]").equals("asc")) {
			tenderSubmissions = appzTenderSubmissionsRepo.getTenderSubmissionDataForFinancialDocView(
					request.getParameter("search[value]"), id,
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.ASC, sorting)));
		} else {
			tenderSubmissions = appzTenderSubmissionsRepo.getTenderSubmissionDataForFinancialDocView(
					request.getParameter("search[value]"), id,
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.DESC, sorting)));
		}

		JSONArray ar = new JSONArray();
//			System.out.println("Size -------------"+supplier.size());
		int n = 1;
		for (Object[] tenderSubmission : tenderSubmissions.getContent()) {
			if (tenderSubmission[0] instanceof TenderSubmissions && tenderSubmission[1] instanceof FinacialResponsesVendor && tenderSubmission[2] instanceof SubCompany) 
			{

				TenderSubmissions ts = (TenderSubmissions) tenderSubmission[0];
				FinacialResponsesVendor f_response = (FinacialResponsesVendor) tenderSubmission[1];
				SubCompany subCompany = (SubCompany) tenderSubmission[2];
				
				JSONObject tenderSubmissionDetails = new JSONObject();

				tenderSubmissionDetails.put("UserName", subCompany.getScname());
				tenderSubmissionDetails.put("Index",n);
				tenderSubmissionDetails.put("financialDoc",f_response.getFinancialDocument());
				System.out.println("Document"+f_response.getFinancialDocument());
				ar.add(tenderSubmissionDetails);
				n++;
			
			}
		}

//		 Optional<Long> count  = poHeaderRepo.getPoDetailsSupplierCount(request.getParameter("search[value]"), code);
		data.put("recordsTotal", tenderSubmissions.getTotalElements());
		data.put("recordsFiltered", tenderSubmissions.getTotalElements());
		data.put("data", ar);

	} catch (Exception e) {
		e.printStackTrace();
	}
	return data;
	
}



@Override
public String tendersubmitRfp(JSONObject data, HttpServletRequest request) {
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	User user = (User) authentication.getPrincipal();
	String status = "2";
	String tenderResponse = "8";
	String tenderResponseParticipate = "5";
	String tenderClose = "15";
	String msg = null;
	Long RfpId = Long.valueOf(data.get("rfpId").toString());
	try {
		Optional<TenderDetails> tds = tenderDetailsRepo.findById(Long.valueOf(data.get("tId").toString()));
		
		LocalDate ClosingDate = tds.get().getClosingdate();
		LocalTime ClosingTime = tds.get().getClosingtime();
		
		LocalDateTime ClosingDateAndTimeTable = tds.get().getClosingDateTime();
		
		String ClosingDateTime = ClosingDate+"T"+ClosingTime;
		LocalDateTime ClosingDateAndTime = LocalDateTime.parse(ClosingDateTime);
		System.out.println("closing date time --------------->"+ClosingDateAndTime);
		
		LocalDate NowDate = LocalDate.now();
		LocalTime NowTime = LocalTime.now();
		String NowDateTime = NowDate+"T"+NowTime;
		LocalDateTime NowDateAndTime = LocalDateTime.parse(NowDateTime);
		System.out.println("now date time --------------->"+NowDateAndTime);
		
		LocalDateTime NowDateAndTimee = LocalDateTime.now();
		System.out.println("LocalDateTime ------------------->>>>"+NowDateAndTimee);
		System.out.println("ClosingDateAndTimeTable ------------------->>>>"+ClosingDateAndTimeTable);
		
		Optional<TenderSubmissions> optTs = appzTenderSubmissionsRepo.findByTenderNoAndSupplierId(
				Long.valueOf(data.get("tId").toString()), Long.valueOf(user.getCompanyCode()));
		
		if(NowDateAndTimee.isBefore(ClosingDateAndTimeTable)) {
			Reader reader1 = null;
			
			if (optTs.isPresent()) {
				String filename = "rfpfile";
				if (filename == "rfpfile") {
					String headerData[]=data.get("undefined").toString().split("base64,");
					String extention[]=data.get("undefined").toString().split("[/;]");
					
					String exten = "csv";
					String head=headerData[1].substring(0, (headerData[1].length()));
					new DeEnCode().decodeMethod(tenderSubmitDataPath,  data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/rfpFile",exten, head);
					
					optTs.get().setUploadRfpFileName(
							tenderSubmitDataPath + data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/rfpFile." + exten);
					appzTenderSubmissionsRepo.save(optTs.get());
				}
				
				ArrayList<String> TableFieldNames = new ArrayList<>();
				
				System.out.println("RFP ID ----------------->"+RfpId);
				
				List<RFPDetails> rfp = rfpDetailRepo.getRfpDetails(RfpId);
				
				for (RFPDetails refpDetail : rfp) {				
						
					String TableField = refpDetail.getRfpDFiledNmae();
					TableFieldNames.add(TableField);
					
				}
				System.out.println("Feeeeeeeilds from table-------------->" + TableFieldNames);
				
				reader1 = Files.newBufferedReader(
						Paths.get(tenderSubmitDataPath + data.get("tId").toString()+"-"+user.getCompanyCode().toString() + "/rfpFile" + ".csv"));
				CSVParser csvParserHead = new CSVParser(reader1,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
				
				ArrayList<String> FieldNames = new ArrayList<>();
				ArrayList<String> Comments = new ArrayList<>();
				ArrayList<String> YesNos = new ArrayList<>();
				ArrayList<String> Units = new ArrayList<>();
				ArrayList<String> QTYs = new ArrayList<>();
				ArrayList<String> Prices = new ArrayList<>();
				for (CSVRecord csvRecord : csvParserHead) {
					
					String FieldName = csvRecord.get("Field Name");
					String Comment = csvRecord.get("Comment");
					String YesNo = csvRecord.get("Y/N");
					String Unit = csvRecord.get("Unit");
					String QTY = csvRecord.get("Quantity");
					String Price = csvRecord.get("Price");
					
					FieldNames.add(FieldName);
					Comments.add(Comment);
					YesNos.add(YesNo);
					Units.add(Unit);
					QTYs.add(QTY);
					Prices.add(Price);
				}
				csvParserHead.close();
				System.out.println("Feeeeeeeilds from file -------------->" + FieldNames);
				
				
				
					System.out.println("True");
					if(FieldNames.equals(TableFieldNames)) {
						int n=0;
						for (RFPDetails rfpDetail : rfp) {
							
							RFPDetailsResponse rfpDetailsResponse = new RFPDetailsResponse();
							rfpDetailsResponse.setCompanyCode(Long.valueOf(user.getCompanyCode().toString()));
							rfpDetailsResponse.setRfpDId(rfpDetail.getRfpDId());
							rfpDetailsResponse.setRfpHId(rfpDetail.getRfpHId());
							rfpDetailsResponse.setRfpID(rfpDetail.getRfpId());
							rfpDetailsResponse.setTenderID(Long.valueOf(data.get("tId").toString()));
							rfpDetailsResponse.setUserId(user.getUserid().toString());
							rfpDetailsResponse.setVenderComment(Comments.get(n));
							rfpDetailsResponse.setVenderResponse(YesNos.get(n));
							rfpDetailsResponse.setUnits(Units.get(n));
							rfpDetailsResponse.setQty(Long.valueOf(QTYs.get(n)));
							rfpDetailsResponse.setPrice(Long.valueOf(Prices.get(n)));
							rfpDetailsResponseRepo.save(rfpDetailsResponse);
							n++;
							
						}
						
						System.out.println("Arrays are equal.");
						msg = "Success";
						
					}else {
						if (optTs.isPresent()) {
							optTs.get().setTenderResponse(tenderResponseParticipate);
							optTs.get().setTenderEligibility(tenderResponseParticipate);
							optTs.get().setUploadCompanyProfileName(null);
							optTs.get().setUploadFinancialsFileName(null);
							optTs.get().setUploadRfpFileName(null);
							optTs.get().setUploadSupportDocOne(null);
							optTs.get().setUploadSupportDocTwo(null);
							optTs.get().setUploadSupportDocThree(null);
							appzTenderSubmissionsRepo.save(optTs.get());
						}
						
						System.out.println("Arrays are not equal.");  
						msg = "FilesNotEqual";
					}
			}
			
		}else {
			if (optTs.isPresent()) {
				optTs.get().setTenderResponse(tenderClose);
				appzTenderSubmissionsRepo.save(optTs.get());
			}
			System.out.println("False");
			msg = "TenderExpired";
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		msg = "Error";
	}
	return msg;
}

@Override
public String aditionalFileForTenderParams(JSONObject data, HttpServletRequest request) {
	String msg = null;
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	User user = (User) authentication.getPrincipal();
	Long userCompanyID = new Long(user.getCompanyCode());
	
	String userId = user.getUserid();
	
	AditionalFilesForTender addFileParams = new AditionalFilesForTender();
	
	try {
		
		addFileParams.setTenderId(Long.valueOf(data.get("tenderId").toString()));
		addFileParams.setFileName(data.get("fileName").toString());
		addFileParams.setFileNameWithoutSpace(data.get("fileName").toString().replaceAll("\\s", ""));
		addFileParams.setStatus("3");
		addFileParams.setCreatedDateTime(LocalDateTime.now());
		addFileParams.setCreatedUser(userId);
		addFileParams.setLockStatus(Long.valueOf(23));
		aditionalFilesForTenderRepo.save(addFileParams);
		msg = "Success";
	} catch (Exception e) {
		e.printStackTrace();
		msg = "Error";
	}
	return msg;
}

@Override
public JSONObject aditionalDetailsForEditTble(HttpServletRequest request, Long id) {
	JSONObject data = new JSONObject();

	try {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long companyID = new Long(user.getCompanyCode());
		System.out.println("Long----- " + companyID);

		data.put("draw", request.getParameter("draw"));
		String sorting = "";
		switch (request.getParameter("order[0][column]")) {
		case "1":
			sorting = "fileName";
			break; 
		
		}

		Page<AditionalFilesForTender> aditionalFiles = null;
		System.out.println("Suppiler Id----------->"+id);
		if (request.getParameter("order[0][dir]").equals("asc")) {
			aditionalFiles = aditionalFilesForTenderRepo.aditionalDetailsForEditTble(
					request.getParameter("search[value]"), id,
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.ASC, sorting)));
		} else {
			aditionalFiles = aditionalFilesForTenderRepo.aditionalDetailsForEditTble(
					request.getParameter("search[value]"), id,PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.DESC, sorting)));
		}

		JSONArray ar = new JSONArray();
					int n = 1;
		for (AditionalFilesForTender aditionalFile : aditionalFiles.getContent()) {
			
				JSONObject aditionalFileDetails = new JSONObject();

				aditionalFileDetails.put("fileName", aditionalFile.getFileName());
				aditionalFileDetails.put("status", aditionalFile.getStatus());
				aditionalFileDetails.put("id", aditionalFile.getAditionalDetailsId());
				aditionalFileDetails.put("fileStatus", aditionalFile.getLockStatus());
				aditionalFileDetails.put("index",n);
				
				ar.add(aditionalFileDetails);
				n++;
			
			
		}

//		 Optional<Long> count  = poHeaderRepo.getPoDetailsSupplierCount(request.getParameter("search[value]"), code);
		data.put("recordsTotal", aditionalFiles.getTotalElements());
		data.put("recordsFiltered", aditionalFiles.getTotalElements());
		data.put("data", ar);

	} catch (Exception e) {
		e.printStackTrace();
	}
	return data;
}

@Override
public List<TenderDetails> getTenderExtendForTenderDetails() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	User user = (User) authentication.getPrincipal();
	
	try {
		List<TenderDetails> next = tenderDetailsRepo.getTenderExtendForTenderDetails();
		return next;
	}  catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

@Override
public JSONObject updateTenderExtend(JSONObject data) {
	
	JSONObject ob = new JSONObject();
	String msg = null;
	
	try {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long tenderId = Long.valueOf(data.get("tenderId").toString());
		
		 Optional<TenderDetails> tenderDetails = tenderDetailsRepo.findById(tenderId);
		if (tenderDetails.isPresent()) {
			
			tenderDetails.get().setTrnderid(Long.valueOf(data.get("tenderId").toString()));
			
			 LocalDate createdDate = Instant.ofEpochMilli(new Long(data.get("closingdate").toString()))
			 .atZone(ZoneId.systemDefault()).toLocalDate();
			 LocalTime createdTimE = LocalTime.parse(data.get("closingtime").toString());

			 tenderDetails.get().setClosingdate(Instant.ofEpochMilli(new Long(data.get("closingdate").toString()))
			 .atZone(ZoneId.systemDefault()).toLocalDate());
						
			System.out.println("Closing time " + data.get("closingtime").toString());
			tenderDetails.get().setClosingtime(LocalTime.parse(data.get("closingtime").toString()));
			
			tenderDetailsRepo.save(tenderDetails.get());
		}
		
	ob.put("responseText", "Success");
	
	}catch(Exception e) {
	 e.printStackTrace();
	 return null;
	}
	return ob;	
}



@Override
public List<TenderDetails> getViewFinancialDetails() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	User user = (User) authentication.getPrincipal();
	
	try {
		List<TenderDetails> next = tenderDetailsRepo.getViewFinancialForDetails();
		return next;
	}  catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}



@Override
public JSONObject getTenderDetailsForFinancialView(HttpServletRequest request, Long tendrID) {
	try {
		JSONObject data= new JSONObject();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		data.put("draw", request.getParameter("draw"));
		String sorting = "";
		
		switch (request.getParameter("order[0][column]")) {
		  case "1":
			sorting = "sc.scname";
		    break;
		  case "2":
			sorting = "sc.scphoneno1";
			break;
		  case "3":
			sorting = "sc.scemailadmin";
			break;
		}
		
		Page<Object[]> viewFinance= null;
		
		if (request.getParameter("order[0][dir]").equals("asc")) {
			viewFinance = appzTenderSubmissionsRepo.getTenderDetailsForFinancialView(request.getParameter("search[value]"), tendrID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		}
		else {
			viewFinance = appzTenderSubmissionsRepo.getTenderDetailsForFinancialView(request.getParameter("search[value]"), tendrID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
		}
		
		JSONArray ar =  new JSONArray();
		int i =0;
		for (Object[] tenderSubmission : viewFinance.getContent()) {
			if (tenderSubmission[0] instanceof TenderSubmissions && tenderSubmission[1] instanceof User && tenderSubmission[2] instanceof SubCompany) 
			{
			TenderSubmissions ts = (TenderSubmissions) tenderSubmission[0];
			User users = (User) tenderSubmission[1];
			SubCompany subCompany = (SubCompany) tenderSubmission[2];
			
			JSONObject ob = new JSONObject();
			
			ob.put("index", ++i);
			ob.put("supName",subCompany.getScname());
			ob.put("phoneNumber",subCompany.getScphoneno1());
			ob.put("email",subCompany.getScemailadmin());

			ob.put("CompanyCode", users.getCompanyCode());
			ob.put("supName", subCompany.getScname());
			ob.put("TenderId", ts.getTenderNo());
			ob.put("SupplierId", ts.getSupplierId());
			ob.put("FinancialFile", ts.getUploadFinancialsFileName());
//			ob.put("financialDoc", ts.getUploadFinancialsFileName());
//			ob.put("LogedUser", ts.getUserId());
			ar.add(ob);
			}
	}
		 data.put("recordsTotal",viewFinance.getTotalElements());
		 data.put("recordsFiltered",viewFinance.getTotalElements());
		 data.put("data", ar);
		return data;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}



@Override
public JSONObject getViewFinancialDetailsForCappex(HttpServletRequest request, Long tenderId, Long supplierId) {
	JSONObject data = new JSONObject();

	try {
		data.put("draw", request.getParameter("draw"));
		String sorting = "";
		switch (request.getParameter("order[0][column]")) {
		case "0":
			sorting = "currency";
			break;
		 case "1":
			 sorting = "amount"; 
			 break;
		  case "2": 
			  sorting = " comment"; 
			  break;
		  case "3": 
			  sorting = "fc.description"; 
			  break;
		}

		Page<Object[]> cappexes = null;
		
		if (request.getParameter("order[0][dir]").equals("asc")) {
		System.out.println("blaa blaaaa");
			cappexes = tenderDetailsRepo.getViewFinancialDetailsForCappex(
					request.getParameter("search[value]"), tenderId,supplierId,
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.ASC, sorting)));
		} else {
			
			cappexes = tenderDetailsRepo.getViewFinancialDetailsForCappex(
					request.getParameter("search[value]"), tenderId,supplierId,
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.DESC, sorting)));
		}

		JSONArray ar = new JSONArray();
		for (Object[] cappex : cappexes.getContent()) {
			if (cappex[0] instanceof FinancialValuesPerTender && cappex[1] instanceof FinancialDetailsPerTender && cappex[2] instanceof FinancialCodes) 
			{
				FinancialValuesPerTender finValPerTender = (FinancialValuesPerTender) cappex[0];
				FinancialDetailsPerTender finDetailPerTender = (FinancialDetailsPerTender) cappex[1];
				FinancialCodes finCode = (FinancialCodes) cappex[2];
				
				JSONObject financialDetails = new JSONObject();
				
				financialDetails.put("CurrencyType", finValPerTender.getCurrency());
				financialDetails.put("Amount", finValPerTender.getAmount());
				financialDetails.put("Comments", finValPerTender.getComment());
				financialDetails.put("FinacialCodeDes", finCode.getDescription());
				ar.add(financialDetails);
			}
		}
		data.put("recordsTotal", cappexes.getTotalElements());
		data.put("recordsFiltered", cappexes.getTotalElements());
		data.put("data", ar);

	} catch (Exception e) {
		e.printStackTrace();
	}
	return data;
}
//			System.out.println("Size -------------"+supplier.size());
		

@Override
public JSONObject aditionalFilesStatus(long id, String approve) {
	Optional<AditionalFilesForTender> af = aditionalFilesForTenderRepo.findById(id);
	JSONObject ob = new JSONObject();
	String active = "3";
	String deactive = "6";
	try {

		if (af.isPresent()) {

			if (approve.equals("active")) {

				af.get().setStatus(active);
				aditionalFilesForTenderRepo.save(af.get());

			}else if(approve.equals("deactive")) {

				af.get().setStatus(deactive);
				aditionalFilesForTenderRepo.save(af.get());

			}
		}

	} catch (Exception e) {
		e.printStackTrace();
		return ob;
	}
	return ob;
}

@Override
public JSONObject editAdditionalFileName(JSONObject data) {
	JSONObject ob = new JSONObject();
	String msg = null;
	try {
		
		Long id = Long.valueOf(data.get("id").toString());

		Optional<AditionalFilesForTender> af = aditionalFilesForTenderRepo.findById(id);
		if (af.isPresent()) {

			af.get().setFileName(data.get("aditionalFilesNameEdit").toString());
			af.get().setFileNameWithoutSpace(data.get("aditionalFilesNameEdit").toString().replaceAll("\\s", ""));
			aditionalFilesForTenderRepo.save(af.get());

		}
		ob.put("responseText", "Success");
	}catch(Exception e) {
		e.printStackTrace();

		return null;
	}
	return ob;
}

@Override
public Boolean getWetherAddtionalFilesExists(Long tenderId, String fileName) {
	return aditionalFilesForTenderRepo.existsByTenderIdAndFileName(tenderId,fileName);
}

@Override
public JSONObject getAdditionalFilesToPage(Long tenderId) {
	JSONObject data = new JSONObject();
	Long active = (long) 3;
	try {
		List<AditionalFilesForTender> af = aditionalFilesForTenderRepo.getAdditionalFilesToPage(tenderId);
		JSONArray ar = new JSONArray();
		for (AditionalFilesForTender objects : af) {
			
				JSONObject afp = new JSONObject();
				afp.put("fileId", objects.getAditionalDetailsId());
				afp.put("fileName", objects.getFileName());
				
				ar.add(afp);
			
		}
		System.out.println(ar);
		data.put("data", ar);
		return data;
		// return tenderDetails;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

@Override
public String aditionalFileUpload(JSONObject data, HttpServletRequest request, Long id) {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	User user = (User) authentication.getPrincipal();
	
	String msg = null;
	try {
		
		Optional<AditionalFilesForTender> af = aditionalFilesForTenderRepo.findById(id);
		if (af.isPresent()) {
			
			String headerData[]=data.get("undefined").toString().split("base64,");
			String extention[]=data.get("undefined").toString().split("[/;]");
			String exten = extention[1];
			System.out.println("split------------------->"+exten);
			
			String head=headerData[1].substring(0, (headerData[1].length()));
			new DeEnCode().decodeMethod(tenderAdditinalPath,af.get().getTenderId()+"-"+af.get().getAditionalDetailsId(),exten, head);
			
			af.get().setFilePath(tenderAdditinalPath+af.get().getTenderId()+"-"+af.get().getAditionalDetailsId()+".pdf");
			af.get().setUpdatedDateTime(LocalDateTime.now());
			af.get().setUpdatedUser(user.getUserid());
			af.get().setLockStatus(Long.valueOf(6));
			aditionalFilesForTenderRepo.save(af.get());
		}
		
		msg = "Success";
	} catch (Exception e) {
		e.printStackTrace();
		msg = null;
	}
	return msg;
}

@Override
public JSONObject aditionalDetailsForViewTble(HttpServletRequest request, Long tenderId) {
	JSONObject data = new JSONObject();

	try {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long companyID = new Long(user.getCompanyCode());
		System.out.println("Long----- " + companyID);

		data.put("draw", request.getParameter("draw"));
		String sorting = "";
		switch (request.getParameter("order[0][column]")) {
		case "1":
			sorting = "fileName";
			break; 
		
		}

		Page<AditionalFilesForTender> aditionalFiles = null;
		
		if (request.getParameter("order[0][dir]").equals("asc")) {
			aditionalFiles = aditionalFilesForTenderRepo.aditionalDetailsForViewTble(
					request.getParameter("search[value]"), tenderId,
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.ASC, sorting)));
		} else {
			aditionalFiles = aditionalFilesForTenderRepo.aditionalDetailsForViewTble(
					request.getParameter("search[value]"), tenderId,
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.DESC, sorting)));
		}

		JSONArray ar = new JSONArray();
//			System.out.println("Size -------------"+supplier.size());
		int n = 1;
		for (AditionalFilesForTender aditionalFile : aditionalFiles.getContent()) {
			
				JSONObject aditionalFileDetails = new JSONObject();

				aditionalFileDetails.put("fileName", aditionalFile.getFileName());
				aditionalFileDetails.put("status", aditionalFile.getStatus());
				aditionalFileDetails.put("id", aditionalFile.getAditionalDetailsId());
				aditionalFileDetails.put("index",n);
				
				ar.add(aditionalFileDetails);
				n++;
			
			
		}

//		 Optional<Long> count  = poHeaderRepo.getPoDetailsSupplierCount(request.getParameter("search[value]"), code);
		data.put("recordsTotal", aditionalFiles.getTotalElements());
		data.put("recordsFiltered", aditionalFiles.getTotalElements());
		
		data.put("data", ar);

	} catch (Exception e) {
		e.printStackTrace();
	}
	return data;
}

@Override
public JSONObject lockAdditionalFileUpload(JSONObject data) {
	JSONObject ob = new JSONObject();
	String msg = null;
	try {
		
		

		List<AditionalFilesForTender> af = aditionalFilesForTenderRepo.findByTenderIdAndStatus(Long.valueOf(data.get("tenderId").toString()));
		JSONArray ar = new JSONArray();
		for (AditionalFilesForTender objects : af) {
			
				objects.setLockStatus(Long.valueOf(23));
				aditionalFilesForTenderRepo.save(objects);
			
		}
		
		ob.put("responseText", "Success");
	}catch(Exception e) {
		e.printStackTrace();

		return null;
	}
	return ob;
}




@Override
public JSONObject getViewFinancialDetailsForOppex(HttpServletRequest request, Long tenderId, Long supplierId) {
	JSONObject data = new JSONObject();

	try {
		data.put("draw", request.getParameter("draw"));
		String sorting = "";
		switch (request.getParameter("order[0][column]")) {
		case "0":
			sorting = "currency";
			break;
		  case "1": 
			  sorting = "amount"; 
			  break;
		  case "2": 
			  sorting = " comment"; 
			  break;
		  case "3": 
			  sorting = "fc.description"; 
			  break;
		}

		Page<Object[]> cappexes = null;
		
		if (request.getParameter("order[0][dir]").equals("asc")) {
		System.out.println("blaa blaaaa");
			cappexes = tenderDetailsRepo.getViewFinancialDetailsForOppex(
					request.getParameter("search[value]"), tenderId,supplierId,
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.ASC, sorting)));
		} else {
			cappexes = tenderDetailsRepo.getViewFinancialDetailsForOppex(
					request.getParameter("search[value]"), tenderId,supplierId,
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.DESC, sorting)));
		}

		JSONArray ar = new JSONArray();

		for (Object[] cappex : cappexes.getContent()) {
			if (cappex[0] instanceof FinancialValuesPerTender && cappex[1] instanceof FinancialDetailsPerTender && cappex[2] instanceof FinancialCodes) 
			{
				FinancialValuesPerTender finValPerTender = (FinancialValuesPerTender) cappex[0];
				FinancialDetailsPerTender finDetailPerTender = (FinancialDetailsPerTender) cappex[1];
				FinancialCodes finCode = (FinancialCodes) cappex[2];
				
				JSONObject financialDetails = new JSONObject();

				financialDetails.put("CurrencyType", finValPerTender.getCurrency());
				financialDetails.put("Amount", finValPerTender.getAmount());
				financialDetails.put("Comments", finValPerTender.getComment());
				financialDetails.put("FinacialCodeDes", finCode.getDescription());
				ar.add(financialDetails);
			}
		}
		data.put("recordsTotal", cappexes.getTotalElements());
		data.put("recordsFiltered", cappexes.getTotalElements());
		data.put("data", ar);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return data;
}



@Override
public JSONObject getViewFinancialDetailsForCappexOppex(HttpServletRequest request, Long tenderId, Long supplierId) {
	JSONObject data = new JSONObject();
	
	try {
		Optional<FinacialResponsesVendor> dataOfAmounts = tenderDetailsRepo.getViewFinancialDetailsForCappexOppex(tenderId,supplierId);
		data.put("Amountlkr", dataOfAmounts.get().getAmountlkr());
		data.put("AmountUsd", dataOfAmounts.get().getAmountusd());
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return data;
}

@Override
public JSONObject getResubmitFinancialDetailsAllowOption() {
	LocalDateTime DateTime = LocalDateTime.now();
	JSONObject data = new JSONObject();
	try {
		List<Object[]> tenderDetails = tenderDetailsRepo.getResubmitFinancialDetailsAllowOption(DateTime);
		JSONArray ar = new JSONArray();
		for (Object[] objects : tenderDetails) {
			if (objects[0] instanceof TenderDetails && objects[1] instanceof EvaluationMarksAll) {
				//EvaluationMarksAll
				TenderDetails td = (TenderDetails) objects[0];
				EvaluationMarksAll ema = (EvaluationMarksAll) objects[1];
				
				JSONObject tenderIDs = new JSONObject();
				
				tenderIDs.put("tenderId", td.getTrnderid());	
				tenderIDs.put("tenderName", td.getTendername());
				tenderIDs.put("tBidNumber", td.getBidno());
				
				ar.add(tenderIDs);
			}
		}
		data.put("data", ar);
		return data;
		//return tenderDetails;
	}  catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

@Override
public String financialResubmitCreation(JSONObject data, HttpServletRequest request) {
	String msg = null;
	try {
		
		//Optional<PoDetails> po = poDetailsRepo.findByTenderId(Long.valueOf(data.get("tenderId").toString()));
		
		
			
			//Long tenderId = po.get().getmTenderId();
			//Long SubCompId = Long.parseLong(po.get().getmSupplierCode());
			
			Long tenderId = Long.valueOf(data.get("tenderId").toString());
			Long SubCompId = Long.valueOf(data.get("supplierId").toString());
			
			Optional<FinacialResponsesVendor> financialRe = financialResponseRepo.findByTenderIDAndSubCompanyCode(tenderId,SubCompId);
			FinacialResponsesVendorOld financialResVenderOld = new FinacialResponsesVendorOld();
			
			
			financialResVenderOld.setAmountlkr(financialRe.get().getAmountlkr());
			financialResVenderOld.setAmountusd(financialRe.get().getAmountusd());
			financialResVenderOld.setDescription(financialRe.get().getDescription());
			financialResVenderOld.setExchangerate(financialRe.get().getExchangerate());
			financialResVenderOld.setFinancialDocument(financialRe.get().getFinancialDocument());
			//financialResVenderOld.setId(financialRe.get().getId());
			financialResVenderOld.setRFPID(financialRe.get().getRFPID());
			financialResVenderOld.setSubCompanyCode(financialRe.get().getSubCompanyCode());
			financialResVenderOld.setSubmitDateTime(financialRe.get().getSubmitDateTime());
			financialResVenderOld.setTenderID(financialRe.get().getTenderID());
			financialResVenderOld.setUserID(financialRe.get().getUserID());
			financialResVenderOld.setFinancialResponsesId(financialRe.get().getId());			
			financialResponseOldRepo.save(financialResVenderOld);
			
			//financialResVender.setAmountlkr(null);
			//financialResVender.setAmountusd(null);
			//financialResVender.setDescription(null);
			//financialResVender.setExchangerate(null);
			//financialResVender.setFinancialDocument(null);
			//FinacialResponsesVendor financialResVender = new FinacialResponsesVendor();
			financialRe.get().setStatus(Long.valueOf(26));
			financialResponseRepo.save(financialRe.get());
			
			//financialResponseRepo.deleteByTenderIDAndSubCompanyCode(tenderId,SubCompId);
			//financialResponseRepo.deleteById(financialRe.get().getId());
			
			List<FinancialValuesPerTender> financialPerTenders = financialValuesPerTenderRepo.findByTenderIdAndSupplierId(tenderId,SubCompId);
			
			for(FinancialValuesPerTender financialPerTender : financialPerTenders) {
				FinancialValuesPerTenderOld financialValuesPerTenderOld = new FinancialValuesPerTenderOld();
				
				financialValuesPerTenderOld.setAmount(financialPerTender.getAmount());
				financialValuesPerTenderOld.setCappex(financialPerTender.getCappex());
				financialValuesPerTenderOld.setComment(financialPerTender.getComment());
				financialValuesPerTenderOld.setCostDescription(financialPerTender.getCostDescription());
				financialValuesPerTenderOld.setCreatedDateTime(financialPerTender.getCreatedDateTime());
				financialValuesPerTenderOld.setCurrency(financialPerTender.getCurrency());
				//financialValuesPerTenderOld.setFinancialId(financialPerTender.getFinancialId());
				financialValuesPerTenderOld.setFinancialPerTenderParamID(financialPerTender.getFinancialPerTenderParamID());
				financialValuesPerTenderOld.setSupplierId(financialPerTender.getSupplierId());
				financialValuesPerTenderOld.setTenderId(financialPerTender.getTenderId());
				financialValuesPerTenderOld.setUserId(financialPerTender.getUserId());
				financialValuesPerTenderOld.setFinancialValPerTender(financialPerTender.getFinancialId());
				financialValuesPerTenderOldRepo.save(financialValuesPerTenderOld);
				financialValuesPerTenderRepo.deleteById(financialPerTender.getFinancialId());
			}
			//financialValuesPerTenderRepo.deleteByTenderIdAndSupplierId(tenderId,SubCompId);
			
			//SubCompanyCodeAndTenderID
		
		
		msg = "Success";
	} catch (Exception e) {
		e.printStackTrace();
		msg = null;
	}
	return msg;
}

@Override
public JSONObject getResubmitFinancialDetailsAllowOptionSupplier(Long tenderId) {
	LocalDateTime DateTime = LocalDateTime.now();
	JSONObject data = new JSONObject();
	try {
		List<Object[]> supplierDetails = tenderDetailsRepo.getResubmitFinancialDetailsAllowOptionSupplier(tenderId);
		JSONArray ar = new JSONArray();
		for (Object[] objects : supplierDetails) {
			if (objects[0] instanceof SubCompany && objects[1] instanceof EvaluationMarksAll) {
				//EvaluationMarksAll
				SubCompany sc = (SubCompany) objects[0];
				EvaluationMarksAll ema = (EvaluationMarksAll) objects[1];
				
				JSONObject supplier = new JSONObject();
				
				supplier.put("supplierId", sc.getSubCompanyID());	
				supplier.put("supplierName", sc.getScname());
				supplier.put("votes", ema.getVoteFromProcument());
				
				ar.add(supplier);
			}
		}
		data.put("data", ar);
		return data;
		//return tenderDetails;
	}  catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

@Override
public List<EligibleSubCategory> getSubCat(Long catId) {
	try {
		List<EligibleSubCategory> subCat = eligibleSubCategoryRepo.findByEligibleCategortID(catId);
		return subCat;
		
	}  catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

@Override
public JSONObject closedTenders() {
	LocalDateTime DateTime = LocalDateTime.now();
	JSONObject data = new JSONObject();
	try {
		List<Object[]> tenderDetails = tenderDetailsRepo.closedTenders(DateTime);
		JSONArray ar = new JSONArray();
		for (Object[] objects : tenderDetails) {
			if (objects[1] instanceof TenderDetails && objects[0] instanceof TenderSubmissions) {
				TenderSubmissions ts = (TenderSubmissions) objects[0];
				TenderDetails td = (TenderDetails) objects[1];
				
				JSONObject tenderIDs = new JSONObject();
				
				tenderIDs.put("tenderId", ts.getTenderNo());	
				tenderIDs.put("tenderName", td.getTendername());
				tenderIDs.put("tBidNumber", td.getBidno());
				
				ar.add(tenderIDs);
			}
		}
		data.put("data", ar);
		return data;
		//return tenderDetails;
	}  catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

@Override
public String additionalFileUploadInvitationForSupplier(JSONObject data, HttpServletRequest request) {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	User user = (User) authentication.getPrincipal();
	String userId = user.getUserid();
	
	try {
		
		Long tenderId = Long.valueOf(data.get("tenderID").toString());
		String noteAndFileName = data.get("noteAndFileNames").toString();
		
		List<TenderSubmissions> tsList = appzTenderSubmissionsRepo.findByTenderNoAndTenderResponse(tenderId,"8");
		Optional<TenderDetails> tenderDetails = tenderDetailsRepo.findById(tenderId);
		Optional<RFP> rfp = rfpRepo.findById(tenderDetails.get().getRfpId());
		
		for (TenderSubmissions ts : tsList) {
			
			AdditionalFileTenderForSupplier additionalFiles = new AdditionalFileTenderForSupplier();
			additionalFiles.setTenderId(tenderId);
			additionalFiles.setSupplierId(ts.getSupplierId());
			additionalFiles.setNoteAndFileName(noteAndFileName);
			additionalFiles.setFileUploadedStatus(Long.valueOf(23));
			additionalFiles.setStatus(Long.valueOf(3));
			additionalFiles.setCreatedUser(userId);
			additionalFiles.setCreatedDateTime(LocalDateTime.now());
			additionalFiles = additionalFileTenderForSupplierRepo.save(additionalFiles);
			
			Optional<SubCompany> sc = subCompanyRepo.findById(ts.getSupplierId());
			
			String subject = "Requesting for Additional Files";
			String Email = sc.get().getScemailadmin();
			System.out.println();
			
			String content = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Dear Sir / Madame\r\n"
					+ ",</span></p>  <p style=\"font-size: 14px; line-height: 140%;\"> </p> <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Requesting for Additional Files for Tender "+tenderDetails.get().getBidno()+"\r\n"
					+ "\r\n"
					+ "We would like to request following additional details.</span></p> <p style=\"font-size: 14px; line-height: 140%;\"> </p><p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Tender No: "+tenderDetails.get().getBidno()+"</span></p><br>"
					+"<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">RFP No: "+rfp.get().getRfpNo()+"\r\n"
					+ "</span></p> <p style=\"font-size: 14px; line-height: 140%;\"> </p>"
					+"<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">List of required documents.</span></p>"
					+"<p style=\"font-size: 14px; line-height: 140%;\"> </p><p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">"+noteAndFileName+"</span></p>"
					+"<p style=\"font-size: 14px; line-height: 140%;\"> </p>"
					+"<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Please use Additional file upload option to submit.</span></p>"
					+"<p style=\"font-size: 14px; line-height: 140%;\"> </p><p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Thank you,<br>Regards,<br>NDB</span></p>";
			CommonEmail comEmail = new CommonEmail("Requesting for Additional Files", content);
			
			System.out.println(comEmail.getHeadingName());
			System.out.println(comEmail.getContent());
			System.out.println("getContentOfEmail"+comEmail.getContentOfEmail());
			System.out.println("getHeadingOfEmail"+comEmail.getHeadingOfEmail());
			
			String body = comEmail.getFirstPart()+comEmail.getHeadingOfEmail()+comEmail.getSecondPart()+comEmail.getContentOfEmail()+comEmail.getThirdPart();
			
			
			 new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					List<String> to = new ArrayList<String>();
					to.add(Email);
					
					common.sendEMailHtml(to, subject, body);
				}
			}).start();
			 
			 
		}

		String msg = "00";
		return msg;
		
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

@Override
public JSONObject getSupplierAdditinalFileUpload(HttpServletRequest request) {
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	User user = (User) authentication.getPrincipal();
	String userId = user.getUserid();
	Long companyCode = Long.valueOf(user.getCompanyCode());	
	try {
		
		JSONObject data = new JSONObject();
		Page<Object[]> supplier_additional_upload = null;
		data.put("draw", request.getParameter("draw"));
		String sorting = "";
		
		switch (request.getParameter("order[0][column]")) {
		case "0":
			sorting = "td.bidno";
			break;
		case "1":
			sorting = "td.tendername";
			break;
		case "2":
			sorting = "rfp.rfpNo";
			break;
		case "3":
			sorting = "rfp.fileName";
			break;
		
		}
		
		if (request.getParameter("order[0][dir]").equals("asc")) {
			System.out.println("ASC ");
			supplier_additional_upload = tenderDetailsRepo.getSupplierAdditinalFileUpload(request.getParameter("search[value]"),companyCode,					
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.ASC, sorting)));
			
			System.out.println("end ");
		} else {
			System.out.println("DESC ");
			supplier_additional_upload = tenderDetailsRepo.getSupplierAdditinalFileUpload(request.getParameter("search[value]"),companyCode,						
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.DESC, sorting)));

		}
		
		JSONArray ar = new JSONArray();
		long count = supplier_additional_upload.getTotalElements();
		long n = 1;
		for (Object[] objects : supplier_additional_upload.getContent()) {
			
			if (objects[0] instanceof AdditionalFileTenderForSupplier && objects[1] 
					instanceof TenderDetails && objects[2] instanceof RFP) {
				
				JSONObject ob = new JSONObject();
				
				AdditionalFileTenderForSupplier taf = (AdditionalFileTenderForSupplier) objects[0];
				TenderDetails td = (TenderDetails) objects[1];
				RFP rfp = (RFP) objects[2];
				
				ob.put("index", n);
				ob.put("tenderNumber", td.getBidno());
				ob.put("tenderName", td.getTendername());
				ob.put("rfpNumber", rfp.getRfpNo());
				ob.put("rfpName",rfp.getFileName());
				ob.put("addFileId", taf.getTenderAdditionalFileUploadId());
				ob.put("fileUploadStatus", taf.getFileUploadedStatus());
				ob.put("fileForUpload", taf.getNoteAndFileName());
				
				ar.add(ob);
				n++;
			}
		}


		data.put("recordsTotal", count);
		data.put("recordsFiltered", count);
		data.put("data", ar);
		System.out.println("data ds--->" + data);
		return data;
		
		
	}catch(Exception e) {
		e.printStackTrace();
		return null;
	}
}

@Override
public String additinalFileUpload(JSONObject data, HttpServletRequest request) {
	String msg = null;
	try {
		
		Long addFileId = Long.valueOf(data.get("addFileId").toString());
		Optional<AdditionalFileTenderForSupplier> aftfs = additionalFileTenderForSupplierRepo.findById(addFileId);
		
		if (aftfs.isPresent()) {
			
			if(data.containsKey("upload_file")) {
				String headerData[]=data.get("upload_file").toString().split("base64,");
				String head=headerData[1].substring(0, (headerData[1].length()));
				String extention[]=data.get("upload_file").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				new DeEnCode().decodeMethod(tenderAdditinalSupplierPath,"additionalFile"+aftfs.get().getTenderAdditionalFileUploadId().toString(),"zip", head);
				aftfs.get().setZipFilePath(tenderAdditinalSupplierPath+"additionalFile"+aftfs.get().getTenderAdditionalFileUploadId().toString()+".zip");
				
				
				
			}else {
				System.out.println("No File------------------->");
			}
			
			aftfs.get().setFileUploadedStatus(Long.valueOf(24));
			additionalFileTenderForSupplierRepo.save(aftfs.get());
			
		}
		
		
		msg = "Success";
		return msg;
	} catch (Exception e) {
		e.printStackTrace();
		msg = "Error";
		return msg;
	}
	
}

@Override
public Boolean getAdditionalFileExists(String enteredValue) {
	Long id = Long.valueOf(enteredValue);
	return additionalFileTenderForSupplierRepo.existsByTenderId(id);
}

@Override
public JSONObject getSupplierAdditinalFileDownload(HttpServletRequest request, Long tenderId) {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	User user = (User) authentication.getPrincipal();
	String userId = user.getUserid();
	Long companyCode = Long.valueOf(user.getCompanyCode());	
	try {
		
		JSONObject data = new JSONObject();
		Page<Object[]> supplier_additional_download = null;
		data.put("draw", request.getParameter("draw"));
		String sorting = "";
		
		switch (request.getParameter("order[0][column]")) {
		case "1":
			sorting = "sc.scname";
			break;
		
		
		}
		
		if (request.getParameter("order[0][dir]").equals("asc")) {
			System.out.println("ASC ");
			supplier_additional_download = tenderDetailsRepo.getSupplierAdditinalFileDownload(request.getParameter("search[value]"),tenderId,					
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.ASC, sorting)));
			
			System.out.println("end ");
		} else {
			System.out.println("DESC ");
			supplier_additional_download = tenderDetailsRepo.getSupplierAdditinalFileDownload(request.getParameter("search[value]"),tenderId,						
					PageRequest.of(
							Integer.parseInt(request.getParameter("start"))
									/ Integer.parseInt(request.getParameter("length")),
							Integer.parseInt(request.getParameter("length")),
							Sort.by(Sort.Direction.DESC, sorting)));

		}
		
		JSONArray ar = new JSONArray();
		long count = supplier_additional_download.getTotalElements();
		long n = 1;
		for (Object[] objects : supplier_additional_download.getContent()) {
			
			if (objects[0] instanceof AdditionalFileTenderForSupplier && objects[1] 
					instanceof SubCompany) {
				
				JSONObject ob = new JSONObject();
				
				AdditionalFileTenderForSupplier taf = (AdditionalFileTenderForSupplier) objects[0];
				SubCompany sc = (SubCompany) objects[1];
				
				ob.put("index", n);
				ob.put("companyName", sc.getScname());
				ob.put("additionalTenderId", taf.getTenderAdditionalFileUploadId());
				ob.put("fileUploadStatus", taf.getFileUploadedStatus());
				
				ar.add(ob);
				n++;
			}
		}


		data.put("recordsTotal", count);
		data.put("recordsFiltered", count);
		data.put("data", ar);
		System.out.println("data ds--->" + data);
		return data;
		
		
	}catch(Exception e) {
		e.printStackTrace();
		return null;
	}
}







/*@Scheduled(cron = "${cron.expression}")
public void closeTenderAutomatically() {
	try {
		
		List<TenderDetails> TenderDetail = tenderDetailsRepo.findAll();
		
		for (TenderDetails td : TenderDetail) {	
			
			System.out.println(td.getClosingDateTime());
			LocalDateTime nowDateTime = LocalDateTime.now();
			
			if(td.getClosingDateTime().isBefore(nowDateTime)) {
				td.setStatus("15");
				tenderDetailsRepo.saveAndFlush(td);
				
			}
			 
		}
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}*/





	/*
	 * @Override public List<TenderDetails>
	 * getTenderDetailsForView(HttpServletRequest request, Long tenderID) { try {
	 * System.out.println("Tender ID"+tenderID); List<TenderDetails> tenderDetails =
	 * tenderDetailsRepo.getTenderDetailsForView(tenderID); return tenderDetails; }
	 * catch (Exception e) { e.printStackTrace(); return null; }
	 * 
	 * }
	 */
			
		
}
