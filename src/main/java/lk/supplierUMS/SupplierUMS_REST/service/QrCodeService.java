package lk.supplierUMS.SupplierUMS_REST.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.User;

public interface QrCodeService {

	JSONObject getQrCodeTableDetails(HttpServletRequest request);

	JSONObject setQrStatusToPresent(long id, String approve);

	JSONObject setQrStatusToPresentFromQr(long id, String approve);

	JSONObject sendEmailAndGenerateQr(HttpServletRequest request);

	JSONObject getQrCount();

	
}
