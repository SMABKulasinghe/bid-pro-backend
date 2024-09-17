package lk.supplierUMS.SupplierUMS_REST.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

public interface UserActivityLogService {

	void createLogEntry(String id, String roleid,HttpServletRequest request, String location, String action);

	JSONObject getlogentry(Date fromDate, Date toDate, String type, String userid, String action,
			HttpServletRequest request);
	

}
