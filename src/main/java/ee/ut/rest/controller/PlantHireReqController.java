package ee.ut.rest.controller;

import java.net.URI;
import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ee.ut.model.ConstructionSite;
import ee.ut.model.PlantHireRequest;
import ee.ut.model.RequestedPlant;
import ee.ut.model.SiteEngineer;
import ee.ut.model.Supplier;
import ee.ut.rest.PlantHireRequestResource;
import ee.ut.rest.PlantHireRequestResourceAssembler;
import ee.ut.rest.RequestedPlantResource;

@Controller
@RequestMapping("/rest/phr")
public class PlantHireReqController {

	@RequestMapping
	(method = RequestMethod.POST, value ="")
	public ResponseEntity<Void> createPlantHireRequest(@RequestBody PlantHireRequestResource phr) {
		PlantHireRequest plantHireRequest = new PlantHireRequest();
		plantHireRequest.setStartDate(phr.getStartDate());
		plantHireRequest.setEndDate(phr.getEndDate());
		plantHireRequest.setTotalCost(phr.getTotalCost());
		
		ConstructionSite constructionSite = new ConstructionSite();
		constructionSite.setLocation(phr.getConstructionSite().getLocation());
		constructionSite.setName(phr.getConstructionSite().getName());
		constructionSite.persist();
		
		SiteEngineer siteEngineer = new SiteEngineer();
		siteEngineer.setName(phr.getSiteEngineer().getName());
		constructionSite.setSiteEngineers(Collections.singleton(siteEngineer));
		siteEngineer.persist();
		
		plantHireRequest.setConstructionSite(constructionSite);
		plantHireRequest.setSiteEngineer(siteEngineer);
		
		RequestedPlantResource requestedPlant = phr.getRequestedPlant();
		RequestedPlant plant = new RequestedPlant();
		plant.setDescription(requestedPlant.getDescription());
		plant.setExternalId(requestedPlant.getExternalId());
		
		Supplier supplier = new Supplier();
		supplier.setName(requestedPlant.getSupplier().getName());
		supplier.persist();
		plant.setSupplier(supplier);
		plant.persist();
		
		plantHireRequest.setRequestedPlant(plant);
		
		plantHireRequest.persist();
		 
		
		HttpHeaders headers = new HttpHeaders();
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().
				 pathSegment(plantHireRequest.getId().toString()).build().toUri();
		headers.setLocation(location);
		ResponseEntity<Void> response = new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		return response;
	}
	 
	@RequestMapping
	(method = RequestMethod.POST, value ="/{phr.id}")
	public ResponseEntity<Void> updatePlantHireRequest(@RequestBody PlantHireRequestResource phr) {
		
		 
		 
		 HttpHeaders headers = new HttpHeaders();
		 ResponseEntity<Void> response = new ResponseEntity<>(headers, HttpStatus.CREATED);
		 return response;
	}
	 
	@RequestMapping
	(method = RequestMethod.DELETE, value ="/{id}")
	public ResponseEntity<Void> deletePlantHireRequest(@PathVariable Long id) {
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		if (phr != null) {
			phr.remove();
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		 
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping
	(method = RequestMethod.GET, value ="/{id}")
	public ResponseEntity<PlantHireRequestResource> getPlantHireRequest(@PathVariable Long id) {
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		if (phr != null) {
			PlantHireRequestResource plantHireRequest = PlantHireRequestResourceAssembler.create(phr);
			return new ResponseEntity<PlantHireRequestResource>(plantHireRequest, HttpStatus.OK);
			
		}
		return new ResponseEntity<PlantHireRequestResource>(HttpStatus.NOT_FOUND);
	}
}
