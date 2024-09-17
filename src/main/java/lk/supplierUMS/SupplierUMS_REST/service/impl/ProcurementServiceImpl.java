package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.AppzEligibleCodesRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.AppzTenderSubmissionsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.BoardPaperUploadRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EligibleCategoryRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EvaluationMarksAllRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.FinancialCodesRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.FinancialDetailsPerTenderRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.FinancialResponseRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.FinancialValuesPerTenderRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ProcurementCommitteeRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ProcurementRequestRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RfpDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RfpDetailsResponseRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.RfpRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.TenderDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.comman.Common;
import lk.supplierUMS.SupplierUMS_REST.comman.CommonEmail;
import lk.supplierUMS.SupplierUMS_REST.comman.DeEnCode;
import lk.supplierUMS.SupplierUMS_REST.entity.AppzEligibleCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.AppzTenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.BoardPaperUpload;
import lk.supplierUMS.SupplierUMS_REST.entity.City;
import lk.supplierUMS.SupplierUMS_REST.entity.EPoHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.Province;
import lk.supplierUMS.SupplierUMS_REST.entity.RFP;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetailsResponse;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.SupplierDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategoryProducts;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationMarksAll;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationSheetCreate;
import lk.supplierUMS.SupplierUMS_REST.entity.FinacialResponsesVendor;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialDetailsPerTender;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialValuesPerTender;
import lk.supplierUMS.SupplierUMS_REST.entity.ProcurementCommittee;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.SupplierProductDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
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
import lk.supplierUMS.SupplierUMS_REST.service.CompanyDetailsService;
import lk.supplierUMS.SupplierUMS_REST.service.ProcurementService;
import lk.supplierUMS.SupplierUMS_REST.service.TenderDetailsService;
import lk.supplierUMS.SupplierUMS_REST.entity.ProcurementRequest;

