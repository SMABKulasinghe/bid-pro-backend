package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URLConnection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.AppzTenderSubmissionsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.CompanySupplierMappingRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.PoDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.PoRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ReIssuePoRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SubCompanyRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.TenderDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.TermsAndConditionsFromPoRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.TermsAndConditionsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.TermsAndConditionsTransRepo;
import lk.supplierUMS.SupplierUMS_REST.comman.Common;
import lk.supplierUMS.SupplierUMS_REST.comman.DeEnCode;
import lk.supplierUMS.SupplierUMS_REST.entity.BoardPaperUpload;
import lk.supplierUMS.SupplierUMS_REST.entity.CompanySupplierMapping;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.EPoHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationMarksAll;
import lk.supplierUMS.SupplierUMS_REST.entity.FinacialResponsesVendor;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.PoDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.RFP;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.Re_issuePO;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.TermsAndConditions;
import lk.supplierUMS.SupplierUMS_REST.entity.TermsAndConditionsFromPo;
import lk.supplierUMS.SupplierUMS_REST.entity.TermsAndConditionsTrans;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.service.PoService;
import org.apache.commons.io.FileUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.StringReader;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
@EnableScheduling
public class PoServiceImpl implements PoService {

	@Autowired
	PoRepo poRepo;

	@Autowired
	PoDetailsRepo poDetailsRepo;
	
	@Autowired
	ReIssuePoRepo reIssuePoRepo;

	@Autowired
	CompanySupplierMappingRepo comSupMapping;

	@Autowired
	TenderDetailsRepo tenderDetailsRepo;

	@Autowired
	AppzTenderSubmissionsRepo appzTenderSubmissionsRepo;
	
	@Autowired
	TermsAndConditionsRepo termsAndConditionsRepo;
	
	@Autowired
	TermsAndConditionsTransRepo termsAndConditionsTransRepo;
	
	@Autowired
	TermsAndConditionsFromPoRepo termsAndConditionsFromPoRepo;

	@Autowired
	Common common;

	@Autowired
	SubCompanyRepo subCompanyRepo;

	@Value("${company.data.path}")
	String poDataPath;
	
	@Value("${po.email.doc.path}")
	String poEmailDocPath;

	@Override
	public String addPo(JSONObject data, HttpServletRequest request) {
		String msg = null;
		
		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		  User user = (User) authentication.getPrincipal(); 
		  Long userCompanyID = new Long(user.getCompanyCode());
		 
		  
		  
		PoDetails poDetails = new PoDetails();

		try {

			String status = "2";
			if (data.containsKey("m_batchfile")) {
				String headerData[] = data.get("m_batchfile").toString().split("base64,");
				String extention[] = data.get("m_batchfile").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->" + exten);

				String head = headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(poDataPath, data.get("supplierId").toString() + "-"
						+ data.get("tenderId").toString() + "-" + data.get("tenderId").toString(), exten, head);

				poDetails.setmBatchFile(poDataPath + data.get("supplierId").toString() + "-"
						+ data.get("tenderId").toString() + "-" + data.get("tenderId").toString() + "." + exten);
			}

			Long qty = Long.valueOf(data.get("qty").toString());
			Long unitPriceLKR = Long.valueOf(data.get("unitPriceLkr").toString());
			Long tot = qty * unitPriceLKR;
			
			poDetails.setmQty(Long.valueOf(data.get("qty").toString()));
			poDetails.setmSupplierCode(data.get("supplierId").toString());
			poDetails.setmTenderId(Long.valueOf(data.get("tenderId").toString()));
			poDetails.setmRfpId(Long.valueOf(data.get("rfpId").toString()));
			poDetails.setSubmissionId(Long.valueOf(data.get("TenderSubmissionId").toString()));
			poDetails.setmUnitPrice(Long.valueOf(data.get("unitPriceLkr").toString()));
			poDetails.setmTotalCost(tot);
			
			poDetails.setmStatus(status);
			poDetails = poDetailsRepo.save(poDetails);
			poDetails.setmPoNo("PO" + poDetails.getmPoId());
			PoDetails po = poDetailsRepo.save(poDetails);
			
			Optional<TenderDetails> td = tenderDetailsRepo.findById(po.getmTenderId());
			Optional<SubCompany> sc = subCompanyRepo.findById(Long.valueOf(po.getmSupplierCode()));
			System.out.println("json - "+data.keySet().toString());
			
			 Set<String> keySet = data.keySet();
			 
			List<Object[]> trmsNCTrns = termsAndConditionsTransRepo.getTermsAndConditionsForSave(Long.valueOf(data.get("tenderId").toString()));
			for (Object[] objects : trmsNCTrns) {
				
				if (objects[0] instanceof TermsAndConditionsTrans && objects[1] instanceof TermsAndConditions) {

					TermsAndConditionsTrans tnct = (TermsAndConditionsTrans) objects[0];
					TermsAndConditions tnc = (TermsAndConditions) objects[1];
					
					for (String string : keySet) {
						
						 if(string.equals(tnc.getTermsOrConditionsWithoutSpace())) {
							 
							 System.out.println("Eq- "+tnc.getTermsConditions());
							TermsAndConditionsFromPo trmConFromPo = new TermsAndConditionsFromPo();
							
							String attributeId = tnc.getTermsOrConditionsWithoutSpace();
							
							trmConFromPo.setTermsConditionsVal(data.get(attributeId).toString());	
							trmConFromPo.setTermsConditionsIdString(tnc.getTermsOrConditionsWithoutSpace());
							trmConFromPo.setTermsConditionsId(tnc.getTermsAndConditionsId());
							trmConFromPo.setTermsAndConditionsTransId(tnct.getTermsAndConditionsTransId());
							trmConFromPo.setCreatedDateTime(LocalDateTime.now());
							trmConFromPo.setCreatedUser(user.getUserid());
							trmConFromPo.setPoId(Long.valueOf(po.getmPoId()));
							trmConFromPo.setTenderId(Long.valueOf(po.getmTenderId()));
							trmConFromPo.setRfpId(Long.valueOf(po.getmRfpId()));
							trmConFromPo.setStatus(Long.valueOf(3));
							termsAndConditionsFromPoRepo.save(trmConFromPo);
							 
						 }
						
					}
					
				}
			}
			
			String k = "<html>\r\n"
					+ "  <body>\r\n"
					+ "    <div style=\"text-align:center;\">\r\n"
					
					+ "      	<div style=\"color:white; background-color: red;\">PO Details</div>\r\n"
					+ "    </div>\r\n"
					+ "        <form>\r\n"
					+ "          <p><label for=\"fname\">Date :</label>\r\n"
					+ "         <label for=\"fname\">"+LocalDate.now()+" </label></p>\r\n"
					
					+ "          <p><label for=\"lname\">Purchase Order No:</label>\r\n"
					+ "          <label for=\"fname\">"+po.getmPoNo()+" </label></p>\r\n"
					
					+ "           <p><label for=\"lname\">Supplier Name:</label>\r\n"
					+ "          <label for=\"fname\">"+sc.get().getScname()+" </label></p>\r\n"
					
					+ "           <p><label for=\"lname\">Supplier Address:</label>\r\n"
					+ "          <label for=\"fname\">"+sc.get().getScaddress1()+","+sc.get().getScaddress2()+","+sc.get().getScaddress3()+" </label></p>\r\n"
					
					+ "           <p><label for=\"lname\">Tender Name:</label>\r\n"
					+ "          <label for=\"fname\">"+td.get().getTendername()+" </label></p>\r\n"
					+ "        </form>\r\n"
					+ "    <p>\r\n"
					+ "      With reference Tender No : "+td.get().getBidno()+", please make arrangements to supply us with the 				following:\r\n"
					+ "    </p>\r\n"
					+ "    <div style=\"text-align:center;\">\r\n"
					+ "    <table style=\"border: px solid;\">\r\n"
					+ "      <tr style=\"border: 1px solid;\">\r\n"
					+ "        <th style=\"border: 2px solid;\">No</th>\r\n"
					+ "        <th style=\"border: 2px solid;\">Description</th>\r\n"
					+ "        <th style=\"border: 2px solid;\">Quantity</th>\r\n"
					+ "        <th style=\"border: 2px solid;\">Unit Price</th>\r\n"
					+ "        <th style=\"border: 2px solid;\">Total Cost</th>\r\n"
					+ "      </tr>\r\n"
					+ "      <tr style=\"border: 1px solid;\">\r\n"
					+ "        <td style=\"border: 1px solid;\">1</td>\r\n"
					+ "        <td style=\"border: 1px solid;\">"+td.get().getTenderdescription()+"</td>\r\n"
					+ "        <td style=\"border: 1px solid;\">"+po.getmQty()+"</td>\r\n"
					+ "        <td style=\"border: 1px solid;\">"+po.getmUnitPrice()+"</td>\r\n"
					+ "        <td style=\"border: 1px solid;\">"+po.getmTotalCost()+"</td>\r\n"
					+ "      </tr>\r\n"
					+ "      <tr style=\"border: 1px solid;\">\r\n"
					+ "        <td style=\"border: 1px solid;\"> </td>\r\n"
					+ "        <td style=\"border: 1px solid; \" colspan=\"3\">Total Cost in LKR (Excluding VAT) </td>\r\n"
					+ "        <td style=\"border: 1px solid;\"> "+po.getmTotalCost()+" </td>\r\n"
					+ "      </tr>\r\n"
					+ "    </table>\r\n"
					+ "    </div>\r\n"
					+ "    <p>\r\n"
					+ "      Terms & Conditions:\r\n"
					+ "      <ul>\r\n"
					+ "        <li>Delivery - "+po.getmDelivery()+"</li>\r\n"
					+ "        <li>Warranty -"+po.getmWarranty()+"</li>\r\n"
					+ "        <li>Payment Term -"+po.getmPaymentTerms()+"</li>\r\n"
					+ "        <li>Bank’s VAT No should be mentioned in the invoice as "+po.getBankVatNo()+".</li>\r\n"
					+ "        <li>Any delay in delivery will accrue a penalty of "+po.getPanelty()+"% of the designated purchase price (Excluding VAT) referred to above for each banking business day </li>\r\n"
					+ "      </ul>\r\n"
					+ "    </p>\r\n"
					+ "  	<p>\r\n"
					+ "  		Yours faithfully,\r\n"
					
					+ "  	</p>\r\n"
					+ "  	\r\n"
					+ "  	<p>\r\n"
						
					+ "      	National Development Bank PLC.\r\n"
					+ "  	</p>\r\n"
					+ "  	\r\n"
					+ "  </body>\r\n"
					+ "</html>";
			
			FileOutputStream file = new FileOutputStream(new File(poEmailDocPath+po.getmPoId()+".pdf"));
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, file);
			document.open();
			ByteArrayInputStream is = new ByteArrayInputStream(k.getBytes());

			XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
			document.close();
			file.close();
			System.out.println("done-->>email");
			 
			
			Optional<PoDetails> poo = poDetailsRepo.findById(po.getmPoId());
			if(poo.isPresent()) {
				PoDetails PoDetail = poo.get();
				PoDetail.setmPoEmailCopy(poEmailDocPath+po.getmPoId()+".pdf");
				poDetailsRepo.save(PoDetail);
			}
			// Optional<TenderSubmissions> ts =
			// appzTenderSubmissionsRepo.findByTenderNoAndSupplierId(Long.valueOf(data.get("tenderId").toString()),Long.valueOf(data.get("supplierId").toString()));
			Optional<TenderSubmissions> ts = appzTenderSubmissionsRepo.findByTenderNoAndSupplierId(
					Long.valueOf(data.get("tenderId").toString()), Long.valueOf(data.get("supplierId").toString()));
			if (ts.isPresent()) {
				TenderSubmissions tenderSubmissions = ts.get();
				tenderSubmissions.setTenderEligibility("16");
				appzTenderSubmissionsRepo.save(tenderSubmissions);
			}

			msg = "Success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// return poRepo.save(ePoHeader).getmCompanyCode().toString();
		return msg;
	}

