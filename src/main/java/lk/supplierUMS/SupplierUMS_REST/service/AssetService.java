package lk.supplierUMS.SupplierUMS_REST.service;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

public interface AssetService {

	String addAsset(JSONObject data, HttpServletRequest request);

	JSONObject getAllAssets(HttpServletRequest request, String parameter);

	String removeAsset(JSONObject data);

	JSONObject getAssetsID(HttpServletRequest request, String parameter);

	String updateAsset(JSONObject data);

	JSONObject getAssetCategory(HttpServletRequest request, String parameter);

	JSONObject getAssetSubCategory(HttpServletRequest request, String parameter);

	JSONObject getAllAssetsForTransfer(HttpServletRequest request, Long category, Long subcategory);

	String transferAsset(JSONObject data);

}
