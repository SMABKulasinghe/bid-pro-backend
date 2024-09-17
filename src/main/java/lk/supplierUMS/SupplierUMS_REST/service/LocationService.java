package lk.supplierUMS.SupplierUMS_REST.service;


import java.util.List;

import lk.supplierUMS.SupplierUMS_REST.entity.City;
import lk.supplierUMS.SupplierUMS_REST.entity.District;
import lk.supplierUMS.SupplierUMS_REST.entity.LocationDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.Province;

public interface LocationService {
	
	public LocationDetails getlocationdataData(String locationid);
	public List<LocationDetails> getAllLocationData();
	public List<Province> getProvinceData();
	public List<District> getDistrictData(long provinceId);
	public List<City> getCityData(long districtId);

}
