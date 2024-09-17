package lk.supplierUMS.SupplierUMS_REST.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.User;

public interface ProcurementService {

	List<TenderDetails> getTendersForProcCommitteCreation();

	List<User> getTendersForProcCommitteeMembers();

	String addProcurementCommittee(JSONObject data, HttpServletRequest request);

	JSONObject getVotingDetailsForVoting(HttpServletRequest request, Long id);

	//JSONObject voteToSupplier(JSONObject data, String voted, long tenderid, long supplierId);

	List<SubCompany> getSuppliersForProcCommitteeMembers();

	JSONObject getVotingDetails(HttpServletRequest request, Long id);

	Boolean getWetherProcComExists(String committeeMember, Long procTenderid);

	String boardPaperSubmit(JSONObject data, HttpServletRequest request, String fileName);

	Boolean getWetherBoardPaperExists(Long boardTenderid);

	JSONObject getRfpDetailsForView(Long id);

	JSONObject voteToSupplier(JSONObject data, long supplierId);

	
}