import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class ProcurementServiceImpl implements ProcurementService {

	@Value("${tenderboardpaper.doc.path}")
	String tenderBoardPaper;
	
	@Value("${tendercappexfile.doc.path}")
	String tenderCappexFile;
	
	@Value("${tenderoppexfile.doc.path}")
	String tenderOppexFile;
	
	@Value("${tendermemo.doc.path}")
	String tenderMemo;
	
	@Value("${procurementCommittee.doc.path}")
	String procurementCommitteeDataPath;
	
	@Autowired
	TenderDetailsRepo tenderDetailsRepo;
	
	@Autowired
	ProcurementCommitteeRepo procurementCommitteeRepo;
	
	@Autowired
	EvaluationMarksAllRepo evaluationMarksAllRepo;
	
	@Autowired
	BoardPaperUploadRepo boardPaperUploadRepo;
	
	@Autowired
	ProcurementRequestRepo procurementRequestRepo;
	
	@Autowired
	RfpRepo rfpRepo;
	
	@Autowired
	EligibleCategoryRepo eligibleCategoryRepo;
	
	@Autowired
	CompanyDetailsService companyDetailsService;
	
	@Override
	public List<TenderDetails> getTendersForProcCommitteCreation() {
		try {
			
			LocalDateTime NowDateAndTimee = LocalDateTime.now();
			
			List<TenderDetails> next = tenderDetailsRepo.getTendersForProcCommitteCreation(NowDateAndTimee);
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> getTendersForProcCommitteeMembers() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long userCompanyID = new Long(user.getCompanyCode());
		String strCompanyId = Long.toString(userCompanyID);
		try {
			List<User> next = tenderDetailsRepo.getTendersForProcCommitteeMembers(strCompanyId);
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String addProcurementCommittee(JSONObject data, HttpServletRequest request) {
		String msg = "Success";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String userId = user.getUserid(); 
		ProcurementCommittee procCom = new ProcurementCommittee();
		
		try {
			
			List<TenderSubmissions> ts = procurementCommitteeRepo.getTenderDetailsOfSupplier(Long.valueOf(data.get("tenderID").toString()));
			//ArrayList<TenderSubmissions> ts = (ArrayList<TenderSubmissions>) procurementCommitteeRepo.getTenderDetailsOfSupplier(Long.valueOf(data.get("tenderID").toString()));
			int size11 = ts.size();
			System.out.println("ListSize "+size11);
			
			
			for(int i=0; i< ts.size(); i++) {
				
				
				procCom.setSupplierId(ts.get(i).getSupplierId());
				procCom.setTenderId(Long.valueOf(data.get("tenderID").toString()));
				procCom.setUserId(data.get("committeeMember").toString());
				procCom.setValidateOrNot(19);
				procCom.setCreatedDateTime(LocalDateTime.now());
				procCom.setCreatedUser(userId);
				procurementCommitteeRepo.save(procCom);
				System.out.println("forloop"+i);
			}
		/*	if (data.containsKey("input_lastsixmonths")) {
				String headerData[]=data.get("input_lastsixmonths").toString().split("base64,");
				String extention[]=data.get("input_lastsixmonths").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(supplierRegistrationDataPath,  data.get("supplierID").toString()+"-"+user.getCompanyCode().toString()+ "/lastsixmonths",exten, head);
				
				supDeatils.setLastSixMonths(
						supplierRegistrationDataPath + data.get("supplierID").toString()+"-"+user.getCompanyCode().toString() + "/lastsixmonths." + exten);
			}
		*/	
			if (data.containsKey("upload_support_doc1")) {
				String headerData[]=data.get("upload_support_doc1").toString().split("base64,");
				String extention[]=data.get("upload_support_doc1").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(procurementCommitteeDataPath, procCom.getProcComId() + "/supportDoc1.",exten, head);
					
				procCom.setSupportdoc1(
						procurementCommitteeDataPath + procCom.getProcComId()+ "/supportDoc1." + exten);
			}
			
			if (data.containsKey("upload_support_doc2")) {
				String headerData[]=data.get("upload_support_doc2").toString().split("base64,");
				String extention[]=data.get("upload_support_doc2").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(procurementCommitteeDataPath,procCom.getProcComId()+ "/supportDoc2" ,exten, head);
				
				procCom.setSupportdoc2(
						procurementCommitteeDataPath + procCom.getProcComId()+ "/supportDoc2." + exten);
			}
			
			if (data.containsKey("upload_support_doc3")) {
				String headerData[]=data.get("upload_support_doc3").toString().split("base64,");
				String extention[]=data.get("upload_support_doc3").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(procurementCommitteeDataPath, procCom.getProcComId()+ "/supportDoc3" ,exten, head);
				
				procCom.setSupportdoc3(
						procurementCommitteeDataPath + procCom.getProcComId()+"/supportDoc3." + exten);
			}
			
			procurementCommitteeRepo.save(procCom);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Error";
		}
		return msg;
	}

	@Override
	public JSONObject getVotingDetailsForVoting(HttpServletRequest request, Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String userId = user.getUserid(); 
		
		JSONObject data = new JSONObject();
		
		try {	
			Page<Object[]> procurementVoting = null;
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
				sorting = "sc.scname";
				break;

			}
			
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				procurementVoting = procurementCommitteeRepo.getVotingDetailsForVoting(request.getParameter("search[value]"),
						id,userId,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
										Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));

				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				procurementVoting = procurementCommitteeRepo.getVotingDetailsForVoting(request.getParameter("search[value]"),
						id,userId,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
										Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
				
			}

			JSONArray ar = new JSONArray();
			long count = procurementVoting.getSize();
			//lo
			System.out.println("get cn--->" + procurementVoting.getContent().size());
			int place = 1;
			
			for (Object[] objects : procurementVoting.getContent()) {
				
					if (objects[0] instanceof ProcurementCommittee && objects[1] instanceof TenderDetails && objects[2] instanceof SubCompany && objects[3] instanceof TenderSubmissions) {
						
					ProcurementCommittee pc = (ProcurementCommittee) objects[0];
					TenderDetails td = (TenderDetails) objects[1];
					SubCompany sc = (SubCompany) objects[2];
					TenderSubmissions ts = (TenderSubmissions) objects[3];
					
					JSONObject procCom = new JSONObject();
					
					
					procCom.put("procComId", pc.getProcComId());
					procCom.put("tenderNum", td.getBidno());
					procCom.put("tenderName", td.getTendername());
					procCom.put("supplierName", sc.getScname());
					procCom.put("voteStatus", pc.getValidateOrNot());
					procCom.put("tenderId", td.getTrnderid());
					procCom.put("supplierId", pc.getSupplierId());
					procCom.put("index", place);
					procCom.put("CompanyProfile", ts.getUploadCompanyProfileName());
					procCom.put("RfpFile", ts.getUploadRfpFileName());
					procCom.put("SupportDoc1", ts.getUploadSupportDocOne());
					procCom.put("SupportDoc2", ts.getUploadSupportDocTwo());
					procCom.put("SupportDoc3", ts.getUploadSupportDocThree());
					 place++;
					ar.add(procCom);

				}

			}
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			System.out.println("data ds--->" + data);
			

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return data;
	}


	@Override
	public JSONObject voteToSupplier(JSONObject data, long supplierId) {
		
		String msg = null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User us = (User) authentication.getPrincipal();
		
		String userId = (us.getUserid());
		String supplierStr = Long.toString(supplierId);
		
		Optional<EvaluationMarksAll> evaAllMarks = procurementCommitteeRepo.getEmpDetailsForVoting(Long.valueOf(data.get("tenderid").toString()),supplierStr);
		List<ProcurementCommittee> procComList = procurementCommitteeRepo.getProcComDetailsList(Long.valueOf(data.get("tenderid").toString()),userId);
		JSONObject ob = new JSONObject();

		
		try {
			
			Long id = Long.valueOf(data.get("selectedProcComId").toString());
			Optional<ProcurementCommittee> procCom = procurementCommitteeRepo.findById(id);	
			
			if(procCom.isPresent()) {
				System.out.println("Add vote reason");
				ProcurementCommittee pc = procCom.get();
				pc.setComment(data.get("comment").toString());

		
			if(Boolean.valueOf(data.get("isVoted").toString())) {
				
				for(int i=0; i< procComList.size(); i++) {
					procComList.get(i).setValidateOrNot(20);
					//procComList.setComment(data.get("comment").toString());
					procurementCommitteeRepo.save(procComList.get(i));
			
				}
				
				procCom.get().setValidateOrNot(18);
				procurementCommitteeRepo.save(procCom.get());
				long nowVote;
				if(evaAllMarks.get().getVoteFromProcument() == null) {
					nowVote = 1;
					evaAllMarks.get().setVoteFromProcument(nowVote);
					evaluationMarksAllRepo.save(evaAllMarks.get());
				
					
				}else {
					long newVote2;
					newVote2 = evaAllMarks.get().getVoteFromProcument();
					newVote2++;
					evaAllMarks.get().setVoteFromProcument(newVote2);
					evaluationMarksAllRepo.save(evaAllMarks.get());
				}
			}else if(!Boolean.valueOf(data.get("isVoted").toString())) {
				procCom.get().setValidateOrNot(20);
				procurementCommitteeRepo.save(procCom.get());
			
			}
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return ob;
		}
		return ob;
	}

	@Override
	public List<SubCompany> getSuppliersForProcCommitteeMembers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getVotingDetails(HttpServletRequest request, Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String userId = user.getUserid(); 
		try {

			JSONObject data = new JSONObject();
			
			
			Page<Object[]> procurementVoting = null;
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
				sorting = "sc.scname";
				break;

			}
			
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				procurementVoting = procurementCommitteeRepo.getVotingDetails(request.getParameter("search[value]"),
						id,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
										Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));

				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				procurementVoting = procurementCommitteeRepo.getVotingDetails(request.getParameter("search[value]"),
						id,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
										Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
				
			}

			JSONArray ar = new JSONArray();
			long count = procurementVoting.getSize();
			//lo
			System.out.println("get cn--->" + procurementVoting.getContent().size());
			int place = 1;
			
			for (Object[] objects : procurementVoting.getContent()) {
				
					if (objects[0] instanceof ProcurementCommittee && objects[1] instanceof 
							TenderDetails && objects[2] instanceof User && objects[3] instanceof SubCompany) {
					
					ProcurementCommittee pc = (ProcurementCommittee) objects[0];
					TenderDetails td = (TenderDetails) objects[1];
					User userR = (User) objects[2];
					SubCompany sc = (SubCompany) objects[3];
					
					JSONObject procCom = new JSONObject();
					//JSONObject ob = new JSONObject();
					
					procCom.put("procComId", pc.getProcComId());
					procCom.put("tenderNum", td.getBidno());
					procCom.put("tenderName", td.getTendername());
					procCom.put("supplierName", sc.getScname());
					procCom.put("voteStatus", pc.getValidateOrNot());
					procCom.put("tenderId", td.getTrnderid());
					procCom.put("supplierId", pc.getSupplierId());
					procCom.put("userName", userR.getUsername());
					procCom.put("index", place);
					 place++;
					ar.add(procCom);

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
	public Boolean getWetherProcComExists(String committeeMember, Long procTenderid) {
		return procurementCommitteeRepo.existsByUserIdAndTenderId(committeeMember,procTenderid);
	}

	@Override
	public String boardPaperSubmit(JSONObject data, HttpServletRequest request, String fileName) {
		String msg = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String userId = user.getUserid(); 
		
		try {
			
			BoardPaperUpload brdPaperUpload = new BoardPaperUpload();
			
			if (fileName.equals("uploadBoardPapers")) {
				String headerData[]=data.get("upload_board_papers").toString().split("base64,");
				String extention[]=data.get("upload_board_papers").toString().split("[/;]");
				String exten = extention[1];
				System.out.println("split------------------->"+exten);
				String head=headerData[1].substring(0, (headerData[1].length()));
				new DeEnCode().decodeMethod(tenderBoardPaper,data.get("tenderID").toString(),exten, head);
				
				String headerData1[]=data.get("upload_cappex_file").toString().split("base64,");
				String extention1[]=data.get("upload_cappex_file").toString().split("[/;]");
				String exten1 = extention1[1];
				System.out.println("split------------------->"+exten1);
				String head1=headerData1[1].substring(0, (headerData1[1].length()));
				new DeEnCode().decodeMethod(tenderCappexFile,data.get("tenderID").toString(),exten1, head1);
				
				String headerData3[]=data.get("upload_oppex_file").toString().split("base64,");
				String extention3[]=data.get("upload_oppex_file").toString().split("[/;]");
				String exten3 = extention3[1];
				System.out.println("split------------------->"+exten3);
				String head3=headerData3[1].substring(0, (headerData3[1].length()));
				new DeEnCode().decodeMethod(tenderOppexFile,data.get("tenderID").toString(),exten3, head3);
				
				String headerData4[]=data.get("upload_memo_file").toString().split("base64,");
				String extention4[]=data.get("upload_memo_file").toString().split("[/;]");
				String exten4 = extention4[1];
				System.out.println("split------------------->"+exten4);
				String head4=headerData4[1].substring(0, (headerData4[1].length()));
				new DeEnCode().decodeMethod(tenderMemo,data.get("tenderID").toString(),exten4, head4);
				
			} /*
				 * else if (fileName.equals("uploadCappexFile")) {
				 * 
				 * 
				 * }else if (fileName.equals("uploadOppexFile")) {
				 * 
				 * 
				 * }else if (fileName.equals("uploadMemoFile")) {
				 * 
				 * 
				 * }
				 */
			
			
			
			brdPaperUpload.setTenderId(Long.valueOf(data.get("tenderID").toString()));
			brdPaperUpload.setBoardPaperFileName(tenderBoardPaper+data.get("tenderID").toString()+".pdf");
			brdPaperUpload.setCappexFileName(tenderCappexFile+data.get("tenderID").toString()+".pdf");
			brdPaperUpload.setOppexFileName(tenderOppexFile+data.get("tenderID").toString()+".pdf");
			brdPaperUpload.setMemoFileName(tenderMemo+data.get("tenderID").toString()+".pdf");
			brdPaperUpload.setCreatedUser(userId);
			brdPaperUpload.setCreatedDateTime(LocalDateTime.now());
			boardPaperUploadRepo.save(brdPaperUpload);
			msg = "Success";
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Error";
		}
		
		return msg;
	}

	@Override
	public Boolean getWetherBoardPaperExists(Long boardTenderid) {
		return boardPaperUploadRepo.existsByTenderId(boardTenderid);
	}

	@Override
	public JSONObject getRfpDetailsForView(Long id) {
		Optional<RFP> rfp =  rfpRepo.findById(id);
		Optional<ProcurementRequest> pr =  procurementRequestRepo.findById(rfp.get().getPrId());
		Optional<EligibleCategory> ec = eligibleCategoryRepo.findById(Long.valueOf(pr.get().getServiceCategory()));
		JSONObject procReqOb = new JSONObject();
		
		procReqOb.put("department", pr.get().getDepartment());
		procReqOb.put("requester", pr.get().getRequester());
		procReqOb.put("hod", pr.get().getHeadApproval());
		procReqOb.put("name", pr.get().getName());
		procReqOb.put("date", pr.get().getDate());
		procReqOb.put("designation", pr.get().getDesignation());
		procReqOb.put("budgeted", pr.get().getBudgeted());
		procReqOb.put("expenditure", pr.get().getExpenditure());
		procReqOb.put("estimatedProj", pr.get().getProjectCost());
		procReqOb.put("exisitingSupllier", pr.get().getExistingSupplier());
		procReqOb.put("exsitingPrice", pr.get().getExistingPrices());
		procReqOb.put("lastPcApprovedDate", pr.get().getApprovedDate());
		procReqOb.put("type", pr.get().getType());
		procReqOb.put("goodAndService", ec.get().getEligibleCategortName());
		procReqOb.put("biddingPeriod", pr.get().getBiddingPeriod());
		procReqOb.put("staffContactDetails", pr.get().getTechnicalClarifications());
		procReqOb.put("sampleRequirment", pr.get().getSampleRequirement());
		procReqOb.put("paymentTerm", pr.get().getPaymentTerms());
		procReqOb.put("quotationValidPeriod", pr.get().getQuotationValidity());
		procReqOb.put("expectedDelPeriod", pr.get().getExpectedValidity());
		procReqOb.put("warrentyPeriod", pr.get().getWarrantyPeriod());
		procReqOb.put("preBidMeetingReq", pr.get().getMeetingRequirement());
		procReqOb.put("RecommendedVendor", pr.get().getOtherVendors());
		procReqOb.put("note", pr.get().getNotedConsider());
		return procReqOb;
	}

	



	

	/*
	 * @Override public Boolean getWetherProcComExists(String committeeMember, Long
	 * procTenderid) {
	 * 
	 * return procurementCommitteeRepo.existsByUserIdAndTenderId(committeeMember,
	 * procTenderid); }
	 */

		
}
