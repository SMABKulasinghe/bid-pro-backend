package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.CityRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.DistrictRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.LocationDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ProvinceRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.City;
import lk.supplierUMS.SupplierUMS_REST.entity.District;
import lk.supplierUMS.SupplierUMS_REST.entity.LocationDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.Province;
import lk.supplierUMS.SupplierUMS_REST.service.LocationService;



@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class LocationServiceImpl implements LocationService{

	
	@Autowired
	LocationDetailsRepo locationDetailsRepo;
	
	@Autowired
	ProvinceRepo provinceRepo;
	
	@Autowired
	DistrictRepo districtRepo;
	
	@Autowired
	CityRepo cityRepo;

	@Override
	public LocationDetails getlocationdataData(String locationid) {
		System.out.println("Inside getlocationdataData Class getlocationdataData.method------->");
		
		LocationDetails locationDetails = locationDetailsRepo.findByLocationcode(locationid);
	
		return locationDetails;
	}

	@Override
	public List<LocationDetails> getAllLocationData() {
		// TODO Auto-generated method stub
		return locationDetailsRepo.findAll();
	}

	@Override
	public List<Province> getProvinceData() {
		try {
			List<Province> pro= provinceRepo.findAll();
			return pro;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<District> getDistrictData(long provinceId) {
		try {
			List<District> district = districtRepo.findByProvinceCode(provinceId);
			return district;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<City> getCityData(long districtId) {
		try {
			List<City> city = cityRepo.findByDistrictCode(districtId);
			return city;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

}