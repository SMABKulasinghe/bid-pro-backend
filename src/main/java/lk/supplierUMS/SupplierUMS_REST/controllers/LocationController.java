package lk.supplierUMS.SupplierUMS_REST.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.supplierUMS.SupplierUMS_REST.entity.City;
import lk.supplierUMS.SupplierUMS_REST.entity.District;
import lk.supplierUMS.SupplierUMS_REST.entity.LocationDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.Province;
import lk.supplierUMS.SupplierUMS_REST.service.LocationService;


@CrossOrigin
@RestController
@RequestMapping(value ="/locationdetails")
public class LocationController {

	@Autowired
	LocationService locationService;

	// --------------Get Location Details Using Location ID--------------//
	@GetMapping(value = "/{locationdata}")
	public LocationDetails getlocationdataData(@PathVariable String locationdata) {
		System.out.println("Inside getlocationdataData getlocationdataData.methodFUCKMEEE ------->");
		System.out.println("Inside getlocationdataData.method locationdata----->" + locationdata);
		LocationDetails locationDetails = locationService.getlocationdataData(locationdata);
		return locationDetails;
	}
	
	// ---------Get All locationService Details--------------------//
	@GetMapping
	public List<LocationDetails> getAlllocationDetails() {
		System.out.println("Inside getAlllocationDetails  ------->");
		List<LocationDetails> locationDetails = locationService.getAllLocationData();

		return locationDetails;
	}
	
	@GetMapping(value = "/getprovince")
	public ResponseEntity<List<Province>> getProvinceDetails() {
		
		try {
			List<Province> province = locationService.getProvinceData();
			return new ResponseEntity<List<Province>>(province, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	

	@GetMapping(value = "/getdistrict/{provinceId}")
	public ResponseEntity<List<District>> getDistrictDetails(@PathVariable long provinceId) {
		
		try {
			List<District> district = locationService.getDistrictData(provinceId);
			//return new ResponseEntity<List<Province>>(province, HttpStatus.OK);
			return new ResponseEntity<List<District>>(district, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@GetMapping(value = "/getcity/{districtId}")
	public ResponseEntity<List<City>> getCityDetails(@PathVariable long districtId) {
		
		try {
			List<City> city = locationService.getCityData(districtId);
			//return new ResponseEntity<List<Province>>(province, HttpStatus.OK);
			return new ResponseEntity<List<City>>(city, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}