	@Override
	public JSONObject getPoAuthorization(HttpServletRequest request) {
		try {
			System.out.println("Request " + request);
			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();

			Page<Object[]> po_authentication_details = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "mPoNo";
				break;
			case "1":
				sorting = "mPoNo";
				break;
			case "2":
				sorting = "mComment";
				break;
			case "3":
				sorting = "mLocationCode";
				break;
			case "4":
				sorting = "mLocationName";
				break;
			case "5":
				sorting = "mOrderGrossValue";
				break;
			case "6":
				sorting = "mPoDate";
				break;
			case "7":
				sorting = "mTotal";
				break;
			case "8":
				sorting = "mTotalGrossAmount";
				break;
			}

//			cod.createdAt LIKE %?1% OR cod.expiryDate LIKE %?1% OR cod.modifiedBy LIKE %?1% OR cod.paymentType LIKE %?1% OR cod.RenewalDatePeriod
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				po_authentication_details = poRepo.getPoAuthorizations(request.getParameter("search[value]"),
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));

				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				po_authentication_details = poRepo.getPoAuthorizations(request.getParameter("search[value]"),
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
//				contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			long count = po_authentication_details.getTotalElements();
			System.out.println("get cn--->" + po_authentication_details.getContent().size());
			for (Object[] objects : po_authentication_details.getContent()) {

				if (objects[0] instanceof PoDetails && objects[1] instanceof TenderDetails && objects[2] instanceof User
						&& objects[3] instanceof TenderSubmissions && objects[4] instanceof RFP
						&& objects[5] instanceof SubCompany) {

					JSONObject ob = new JSONObject();
					PoDetails poDetails = (PoDetails) objects[0];
					TenderDetails tenderDetails = (TenderDetails) objects[1];
					User userDetails = (User) objects[2];
					TenderSubmissions tenderSubmissions = (TenderSubmissions) objects[3];
					RFP rfp = (RFP) objects[4];
					SubCompany subCompany = (SubCompany) objects[5];
//					
					ob.put("SupplierName", subCompany.getScname());
					ob.put("TenderName", tenderDetails.getTendername());
					ob.put("RfpFileName", rfp.getFileName());
					ob.put("RfpId", rfp.getRfpID());
					ob.put("BatchFile", poDetails.getmBatchFile());
					ob.put("TenderSubmissionId", tenderSubmissions.getSubmission_id());
					ob.put("CompanyCode", userDetails.getCompanyCode());
					ob.put("Qty", poDetails.getmQty());
					ob.put("TenderEligibility", tenderSubmissions.getTenderEligibility());
					ob.put("TenderId", tenderDetails.getTrnderid());
					ob.put("PoId", poDetails.getmPoId());
					ob.put("FilePath", poDetails.getmPoEmailCopy());
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
		/* return null; */
		/* return null; */
	}

	@Override
	public JSONObject PoAuthorization(long id, String approve) {
		JSONObject ob = new JSONObject();
		try {
			String statusApproved = "3";
			String statusRejected = "1";

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();

			// Optional<EPoHeader> po = poRepo.findById(id);
			// if (po.isPresent()) {
			if (approve.equals("approve")) {

				// po.get().setmLoggedUserID(user.getUserid());
				// po.get().setmStatus(statusApproved);
				// poRepo.save(po.get());

			} else {
				// po.get().setmLoggedUserID(user.getUserid());
				// po.get().setmStatus(statusRejected);
				// poRepo.save(po.get());
			}
			// }

		} catch (Exception e) {
			e.printStackTrace();
			// return ob;
		}
		return ob;

	}

	@Override
	public JSONObject getPoDetails(HttpServletRequest request) {
		try {

			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();

			Page<Object[]> po_authentication_details = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "mPoNo";
				break;
			case "1":
				sorting = "contractId";
				break;
			case "2":
				sorting = "contractAmount";
				break;
			case "3":
				sorting = "createdAt";
				break;
			case "4":
				sorting = "expiryDate";
				break;
			case "5":
				sorting = "amcusd";
				break;
			case "6":
				sorting = "paymentType";
				break;
			case "7":
				sorting = "RenewalDatePeriod";
				break;
			}

//			cod.createdAt LIKE %?1% OR cod.expiryDate LIKE %?1% OR cod.modifiedBy LIKE %?1% OR cod.paymentType LIKE %?1% OR cod.RenewalDatePeriod
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				po_authentication_details = poRepo.getPoDetails(request.getParameter("search[value]"), PageRequest.of(
						Integer.parseInt(request.getParameter("start"))
								/ Integer.parseInt(request.getParameter("length")),
						Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));

				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				po_authentication_details = poRepo.getPoDetails(request.getParameter("search[value]"), PageRequest.of(
						Integer.parseInt(request.getParameter("start"))
								/ Integer.parseInt(request.getParameter("length")),
						Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
//				contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			long count = po_authentication_details.getTotalElements();
			System.out.println("get cn--->" + po_authentication_details.getContent().size());
			for (Object[] objects : po_authentication_details.getContent()) {

				if (objects[0] instanceof EPoHeader && objects[1] instanceof SubCompany) {

					JSONObject ob = new JSONObject();
					EPoHeader epoheader = (EPoHeader) objects[0];
					SubCompany subCompany = (SubCompany) objects[1];
//					
					ob.put("supplierName", subCompany.getScname());
					ob.put("poNumber", epoheader.getmPoNo());
					ob.put("comment", epoheader.getmComment());
					ob.put("locationCode", epoheader.getmLocationCode());
					ob.put("locationName", epoheader.getmLocationName());
					ob.put("grossValue", epoheader.getmOrderGrossValue());
					ob.put("poDate", epoheader.getmPoDate());
					ob.put("totalVal", epoheader.getmTotal());
					ob.put("totalGrossVal", epoheader.getmTotalGrossAmount());

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
		/* return null; */
		/* return null; */

	}

	@Override
	public JSONObject getTenderIds() {
		JSONObject data = new JSONObject();
		try {
			List<Object[]> tenderDetails = poRepo.getTenderDetails();
			JSONArray ar = new JSONArray();
			for (Object[] objects : tenderDetails) {
				if (objects[0] instanceof TenderDetails && objects[1] instanceof TenderSubmissions
						&& objects[2] instanceof FinacialResponsesVendor) {

					TenderDetails td = (TenderDetails) objects[0];
					TenderSubmissions ts = (TenderSubmissions) objects[1];
					FinacialResponsesVendor fr = (FinacialResponsesVendor) objects[2];

					JSONObject tenderIDs = new JSONObject();

					tenderIDs.put("tenderId", td.getTrnderid());
					tenderIDs.put("tenderName", td.getTendername());
					tenderIDs.put("tBidNumber", td.getBidno());
					tenderIDs.put("closingDateTime", td.getClosingDateTime());
					tenderIDs.put("amountLkr", fr.getAmountlkr());
					tenderIDs.put("amountUsd", fr.getAmountusd());

					ar.add(tenderIDs);
				}
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
	public JSONObject getTenderDetailsForPo(Long id) {
		// Authentication authentication =
		// SecurityContextHolder.getContext().getAuthentication();
		// User user = (User) authentication.getPrincipal();
		// Long userCompanyID = new Long(user.getCompanyCode());

		JSONObject tenderDetailsForPo = new JSONObject();
		try {

			List<Object[]> tds = poRepo.getTenderDetailsForPo(id);

			for (Object[] objects : tds) {
				if (objects[0] instanceof TenderDetails && objects[1] instanceof RFP
						&& objects[2] instanceof EligibleCategory && objects[3] instanceof TenderSubmissions
						&& objects[4] instanceof SubCompany && objects[5] instanceof FinacialResponsesVendor
						&& objects[6] instanceof BoardPaperUpload) {
					TenderDetails td = (TenderDetails) objects[0];
					RFP rfp = (RFP) objects[1];
					EligibleCategory ec = (EligibleCategory) objects[2];
					TenderSubmissions ts = (TenderSubmissions) objects[3];
					SubCompany sc = (SubCompany) objects[4];
					FinacialResponsesVendor fr = (FinacialResponsesVendor) objects[5];
					BoardPaperUpload bpu = (BoardPaperUpload) objects[6];

					tenderDetailsForPo.put("TenderName", td.getTendername());
					tenderDetailsForPo.put("Category", ec.getEligibleCategortName());
					tenderDetailsForPo.put("RfpId", td.getRfpId());
					tenderDetailsForPo.put("RfpFileName", rfp.getFileName());
					tenderDetailsForPo.put("VenderId", ts.getVendorId());
					tenderDetailsForPo.put("TenderId", td.getTrnderid());
					tenderDetailsForPo.put("TenderSubmissionId", ts.getSubmission_id());
					tenderDetailsForPo.put("SupplierName", sc.getScname());

					tenderDetailsForPo.put("amountLkr", fr.getAmountlkr());
					tenderDetailsForPo.put("amountUsd", fr.getAmountusd());
					
					tenderDetailsForPo.put("boardPaper", bpu.getBoardPaperFileName());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return tenderDetailsForPo;
	}

	@Override
	public JSONObject sendEmailAndAuth(JSONObject data) {
		JSONObject returnData = new JSONObject();
		Long tenderSubmissionId = Long.valueOf(data.get("tenderSubmissionId").toString());
		Long companyCode = Long.valueOf(data.get("companyCode").toString());
		Long tenderId = Long.valueOf(data.get("tenderId").toString());
		Long qty = Long.valueOf(data.get("qty").toString());
		Long poId = Long.valueOf(data.get("poId").toString());

		Optional<SubCompany> comDetails = subCompanyRepo.getCompanyDetails(companyCode);
		Optional<TenderDetails> tenderDetailsForMail = tenderDetailsRepo.findById(tenderId);
		Optional<PoDetails> poDetailsForMail = poDetailsRepo.findById(poId);

		String subject = "Issue PO - " + poDetailsForMail.get().getmPoNo();
		// tender id
		String firstPart = "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\r\n"
				+ "<head>\r\n" + "<!--[if gte mso 9]>\r\n" + "<xml>\r\n" + "  <o:OfficeDocumentSettings>\r\n"
				+ "    <o:AllowPNG/>\r\n" + "    <o:PixelsPerInch>96</o:PixelsPerInch>\r\n"
				+ "  </o:OfficeDocumentSettings>\r\n" + "</xml>\r\n" + "<![endif]-->\r\n"
				+ "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
				+ "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "  <meta name=\"x-apple-disable-message-reformatting\">\r\n"
				+ "  <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\r\n"
				+ "  <title></title>\r\n" + "  \r\n" + "    <style type=\"text/css\">\r\n"
				+ "      @media only screen and (min-width: 620px) {\r\n" + "  .u-row {\r\n"
				+ "    width: 600px !important;\r\n" + "  }\r\n" + "  .u-row .u-col {\r\n"
				+ "    vertical-align: top;\r\n" + "  }\r\n" + "\r\n" + "  .u-row .u-col-50 {\r\n"
				+ "    width: 300px !important;\r\n" + "  }\r\n" + "\r\n" + "  .u-row .u-col-100 {\r\n"
				+ "    width: 600px !important;\r\n" + "  }\r\n" + "\r\n" + "}\r\n" + "\r\n"
				+ "@media (max-width: 620px) {\r\n" + "  .u-row-container {\r\n" + "    max-width: 100% !important;\r\n"
				+ "    padding-left: 0px !important;\r\n" + "    padding-right: 0px !important;\r\n" + "  }\r\n"
				+ "  .u-row .u-col {\r\n" + "    min-width: 320px !important;\r\n"
				+ "    max-width: 100% !important;\r\n" + "    display: block !important;\r\n" + "  }\r\n"
				+ "  .u-row {\r\n" + "    width: calc(100% - 40px) !important;\r\n" + "  }\r\n" + "  .u-col {\r\n"
				+ "    width: 100% !important;\r\n" + "  }\r\n" + "  .u-col > div {\r\n" + "    margin: 0 auto;\r\n"
				+ "  }\r\n" + "}\r\n" + "body {\r\n" + "  margin: 0;\r\n" + "  padding: 0;\r\n" + "}\r\n" + "\r\n"
				+ "table,\r\n" + "tr,\r\n" + "td {\r\n" + "  vertical-align: top;\r\n"
				+ "  border-collapse: collapse;\r\n" + "}\r\n" + "\r\n" + "p {\r\n" + "  margin: 0;\r\n" + "}\r\n"
				+ "\r\n" + ".ie-container table,\r\n" + ".mso-container table {\r\n" + "  table-layout: fixed;\r\n"
				+ "}\r\n" + "\r\n" + "* {\r\n" + "  line-height: inherit;\r\n" + "}\r\n" + "\r\n"
				+ "a[x-apple-data-detectors='true'] {\r\n" + "  color: inherit !important;\r\n"
				+ "  text-decoration: none !important;\r\n" + "}\r\n" + "\r\n"
				+ "table, td { color: #000000; } </style>\r\n" + "  \r\n" + "  \r\n" + "\r\n"
				+ "<!--[if !mso]><!--><link href=\"https://fonts.googleapis.com/css?family=Lato:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\r\n"
				+ "\r\n" + "</head>\r\n" + "\r\n"
				+ "<body class=\"clean-body u_body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f9f9f9;color: #000000\">\r\n"
				+ "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\r\n"
				+ "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\r\n"
				+ "  <table style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f9f9f9;width:100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
				+ "  <tbody>\r\n" + "  <tr style=\"vertical-align: top\">\r\n"
				+ "    <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n"
				+ "    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f9f9f9;\"><![endif]-->\r\n"
				+ "    \r\n" + "\r\n"
				+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: #f9f9f9\">\r\n"
				+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #f9f9f9;\">\r\n"
				+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
				+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: #f9f9f9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #f9f9f9;\"><![endif]-->\r\n"
				+ "      \r\n"
				+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
				+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
				+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
				+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
				+ "  \r\n"
				+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "  <tbody>\r\n" + "    <tr>\r\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:15px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "        \r\n"
				+ "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #f9f9f9;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
				+ "    <tbody>\r\n" + "      <tr style=\"vertical-align: top\">\r\n"
				+ "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
				+ "          <span>&#160;</span>\r\n" + "        </td>\r\n" + "      </tr>\r\n" + "    </tbody>\r\n"
				+ "  </table>\r\n" + "\r\n" + "      </td>\r\n" + "    </tr>\r\n" + "  </tbody>\r\n" + "</table>\r\n"
				+ "\r\n" + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + "  </div>\r\n" + "</div>\r\n"
				+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
				+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + "    </div>\r\n"
				+ "  </div>\r\n" + "</div>\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
				+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n"
				+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
				+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n"
				+ "      \r\n"
				+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
				+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
				+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
				+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
				+ "  \r\n"
				+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "  <tbody>\r\n" + "    <tr>\r\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:25px 62px 25px 10px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "        \r\n" + "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n"
				+ "  <tr>\r\n" + "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n"
				+ "      \r\n"
				+ "      <img align=\"center\" border=\"0\" src=\"https://www.ndbbank.com/images/logo.svg\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 60%;max-width: 316.8px;\" width=\"316.8\"/>\r\n"
				+ "      \r\n" + "    </td>\r\n" + "  </tr>\r\n" + "</table>\r\n" + "\r\n" + "      </td>\r\n"
				+ "    </tr>\r\n" + "  </tbody>\r\n" + "</table>\r\n" + "\r\n"
				+ "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + "  </div>\r\n" + "</div>\r\n"
				+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
				+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + "    </div>\r\n"
				+ "  </div>\r\n" + "</div>\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
				+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #161a39;\">\r\n"
				+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
				+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #161a39;\"><![endif]-->\r\n"
				+ "      \r\n"
				+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
				+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
				+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
				+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
				+ "  \r\n"
				+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "  <tbody>\r\n" + "    <tr>\r\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "        \r\n" + "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
				+ "    <p style=\"font-size: 14px; line-height: 140%; text-align: center;\"> </p>\r\n"
				+ "<p style=\"font-size: 14px; line-height: 140%; text-align: center;\"><span style=\"font-size: 28px; line-height: 39.2px; color: #ffffff; font-family: Lato, sans-serif;\">PO Details</span></p>\r\n"
				+ "  </div>\r\n" + "\r\n" + "      </td>\r\n" + "    </tr>\r\n" + "  </tbody>\r\n" + "</table>\r\n"
				+ "\r\n" + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + "  </div>\r\n" + "</div>\r\n"
				+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
				+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + "    </div>\r\n"
				+ "  </div>\r\n" + "</div>\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
				+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n"
				+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
				+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n"
				+ "      \r\n"
				+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
				+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
				+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
				+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
				+ "  \r\n"
				+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "  <tbody>\r\n" + "    <tr>\r\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 40px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "        \r\n" + "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">";

		String secondPart = "</div>\r\n" + "\r\n" + "      </td>\r\n" + "    </tr>\r\n" + "  </tbody>\r\n"
				+ "</table>\r\n" + "\r\n" + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + "  </div>\r\n"
				+ "</div>\r\n" + "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
				+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + "    </div>\r\n"
				+ "  </div>\r\n" + "</div>\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
				+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #18163a;\">\r\n"
				+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
				+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #18163a;\"><![endif]-->\r\n"
				+ "      \r\n"
				+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 20px 20px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
				+ "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n"
				+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
				+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 20px 20px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
				+ "  \r\n"
				+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "  <tbody>\r\n" + "    <tr>\r\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "        \r\n" + "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
				+ "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\">National Development Bank PLC </span></p>\r\n"
				+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\">40, Nawam Mawatha, Colombo 2, Sri Lanka</span></p>\r\n"
				+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\">T: +94 11 244 8448 | Ext. 31152</span></p>\r\n"
				+ "  </div>\r\n" + "\r\n" + "      </td>\r\n" + "    </tr>\r\n" + "  </tbody>\r\n" + "</table>\r\n"
				+ "\r\n" + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + "  </div>\r\n" + "</div>\r\n"
				+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
				+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px 0px 0px 20px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
				+ "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n"
				+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
				+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px 0px 0px 20px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
				+ "  \r\n"
				+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "  <tbody>\r\n" + "    <tr>\r\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:5px 10px 10px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "        \r\n" + "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
				+ "    <p style=\"line-height: 140%; font-size: 14px;\"> </p>\r\n"
				+ "<p style=\"line-height: 140%; font-size: 14px;\"> </p>\r\n"
				+ "<p style=\"line-height: 140%; font-size: 14px;\"> </p>\r\n"
				+ "<p style=\"line-height: 140%; font-size: 14px;\"> </p>\r\n"
				+ "<p style=\"line-height: 140%; font-size: 14px;\"><span style=\"font-size: 14px; line-height: 19.6px;\"><span style=\"color: #ecf0f1; font-size: 14px; line-height: 19.6px;\"><span style=\"line-height: 19.6px; font-size: 14px;\">Company ©  All Rights Reserved</span></span></span></p>\r\n"
				+ "  </div>\r\n" + "\r\n" + "      </td>\r\n" + "    </tr>\r\n" + "  </tbody>\r\n" + "</table>\r\n"
				+ "\r\n" + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + "  </div>\r\n" + "</div>\r\n"
				+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
				+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + "    </div>\r\n"
				+ "  </div>\r\n" + "</div>\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: #f9f9f9\">\r\n"
				+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #1c103b;\">\r\n"
				+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
				+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: #f9f9f9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #1c103b;\"><![endif]-->\r\n"
				+ "      \r\n"
				+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
				+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
				+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
				+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
				+ "  \r\n"
				+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "  <tbody>\r\n" + "    <tr>\r\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:15px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "        \r\n"
				+ "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #1c103b;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
				+ "    <tbody>\r\n" + "      <tr style=\"vertical-align: top\">\r\n"
				+ "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
				+ "          <span>&#160;</span>\r\n" + "        </td>\r\n" + "      </tr>\r\n" + "    </tbody>\r\n"
				+ "  </table>\r\n" + "\r\n" + "      </td>\r\n" + "    </tr>\r\n" + "  </tbody>\r\n" + "</table>\r\n"
				+ "\r\n" + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + "  </div>\r\n" + "</div>\r\n"
				+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
				+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + "    </div>\r\n"
				+ "  </div>\r\n" + "</div>\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
				+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #f9f9f9;\">\r\n"
				+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
				+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #f9f9f9;\"><![endif]-->\r\n"
				+ "      \r\n"
				+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
				+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
				+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
				+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
				+ "  \r\n"
				+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "  <tbody>\r\n" + "    <tr>\r\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 40px 30px 20px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "        \r\n" + "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
				+ "    \r\n" + "  </div>\r\n" + "\r\n" + "      </td>\r\n" + "    </tr>\r\n" + "  </tbody>\r\n"
				+ "</table>\r\n" + "\r\n" + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + "  </div>\r\n"
				+ "</div>\r\n" + "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
				+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + "    </div>\r\n"
				+ "  </div>\r\n" + "</div>\r\n" + "\r\n" + "\r\n"
				+ "    <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n" + "    </td>\r\n" + "  </tr>\r\n"
				+ "  </tbody>\r\n" + "  </table>\r\n" + "  <!--[if mso]></div><![endif]-->\r\n"
				+ "  <!--[if IE]></div><![endif]-->\r\n" + "</body>\r\n" + "\r\n" + "</html>";
		
		String date = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Date :"
				+ LocalDate.now() + "</span></p>";
		String purchesOrder = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Purchase Order No :"
				+ poDetailsForMail.get().getmPoNo() + "</span></p>";
		String supplierName = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Supplier Name : "
				+ comDetails.get().getScname() + "</span></p>";
		String supplierAddress = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Supplier Address : "
				+ comDetails.get().getScaddress1() + "," + comDetails.get().getScaddress2() + ","
				+ comDetails.get().getScaddress3() + "</span></p>";
		String speace1 = "<p style=\"font-size: 14px; line-height: 140%;\"> </p>";
		String tenderName = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Tender Name : "
				+ tenderDetailsForMail.get().getTendername() + "</span></p>";
		String speace2 = "<p style=\"font-size: 14px; line-height: 140%;\"> </p>";
		String tenderNumDetails = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">With reference Tender No : "
				+ tenderDetailsForMail.get().getBidno()
				+ ", please make arrangements to supply us with the following:</span></p>";
		String html1 = "</div>\r\n" + "\r\n" + "      </td>\r\n" + "    </tr>\r\n" + "  </tbody>\r\n" + "</table>\r\n"
				+ "\r\n" + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + "  </div>\r\n" + "</div>\r\n"
				+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
				+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + "    </div>\r\n"
				+ "  </div>\r\n" + "</div>\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
				+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\r\n"
				+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
				+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: transparent;\"><![endif]-->\r\n"
				+ "      \r\n"
				+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\r\n"
				+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
				+ "  <div style=\"height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\r\n"
				+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\"><!--<![endif]-->\r\n"
				+ "  \r\n"
				+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "  <tbody>\r\n" + "    <tr>\r\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "        \r\n" + "  <div>\r\n" + "    <strong>\r\n" + "  <head>\r\n"
				+ "    <!-- Required meta tags -->\r\n" + "    <meta charset=\"utf-8\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n"
				+ "\r\n" + "    <!-- Bootstrap CSS -->\r\n"
				+ "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css\" integrity=\"sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N\" crossorigin=\"anonymous\">\r\n"
				+ "\r\n" + "    \r\n" + "  </head>\r\n" + "  <body>\r\n"
				+ "    <table style=\"border: 1px solid black;\" class=\"table table-bordered\">\r\n" + "      <thead>\r\n" + "        <tr style=\"border: 1px solid black;\">\r\n"
				+ "          <th style=\"border: 1px solid black;\" scope=\"col\">No</th>\r\n" + "          <th style=\"border: 1px solid black;\" scope=\"col\">Description</th>\r\n"
				+ "          <th style=\"border: 1px solid black;\" scope=\"col\">Quantity</th>\r\n" + "          <!-- <th scope=\"col\">UMO</th> -->\r\n"
				+ "          <th style=\"border: 1px solid black;\" scope=\"col\">Unit Price</th>\r\n" + "          <th style=\"border: 1px solid black;\" scope=\"col\">Total Cost</th>\r\n"
				+ "        </tr>\r\n" + "      </thead>\r\n" + "      <tbody>";
		String html2 = "<tr style=\"border: 1px solid black;\">\r\n"
				+ "          <th scope=\"row\" style=\"border: 1px solid black;\">1</th>\r\n"
				+ "          <td style=\"border: 1px solid black;\">"+tenderDetailsForMail.get().getTenderdescription()+"</td>\r\n"
				+ "          <td style=\"border: 1px solid black;\">"+poDetailsForMail.get().getmQty()+"</td>\r\n"
				+ "          <!-- <td>@mdo</td> -->\r\n"
				+ "          <td style=\"border: 1px solid black;\">Rs. "+poDetailsForMail.get().getmUnitPrice()+".00</td>\r\n"
				+ "          <td style=\"border: 1px solid black;\">Rs. "+poDetailsForMail.get().getmTotalCost()+".00</td>\r\n"
				+ "        </tr>";
		String html3 = "<tr style=\"border: 1px solid black;\">\r\n" + "          <th style=\"border: 1px solid black;\" scope=\"row\"></th>\r\n"
				+ "          <td style=\"border: 1px solid black;\" colspan=\"3\">Total Cost in LKR (Excluding VAT)</td>\r\n" + "          <td style=\"border: 1px solid black;\">Rs. "
				+ poDetailsForMail.get().getmTotalCost() + ".00</td>\r\n" + "        </tr>";
		String html4 = "</tbody>\r\n" + "    </table>\r\n"
				+ "    <script src=\"https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js\" integrity=\"sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj\" crossorigin=\"anonymous\"></script>\r\n"
				+ "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct\" crossorigin=\"anonymous\"></script>\r\n"
				+ "  </body>\r\n" + "</strong>\r\n" + "  </div>\r\n" + "\r\n" + "      </td>\r\n" + "    </tr>\r\n"
				+ "  </tbody>\r\n" + "</table>\r\n" + "\r\n"
				+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "  <tbody>\r\n" + "    <tr>\r\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 40px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "        \r\n" + "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
				+ "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Terms &amp; Conditions:</span></p>\r\n"
				+ "<ul>";
		String delivery = "<li style=\"font-size: 18px; line-height: 25.2px;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Delivery - "
				+ poDetailsForMail.get().getmDelivery() + "</span></li>";
		String warranty = "<li style=\"font-size: 18px; line-height: 25.2px;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Warranty - "
				+ poDetailsForMail.get().getmWarranty() + "</span></li>";
		String payment = "<li style=\"font-size: 18px; line-height: 25.2px;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Payment Term - "
				+ poDetailsForMail.get().getmPaymentTerms() + "</span></li>";
		String banksVat = "<li style=\"font-size: 18px; line-height: 25.2px;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Bank's VAT No should be mentioned in the invoice as "
				+ poDetailsForMail.get().getBankVatNo() + ".</span></li>";
		String panelty = "<li style=\"font-size: 18px; line-height: 25.2px;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Any delay in delivery will accrue a penalty of "
				+ poDetailsForMail.get().getPanelty()
				+ " of the designated purchase price (Excluding VAT) referred to above for each banking business day </span></li>";
		String html5 = "</ul>\r\n"
				+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 20px; line-height: 28px;\"><strong><span style=\"color: #34495e; line-height: 28px; font-size: 20px;\">Yours faithfully,</span></strong></span></p>\r\n"
				+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 20px; line-height: 28px;\"><strong><span style=\"color: #34495e; line-height: 28px; font-size: 20px;\">National Development Bank PLC.</span></strong></span></p>\r\n"
				+ "  </div>\r\n" + "\r\n" + "      </td>\r\n" + "    </tr>\r\n" + "  </tbody>\r\n" + "</table>\r\n"
				+ "\r\n" + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + "  </div>\r\n" + "</div>\r\n"
				+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
				+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + "    </div>\r\n"
				+ "  </div>\r\n" + "</div>\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
				+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #18163a;\">\r\n"
				+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
				+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #18163a;\"><![endif]-->\r\n"
				+ "      \r\n"
				+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 20px 20px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
				+ "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n"
				+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
				+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 20px 20px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
				+ "  \r\n"
				+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "  <tbody>\r\n" + "    <tr>\r\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "        \r\n" + "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
				+ "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\">National Development Bank PLC </span></p>\r\n"
				+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\">40, Nawam Mawatha, Colombo 2, Sri Lanka</span></p>\r\n"
				+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\">T: +94 11 244 8448 | Ext. 31152</span></p>\r\n"
				+ "  </div>\r\n" + "\r\n" + "      </td>\r\n" + "    </tr>\r\n" + "  </tbody>\r\n" + "</table>\r\n"
				+ "\r\n" + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + "  </div>\r\n" + "</div>\r\n"
				+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
				+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px 0px 0px 20px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
				+ "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n"
				+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
				+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px 0px 0px 20px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
				+ "  \r\n"
				+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "  <tbody>\r\n" + "    <tr>\r\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:5px 10px 10px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "        \r\n" + "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
				+ "    <p style=\"line-height: 140%; font-size: 14px;\"> </p>\r\n"
				+ "<p style=\"line-height: 140%; font-size: 14px;\"> </p>\r\n"
				+ "<p style=\"line-height: 140%; font-size: 14px;\"> </p>\r\n"
				+ "<p style=\"line-height: 140%; font-size: 14px;\"> </p>\r\n"
				+ "<p style=\"line-height: 140%; font-size: 14px;\"><span style=\"font-size: 14px; line-height: 19.6px;\"><span style=\"color: #ecf0f1; font-size: 14px; line-height: 19.6px;\"><span style=\"line-height: 19.6px; font-size: 14px;\">Company ©  All Rights Reserved</span></span></span></p>\r\n"
				+ "  </div>\r\n" + "\r\n" + "      </td>\r\n" + "    </tr>\r\n" + "  </tbody>\r\n" + "</table>\r\n"
				+ "\r\n" + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + "  </div>\r\n" + "</div>\r\n"
				+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
				+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + "    </div>\r\n"
				+ "  </div>\r\n" + "</div>\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: #f9f9f9\">\r\n"
				+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #1c103b;\">\r\n"
				+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
				+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: #f9f9f9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #1c103b;\"><![endif]-->\r\n"
				+ "      \r\n"
				+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
				+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
				+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
				+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
				+ "  \r\n"
				+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "  <tbody>\r\n" + "    <tr>\r\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:15px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "        \r\n"
				+ "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #1c103b;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
				+ "    <tbody>\r\n" + "      <tr style=\"vertical-align: top\">\r\n"
				+ "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
				+ "          <span>&#160;</span>\r\n" + "        </td>\r\n" + "      </tr>\r\n" + "    </tbody>\r\n"
				+ "  </table>\r\n" + "\r\n" + "      </td>\r\n" + "    </tr>\r\n" + "  </tbody>\r\n" + "</table>\r\n"
				+ "\r\n" + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + "  </div>\r\n" + "</div>\r\n"
				+ "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
				+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + "    </div>\r\n"
				+ "  </div>\r\n" + "</div>\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
				+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #f9f9f9;\">\r\n"
				+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
				+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #f9f9f9;\"><![endif]-->\r\n"
				+ "      \r\n"
				+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
				+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
				+ "  <div style=\"height: 100%;width: 100% !important;\">\r\n"
				+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
				+ "  \r\n"
				+ "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "  <tbody>\r\n" + "    <tr>\r\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 40px 30px 20px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "        \r\n" + "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n"
				+ "    \r\n" + "  </div>\r\n" + "\r\n" + "      </td>\r\n" + "    </tr>\r\n" + "  </tbody>\r\n"
				+ "</table>\r\n" + "\r\n" + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + "  </div>\r\n"
				+ "</div>\r\n" + "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
				+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + "    </div>\r\n"
				+ "  </div>\r\n" + "</div>\r\n" + "\r\n" + "\r\n"
				+ "    <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n" + "    </td>\r\n" + "  </tr>\r\n"
				+ "  </tbody>\r\n" + "  </table>\r\n" + "  <!--[if mso]></div><![endif]-->\r\n"
				+ "  <!--[if IE]></div><![endif]-->\r\n" + "</body>\r\n" + "\r\n" + "</html>";

		/*
		 * String body = "Issue PO - "+poDetailsForMail.get().getmPoNo() +
		 * "\nYou have been introduced by " + com.get().getScname() +
		 * "\nDate :"+LocalDate.now()
		 * 
		 * + "\nTo :"+comDetails.get().getScname()
		 * 
		 * +
		 * "\n\nAfter reviewing all the tenders submitted by different organizations, "
		 * +
		 * "\nwe have selected your company for the order of "+tenderDetailsForMail.get(
		 * ).getBidno()+" - "+tenderDetailsForMail.get().getTendername()+"." +
		 * "\nThe order quantity would be for "+qty+"." +
		 * "\nWe are also enclosing the terms and conditions for your consideration." +
		 * "\nIf you need any clarifications regarding the same, please feel free to contact "
		 * +tenderDetailsForMail.get().getCordinator1name()+" on "+tenderDetailsForMail.
		 * get().getCordinator1contactno() +
		 * "\nWe would appreciate if you could deliver the goods at the earliest." +
		 * "\n\nThank You!,";
		 */
		String body = firstPart+date+purchesOrder+supplierName+supplierAddress+speace1+tenderName+speace2+tenderNumDetails+html1+html2+html3+html4+delivery+warranty+payment+banksVat+panelty+html5;
		new Thread(new Runnable() {

			@Override
			public void run() {
				Optional<SubCompany> emailForSend = subCompanyRepo.getEmailsForSubCompany(companyCode);
				String emails = null;
				if (emailForSend.isPresent()) {
					emails = emailForSend.get().getScemailadmin();
					System.out.println(emailForSend.get().getScemailadmin());
				}
				List<String> to = new ArrayList<String>();
				to.add(emails);

				common.sendEMailHtml(to, subject, body);
			}
		}).start();

		try {
			Optional<TenderSubmissions> ts = appzTenderSubmissionsRepo.findById(tenderSubmissionId);
			if (ts.isPresent()) {
				TenderSubmissions tenderSubmissions = ts.get();
				tenderSubmissions.setTenderEligibility("17");
				appzTenderSubmissionsRepo.save(tenderSubmissions);
			}

			/*
			 * String k="<!doctype html>\r\n" + "<html lang=\"en\">\r\n"
			 * 
			 * + "  <body>\r\n" + "    <table class=\"table table-bordered\">\r\n" +
			 * "        <thead>\r\n" + "          <tr>\r\n" +
			 * "            <th scope=\"col\">No</th>\r\n" +
			 * "            <th scope=\"col\">Description</th>\r\n" +
			 * "            <th scope=\"col\">Quantity</th>\r\n" +
			 * "            <th scope=\"col\">UMO</th>\r\n" +
			 * "            <th scope=\"col\">Unit Price</th>\r\n" +
			 * "            <th scope=\"col\">Total Cost</th>\r\n" + "          </tr>\r\n" +
			 * "        </thead>\r\n" + "        <tbody>\r\n" + "          <tr>\r\n" +
			 * "            <th scope=\"row\">1</th>\r\n" + "            <td>RAM</td>\r\n" +
			 * "            <td>7</td>\r\n" + "            <td>@mdo</td>\r\n" +
			 * "            <td>Rs. 12000.00</td>\r\n" +
			 * "            <td>Rs. 84000.00</td>\r\n" + "          </tr>\r\n" +
			 * "          <tr>\r\n" + "            <th scope=\"row\">2</th>\r\n" +
			 * "            <td>SSD</td>\r\n" + "            <td>8</td>\r\n" +
			 * "            <td>@fat</td>\r\n" + "            <td>Rs. 18500.00</td>\r\n" +
			 * "            <td>Rs. 148000.00</td>\r\n" + "          </tr>\r\n" +
			 * "          <tr>\r\n" + "            <th scope=\"row\"></th>\r\n" +
			 * "            <td colspan=\"4\">Total Cost in LKR (Excluding VAT)</td>\r\n" +
			 * "            <td>Rs. 232000.00</td>\r\n" + "          </tr>\r\n" +
			 * "        </tbody>\r\n" + "      </table>\r\n" + "\r\n" +
			 * "    <script src=\"https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js\" integrity=\"sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj\" crossorigin=\"anonymous\"></script>\r\n"
			 * +
			 * "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct\" crossorigin=\"anonymous\"></script>\r\n"
			 * + "\r\n" + "    \r\n" + "  </body>\r\n" + "</html>"; FileOutputStream
			 * file=new FileOutputStream(new File("E://FileSave/output1.pdf")); Document
			 * document=new Document(); PdfWriter writer=PdfWriter.getInstance(document,
			 * file); document.open(); ByteArrayInputStream is=new
			 * ByteArrayInputStream(k.getBytes());
			 * 
			 * XMLWorkerHelper.getInstance().parseXHtml(writer,document,is);
			 * document.close(); file.close(); System.out.println("done-->>email");
			 */

		} catch (Exception e) {
			e.printStackTrace();
			returnData.put("responseText", "Error");
			return returnData;
		}
		return null;
	}

	@Override
	public JSONObject poReject(JSONObject data) {
		Long tenderSubmissionId = Long.valueOf(data.get("tenderSubmissionId").toString());
		JSONObject returnData = new JSONObject();
		try {
			Optional<TenderSubmissions> ts = appzTenderSubmissionsRepo.findById(tenderSubmissionId);
			if (ts.isPresent()) {
				TenderSubmissions tenderSubmissions = ts.get();
				tenderSubmissions.setTenderEligibility("18");
				appzTenderSubmissionsRepo.save(tenderSubmissions);
			}

		} catch (Exception e) {
			e.printStackTrace();
			returnData.put("responseText", "Error");
			return returnData;
		}
		return null;
	}

	@Override
	public JSONObject getPoDetailsForTable(HttpServletRequest request) {
		try {
			System.out.println("Request " + request);
			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();

			Page<Object[]> po_authentication_details = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "mPoNo";
				break;
			case "1":
				sorting = "mPoNo";
				break;
			case "2":
				sorting = "mComment";
				break;
			case "3":
				sorting = "mLocationCode";
				break;
			case "4":
				sorting = "mLocationName";
				break;
			case "5":
				sorting = "mOrderGrossValue";
				break;
			case "6":
				sorting = "mPoDate";
				break;
			case "7":
				sorting = "mTotal";
				break;
			case "8":
				sorting = "mTotalGrossAmount";
				break;
			}

//			cod.createdAt LIKE %?1% OR cod.expiryDate LIKE %?1% OR cod.modifiedBy LIKE %?1% OR cod.paymentType LIKE %?1% OR cod.RenewalDatePeriod
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				po_authentication_details = poRepo.getPoDetailsForTable(request.getParameter("search[value]"),
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));

				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				po_authentication_details = poRepo.getPoDetailsForTable(request.getParameter("search[value]"),
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
//				contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			long count = po_authentication_details.getTotalElements();
			System.out.println("get cn--->" + po_authentication_details.getContent().size());
			for (Object[] objects : po_authentication_details.getContent()) {

				if (objects[0] instanceof PoDetails && objects[1] instanceof TenderDetails && objects[2] instanceof User
						&& objects[3] instanceof TenderSubmissions && objects[4] instanceof RFP
						&& objects[5] instanceof SubCompany) {

					JSONObject ob = new JSONObject();
					PoDetails poDetails = (PoDetails) objects[0];
					TenderDetails tenderDetails = (TenderDetails) objects[1];
					User userDetails = (User) objects[2];
					TenderSubmissions tenderSubmissions = (TenderSubmissions) objects[3];
					RFP rfp = (RFP) objects[4];
					SubCompany subCompany = (SubCompany) objects[5];
//					
					ob.put("SupplierName", subCompany.getScname());
					ob.put("TenderName", tenderDetails.getTendername());
					ob.put("RfpFileName", rfp.getFileName());
					ob.put("RfpId", rfp.getRfpID());
					ob.put("BatchFile", poDetails.getmBatchFile());
					ob.put("TenderSubmissionId", tenderSubmissions.getSubmission_id());
					ob.put("CompanyCode", userDetails.getCompanyCode());
					ob.put("Qty", poDetails.getmQty());
					ob.put("TenderEligibility", tenderSubmissions.getTenderEligibility());
					ob.put("poId", poDetails.getmPoId());
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
		/* return null; */
		/* return null; */
	}

	@Override
	public JSONObject getPoDetailsForSupplierTable(HttpServletRequest request) {
		try {
			System.out.println("Request " + request);
			JSONObject data = new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			String CompCode = Long.toString(userCompanyID);
			System.out.println("Company code-------------->>>>" + userCompanyID);

			Page<Object[]> po_authentication_details = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "mPoNo";
				break;
			case "1":
				sorting = "mPoNo";
				break;
			case "2":
				sorting = "mComment";
				break;
			case "3":
				sorting = "mLocationCode";
				break;
			case "4":
				sorting = "mLocationName";
				break;
			case "5":
				sorting = "mOrderGrossValue";
				break;
			case "6":
				sorting = "mPoDate";
				break;
			case "7":
				sorting = "mTotal";
				break;
			case "8":
				sorting = "mTotalGrossAmount";
				break;
			}

//			cod.createdAt LIKE %?1% OR cod.expiryDate LIKE %?1% OR cod.modifiedBy LIKE %?1% OR cod.paymentType LIKE %?1% OR cod.RenewalDatePeriod
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				po_authentication_details = poRepo.getPoDetailsForSupplierTable(request.getParameter("search[value]"),
						CompCode,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));

				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				po_authentication_details = poRepo.getPoDetailsForSupplierTable(request.getParameter("search[value]"),
						CompCode,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
//				contract_detail = contractInvoiceHeaderRepo.getContractForSuppliers(id, Long.valueOf(user.getCompanyCode()), request.getParameter("search[value]"), PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}

			JSONArray ar = new JSONArray();
			long count = po_authentication_details.getTotalElements();
			System.out.println("get cn--->" + po_authentication_details.getContent().size());
			for (Object[] objects : po_authentication_details.getContent()) {

				if (objects[0] instanceof PoDetails && objects[1] instanceof TenderDetails && objects[2] instanceof User
						&& objects[3] instanceof TenderSubmissions && objects[4] instanceof RFP
						&& objects[5] instanceof SubCompany) {

					JSONObject ob = new JSONObject();
					PoDetails poDetails = (PoDetails) objects[0];
					TenderDetails tenderDetails = (TenderDetails) objects[1];
					User userDetails = (User) objects[2];
					TenderSubmissions tenderSubmissions = (TenderSubmissions) objects[3];
					RFP rfp = (RFP) objects[4];
					SubCompany subCompany = (SubCompany) objects[5];
//					
					ob.put("SupplierName", subCompany.getScname());
					ob.put("TenderName", tenderDetails.getTendername());
					ob.put("RfpFileName", rfp.getFileName());
					ob.put("RfpId", rfp.getRfpID());
					ob.put("BatchFile", poDetails.getmBatchFile());
					ob.put("TenderSubmissionId", tenderSubmissions.getSubmission_id());
					ob.put("CompanyCode", userDetails.getCompanyCode());
					ob.put("Qty", poDetails.getmQty());
					ob.put("TenderEligibility", tenderSubmissions.getTenderEligibility());

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
	public String addTermsAndConditions(JSONObject data, HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String userName = user.getUserid();
		TermsAndConditions trmAndCon = new TermsAndConditions();
		try {
			
			trmAndCon.setTermsConditions(data.get("termsAndConditions").toString());
			
			trmAndCon.setStatus(Long.valueOf(3));
			trmAndCon.setCreatedDateTime(LocalDateTime.now());
			trmAndCon.setCreatedUser(userName);
			String trmAndConwithoutSpace = data.get("termsAndConditions").toString().replaceAll("\\s", "");
			trmAndCon.setTermsOrConditionsWithoutSpace(trmAndConwithoutSpace);
			termsAndConditionsRepo.save(trmAndCon);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		String msg = "Success";
		return msg;
	}

	@Override
	public JSONObject getTermsAndConditions() {
		JSONObject data = new JSONObject();
		try {
			List<TermsAndConditions> trmAndCon = termsAndConditionsRepo.getTermsAndConditions();
			JSONArray ar = new JSONArray();
			for (TermsAndConditions objects : trmAndCon) {
				
					JSONObject trm = new JSONObject();

					trm.put("trmAndConId", objects.getTermsAndConditionsId());
					trm.put("trmAndCon", objects.getTermsConditions());
					

					ar.add(trm);
				
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
	public String addTermsAndConditionsToTransTable(JSONObject data, HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String userName = user.getUserid();
		TermsAndConditionsTrans trmAndConTrans = new TermsAndConditionsTrans();
		try {
			//data.get("termsAndConditions").toString()
			trmAndConTrans.setTermsConditionsId(Long.valueOf(data.get("trmAndCon").toString()));
			trmAndConTrans.setTenderId(Long.valueOf(Long.valueOf(data.get("tenderId").toString())));
			trmAndConTrans.setStatus(Long.valueOf(3));
			trmAndConTrans.setCreatedDateTime(LocalDateTime.now());
			trmAndConTrans.setCreatedUser(userName);
			termsAndConditionsTransRepo.save(trmAndConTrans);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		String msg = "Success";
		return msg;
	}

	@Override
	public JSONObject getTermsAndConditionsForTable(HttpServletRequest request, Long tenderId) {
		try {
			System.out.println("Request " + request);
			JSONObject data = new JSONObject();
			

			Page<Object[]> termsAndCon = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "1":
				sorting = "termsConditions";
				break;
			
			
			}


			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				termsAndCon = poRepo.getTermsAndConditionsForTable(request.getParameter("search[value]"),
						tenderId,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));

				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				termsAndCon = poRepo.getTermsAndConditionsForTable(request.getParameter("search[value]"),
						tenderId,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));

			}

			JSONArray ar = new JSONArray();
			long count = termsAndCon.getTotalElements();
			System.out.println("get cn--->" + termsAndCon.getContent().size());
			int index = 1;
			for (Object[] objects : termsAndCon.getContent()) {
					
				if (objects[0] instanceof TermsAndConditions && objects[1] instanceof TermsAndConditionsTrans) {

					
					TermsAndConditions tc = (TermsAndConditions) objects[0];
					TermsAndConditionsTrans tct = (TermsAndConditionsTrans) objects[1];
				
					JSONObject ob = new JSONObject();
					
					ob.put("trmsAndConTransId", tct.getTermsAndConditionsTransId());
					ob.put("trmsAndCon", tc.getTermsConditions());
					ob.put("status", tct.getStatus());
					ob.put("index", index);

					ar.add(ob);
					index++;
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
	public JSONObject termsAndConditionsStatusTransTbl(long id, String approve) {
		Optional<TermsAndConditionsTrans> tct = termsAndConditionsTransRepo.findById(id);
		JSONObject ob = new JSONObject();
		Long active = (long) 3;
		Long deactive = (long) 6;
		try {

			if (tct.isPresent()) {

				if (approve.equals("active")) {

					tct.get().setStatus(active);
					termsAndConditionsTransRepo.save(tct.get());

				}else if(approve.equals("deactive")) {

					tct.get().setStatus(deactive);
					termsAndConditionsTransRepo.save(tct.get());

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ob;
		}
		return ob;
	}

	@Override
	public JSONObject viewTermsAndConditions(HttpServletRequest request) {
		try {
			System.out.println("Request " + request);
			JSONObject data = new JSONObject();
			

			Page<TermsAndConditions> termsAndCon = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "1":
				sorting = "termsConditions";
				break;
			
			
			}


			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				termsAndCon = poRepo.viewTermsAndConditions(request.getParameter("search[value]"),
						
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));

				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				termsAndCon = poRepo.viewTermsAndConditions(request.getParameter("search[value]"),
						
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
								Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));

			}

			JSONArray ar = new JSONArray();
			long count = termsAndCon.getTotalElements();
			System.out.println("get cn--->" + termsAndCon.getContent().size());
			int index = 1;
			for (TermsAndConditions tc : termsAndCon.getContent()) {
					
					JSONObject ob = new JSONObject();
					
					ob.put("trmsAndConId", tc.getTermsAndConditionsId());
					ob.put("trmsAndCon", tc.getTermsConditions());
					ob.put("status", tc.getStatus());
					ob.put("index", index);

					ar.add(ob);
					index++;
				
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
	public JSONObject termsAndConditionsStatusTbl(long id, String approve) {
		Optional<TermsAndConditions> tc = termsAndConditionsRepo.findById(id);
		JSONObject ob = new JSONObject();
		Long active = (long) 3;
		Long deactive = (long) 6;
		try {

			if (tc.isPresent()) {

				if (approve.equals("active")) {

					tc.get().setStatus(active);
					termsAndConditionsRepo.save(tc.get());

				}else if(approve.equals("deactive")) {

					tc.get().setStatus(deactive);
					termsAndConditionsRepo.save(tc.get());

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ob;
		}
		return ob;
	}

	@Override
	public Boolean getWetherTermsConExists(String termsAndCon) {
		return termsAndConditionsRepo.existsByTermsConditions(termsAndCon);
	}

	@Override
	public Boolean getWetherTermsConTenderExists(Long tenderId, Long termsCon) {
		return termsAndConditionsTransRepo.existsByTenderIdAndTermsConditionsId(tenderId,termsCon);
	}

	@Override
	public JSONObject editTermsAndConditions(JSONObject data) {
		JSONObject ob = new JSONObject();
		String msg = null;
		try {
			
			Long id = Long.valueOf(data.get("id").toString());

			Optional<TermsAndConditions> tc = termsAndConditionsRepo.findById(id);
			if (tc.isPresent()) {

				tc.get().setTermsConditions(data.get("termsAndConditionEdit").toString());
				tc.get().setTermsOrConditionsWithoutSpace(data.get("termsAndConditionEdit").toString().replaceAll("\\s", ""));
				termsAndConditionsRepo.save(tc.get());

			}
			ob.put("responseText", "Success");
		}catch(Exception e) {
			e.printStackTrace();

			return null;
		}
		return ob;
	}

	@Override
	public JSONObject getTermsAndConditionsForIssuePoPage(Long tenderId) {
		JSONObject data = new JSONObject();
		try {
			List<Object[]> tc = termsAndConditionsRepo.getTermsAndConditionsForIssuePoPage(tenderId);
			JSONArray ar = new JSONArray();
			for (Object[] objects : tc) {
				if (objects[0] instanceof TermsAndConditions && objects[1] instanceof TermsAndConditionsTrans) {

					TermsAndConditions tnc = (TermsAndConditions) objects[0];
					TermsAndConditionsTrans tnct = (TermsAndConditionsTrans) objects[1];
					
					JSONObject termsAndCon = new JSONObject();
					termsAndCon.put("termsAndCon", tnc.getTermsConditions());
					termsAndCon.put("trmAndConWithoutSpace", tnc.getTermsOrConditionsWithoutSpace());
					termsAndCon.put("termsAndConditionId", tnc.getTermsAndConditionsId());
					termsAndCon.put("trmAndConTrnsId", tnct.getTermsAndConditionsTransId());
					ar.add(termsAndCon);
				}
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
	public JSONObject getTermsAndConView(long id) {
		JSONObject data = new JSONObject();
		try {
			JSONArray ar = new JSONArray();
			List<Object[]> termsAndCon = null;
			
			termsAndCon = termsAndConditionsRepo.getTermsAndConView(id);
			
			for (Object[] object : termsAndCon) {
				if (object[0] instanceof TermsAndConditionsFromPo && object[1] instanceof TermsAndConditions) {
					JSONObject ob = new JSONObject();
					TermsAndConditionsFromPo termNconFromPo = (TermsAndConditionsFromPo) object[0];
					TermsAndConditions tnc = (TermsAndConditions) object[1];
	
					ob.put("termAndCon", tnc.getTermsConditions());  
					ob.put("termAndConDetails", termNconFromPo.getTermsConditionsVal());
					
					ar.add(ob);
					
					}
				
			}
			data.put("data", ar);
			System.out.println("data ds--->" + data);
			return data;
			
		}catch(Exception e) {
			e.printStackTrace();
			return data;
		}
	}

	
	@Override
	public JSONObject getTenders() {
		JSONObject data = new JSONObject();
		try {
			List<Object[]> tenderDetails = reIssuePoRepo.getTenderDetailsForReIssuePo();
			JSONArray ar = new JSONArray();
			for (Object[] objects : tenderDetails) {
				if (objects[0] instanceof TenderDetails && objects[1] instanceof TenderSubmissions
						&& objects[2] instanceof FinacialResponsesVendor) {

					TenderDetails td = (TenderDetails) objects[0];
					TenderSubmissions ts = (TenderSubmissions) objects[1];
					FinacialResponsesVendor fr = (FinacialResponsesVendor) objects[2];

					JSONObject tenderID = new JSONObject();

					tenderID.put("tenderId", td.getTrnderid());
					tenderID.put("t_tenderName", td.getTendername());
					tenderID.put("tBidNumber", td.getBidno());
					tenderID.put("closingDateTime", td.getClosingDateTime());
					tenderID.put("amountLkr", fr.getAmountlkr());
					tenderID.put("amountUsd", fr.getAmountusd());

					ar.add(tenderID);
				}
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
	public JSONObject getTenderDetailsForReIssuePo(HttpServletRequest request, Long id) {

		JSONObject returnObj = new JSONObject();
		
		try {

			List<Object[]> tds = reIssuePoRepo.getTenderByIdDetailsForReIssuePo(id);
			JSONArray ar = new JSONArray();

			for (Object[] tenderDetails : tds) {
				       if (tenderDetails[0] instanceof TenderDetails 
					//	&& tenderDetails[1] instanceof RFP
						&& tenderDetails[1] instanceof EligibleCategory 
						&& tenderDetails[2] instanceof TenderSubmissions
						&& tenderDetails[3] instanceof SubCompany 
						&& tenderDetails[4] instanceof FinacialResponsesVendor
					//	&& tenderDetails[6] instanceof BoardPaperUpload
						) 
				{
					TenderDetails td = (TenderDetails) tenderDetails[0];
					//RFP rfp = (RFP) tenderDetails[1];
					EligibleCategory ec = (EligibleCategory) tenderDetails[1];
					TenderSubmissions ts = (TenderSubmissions) tenderDetails[2];
					SubCompany sc = (SubCompany) tenderDetails[3];
					FinacialResponsesVendor fr = (FinacialResponsesVendor) tenderDetails[4];
					//BoardPaperUpload bpu = (BoardPaperUpload) tenderDetails[6];
					
					JSONObject data = new JSONObject();
					
					//data.put("TenderId", td.getTrnderid());
					data.put("Tendername", td.getTendername());
					data.put("Category", ec.getEligibleCategortName());
					//data.put("RfpId", td.getRfpId());
					//data.put("RfpFileName", rfp.getFileName());
					//data.put("VenderId", ts.getVendorId());
					//data.put("TenderSubmissionId", ts.getSubmission_id());
					//data.put("SupplierName", sc.getScname());

					data.put("amountLkr", fr.getAmountlkr());
					//data.put("amountUsd", fr.getAmountusd());
					ar.add(data);
					//data.put("boardPaper", bpu.getBoardPaperFileName());
				}
				       
			}
			returnObj.put("data", ar);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return returnObj;
	}


	@Override
	public JSONObject addReIssuePo(JSONObject data, HttpServletRequest request) {
		//String msg = null;
		JSONObject res = new JSONObject();
		
		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		  User user = (User) authentication.getPrincipal(); 
		  Long userCompanyID = new Long(user.getCompanyCode());
		 
		  
		  
		  Re_issuePO poDetails = new Re_issuePO();

		try {

			Long qty = Long.valueOf(data.get("qty").toString());
			Long unitPriceLKR = Long.valueOf(data.get("unitPriceLkr").toString());
			Long tot = qty * unitPriceLKR;
			
			poDetails.setQty(Long.valueOf(data.get("qty").toString()));
			poDetails.setTenderId(Long.valueOf(data.get("tenderIdd").toString()));
			poDetails.setUnitPrice(Long.valueOf(data.get("unitPriceLkr").toString()));
			poDetails.setCategory(data.get("category").toString());
			//poDetails.setSupplierCode(data.get("supplierId").toString());
			//poDetails.setRfpId(Long.valueOf(data.get("rfpId").toString()));
			//poDetails.setSubmissionId(Long.valueOf(data.get("TenderSubmissionId").toString()));
			//poDetails.setmTotalCost(tot);
			
			poDetails = reIssuePoRepo.save(poDetails);
			Re_issuePO saved  = null;
			
		//   PO/Year/ReIssuePOId --> ReIssuePO number Format --> Issue after ReIssuePO save
			
							Long reIssuePoId = poDetails.getReIssuePoId();
						//	Long reIssuecatIdForFind = poDetails.getCategory();
							
							Optional<Re_issuePO> poIdForUser = reIssuePoRepo.findById(reIssuePoId);
						//	Optional<EligibleCategory> catIdForUser = eligibleCategoryRepo.findById(reIssuecatIdForFind);
							
							if (poIdForUser.isPresent()) {
								//LocalDateTime createdDateTime = poIdForUser.get().getCreatedAt();
								//int year = createdDateTime.getYear();
								
								poIdForUser.get().setReIssuePoNo("PO/"+poIdForUser.get().getReIssuePoId());
								 saved =	reIssuePoRepo.save(poIdForUser.get());	
								
							}	
							Optional<Re_issuePO> poIdForUserReturn = reIssuePoRepo.findById(reIssuePoId);
							String reIssuePoNo= poIdForUserReturn.get().getReIssuePoNo();
							res.put("msg", "success");
							res.put("code", "01");
							res.put("poNo", saved.getReIssuePoNo());
						
						
//						Optional<Re_issuePO> po = reIssuePoRepo.findById(reIssuePoId);
//						if (po.isPresent()) {
//							Re_issuePO re_issuePO = po.get();
//							// if (status.equals("open")) {
//
//							// tender.get().setTrnderid(user.getTrnderid());
//							//re_issuePO.setApprovalstatus(statusOpen);
//							//re_issuePO.setStatus(statusOpen);
//							re_issuePO = reIssuePoRepo.save(re_issuePO);
//							res.put("msg", "success");
//							res.put("code", "01");
//							
//
//						} 
						
						return res;

			//msg = "Success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			res.put("msg", "error");
			res.put("code", "02");
			return res;
		}
		//return res;

	}



	/*
	 * @Override public JSONObject sendEmailAndAuth(Long tenderSubmissionId, Long
	 * companyCode) { String msg = null; try { Optional<TenderSubmissions> ts =
	 * appzTenderSubmissionsRepo.findById(tenderSubmissionId); if (ts.isPresent()) {
	 * TenderSubmissions tenderSubmissions = ts.get();
	 * tenderSubmissions.setTenderEligibility("17");
	 * appzTenderSubmissionsRepo.save(tenderSubmissions); }
	 * 
	 * 
	 * msg = "Success";
	 * 
	 * } catch (Exception e) { e.printStackTrace(); return null; } return null;
	 * 
	 * }
	 */

}
