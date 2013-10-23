package ee.ut.rest.controller;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.Collections;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import ee.ut.domain.PlantHireRequestStatus;
import ee.ut.domain.PurchaseOrderStatus;
import ee.ut.model.ConstructionSite;
import ee.ut.model.PlantHireRequest;
import ee.ut.model.PurchaseOrder;
import ee.ut.model.RequestedPlant;
import ee.ut.model.SiteEngineer;
import ee.ut.model.Supplier;
import ee.ut.repository.PurchaseOrderRepository;
import ee.ut.rest.PlantHireRequestResource;
import ee.ut.rest.PlantHireRequestResourceAssembler;
import ee.ut.rest.PurchaseOrderResource;
import ee.ut.rest.RequestedPlantResource;
import ee.ut.service.PlantHireRequestService;
import ee.ut.service.PlantHireRequestService.ConstructionSiteNotFoundException;
import ee.ut.util.ExtendedLink;

@Controller
@RequestMapping("/rest/phr")
public class PlantHireReqController {
	
	@Autowired PurchaseOrderRepository purchaseOrderRepository;
	@Autowired PlantHireRequestService phrService;

	@RequestMapping
	(method = RequestMethod.POST, value ="")
	public ResponseEntity<Void> createPlantHireRequest(@RequestBody PlantHireRequestResource phr) throws ConstructionSiteNotFoundException {
		PlantHireRequest plantHireRequest;
		plantHireRequest = phrService.createPHR(phr);
		HttpHeaders headers = new HttpHeaders();
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().
					pathSegment(plantHireRequest.getId().toString()).build().toUri();
		headers.setLocation(location);
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@ExceptionHandler({ConstructionSiteNotFoundException.class})
	public ResponseEntity<String> handleBadRequest(Exception e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping
	(method = RequestMethod.POST, value ="/{id}")
	public ResponseEntity<Void> updatePlantHireRequest(@RequestBody PlantHireRequestResource phr, @PathVariable Long id) {
		PlantHireRequest phrToBeChanged = PlantHireRequest.findPlantHireRequest(id);
		if (phrToBeChanged != null) {
			if (phrToBeChanged.getStatus() == PlantHireRequestStatus.PENDING_CONFIRMATION ||
					phrToBeChanged.getStatus() == PlantHireRequestStatus.REJECTED) {
				PlantHireRequest plantHireRequest = phrToBeChanged;
				plantHireRequest.setStartDate(phr.getStartDate());
				plantHireRequest.setEndDate(phr.getEndDate());
				plantHireRequest.setTotalCost(phr.getTotalCost());
				plantHireRequest.setStatus(PlantHireRequestStatus.PENDING_CONFIRMATION);
				
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
				plant.setSupplier(supplier);
				
				plantHireRequest.setRequestedPlant(plant);
				
				HttpHeaders headers = new HttpHeaders();
				URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().
						 build().toUri();
				headers.setLocation(location);
				ResponseEntity<Void> response = new ResponseEntity<Void>(headers, HttpStatus.CREATED);
				return response;
				
			}
			
			return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		 
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	 
	@RequestMapping
	(method = RequestMethod.DELETE, value ="/{id}/cancel")
	public ResponseEntity<Void> cancelPHR(@PathVariable Long id) {
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		if (phr != null) {
			if (phr.getStatus() == PlantHireRequestStatus.REJECTED ||
					phr.getStatus() == PlantHireRequestStatus.PENDING_CONFIRMATION) {
				phr.setStatus(PlantHireRequestStatus.CANCELLED);
				return new ResponseEntity<Void>(HttpStatus.OK);
			} else if (phr.getStatus() == PlantHireRequestStatus.OPEN) {
				PurchaseOrder po = purchaseOrderRepository.findByPHR(phr);
				if (po != null) {
					//TODO send cancellation request
					phr.setStatus(PlantHireRequestStatus.CANCELLED);
					return new ResponseEntity<Void>(HttpStatus.OK);
				} else {
					//TODO Should check if date before delivery
					phr.setStatus(PlantHireRequestStatus.CANCELLED);
					return new ResponseEntity<Void>(HttpStatus.OK);
				}
			}
			return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping
	(method = RequestMethod.GET, value ="/{id}")
	public ResponseEntity<PlantHireRequestResource> getPlantHireRequest(@PathVariable Long id) throws NoSuchMethodException, SecurityException {
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		if (phr != null) {
			PlantHireRequestResourceAssembler assembler = new PlantHireRequestResourceAssembler();
			PlantHireRequestResource plantHireRequest = assembler.toResource(phr);
			
			
			switch (phr.getStatus()) {
				case PENDING_CONFIRMATION:
					Method method1 = PlantHireReqController.class.getMethod("rejectPHR", Long.class);
					String link1 = linkTo(method1, phr.getId()).toUri().toString();
					plantHireRequest.add(new ExtendedLink(link1, "rejectPHR", "DELETE"));
					Method method2 = PlantHireReqController.class.getMethod("acceptPHR", Long.class);
					String link2 = linkTo(method2, phr.getId()).toUri().toString();
					plantHireRequest.add(new ExtendedLink(link2, "acceptPHR", "POST"));
				case REJECTED:
				case OPEN:
					Method method3 = PlantHireReqController.class.getMethod("cancelPHR", Long.class);
					String link3 = linkTo(method3, phr.getId()).toUri().toString();
					plantHireRequest.add(new ExtendedLink(link3, "cancelPHR", "DELETE"));
					break;
				default:
				
			}
			return new ResponseEntity<PlantHireRequestResource>(plantHireRequest, HttpStatus.OK);
		}
		return new ResponseEntity<PlantHireRequestResource>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping
	(method = RequestMethod.DELETE, value ="/{id}/reject")
	public ResponseEntity<Void> rejectPHR(@PathVariable Long id) {
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		if (phr != null) {
			if (phr.getStatus() == PlantHireRequestStatus.PENDING_CONFIRMATION) {
				phr.setStatus(PlantHireRequestStatus.REJECTED);
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
			return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping
	(method = RequestMethod.POST, value ="/{id}/accept")
	public ResponseEntity<Void> acceptPHR(@PathVariable Long id) {
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		if (phr != null) {
			if (phr.getStatus() == PlantHireRequestStatus.PENDING_CONFIRMATION) {
				phr.setStatus(PlantHireRequestStatus.OPEN);
				PurchaseOrder po = new PurchaseOrder();
				po.setPlantHireRequest(phr);
				po.setStatus(PurchaseOrderStatus.CREATED);
				po.setDateCreated(new Date());
				po.persist();
				
				if(submitPO(po)){
					return new ResponseEntity<Void>(HttpStatus.OK);
				}
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	public boolean submitPO(PurchaseOrder po) {
		ClientResponse response = createPurchaseOrderResource(po);
		return (response.getStatus() == ClientResponse.Status.CREATED
				.getStatusCode());
	}


	private ClientResponse createPurchaseOrderResource(PurchaseOrder po) {
		String DOMAIN_URL = "http://rentit4.herokuapp.com/";

		Client client = Client.create();
		WebResource webResource = client.resource(DOMAIN_URL
				+ "rest/purchaseorders");

		PurchaseOrderResource por = new PurchaseOrderResource();

		por.setExternalID(po.getId().toString());
		por.setPlantID(po.getPlantHireRequest().getRequestedPlant().getId());
		por.setStartDate(po.getPlantHireRequest().getStartDate());
		por.setEndDate(po.getPlantHireRequest().getEndDate());
		por.setConstructionSite(po.getPlantHireRequest().getConstructionSite().getName());
		por.setSiteEngineer(po.getPlantHireRequest().getSiteEngineer().getName());
		por.setTotalCost(po.getPlantHireRequest().getTotalCost());
		por.setPOrecievedDate(po.getDateCreated());
		por.setStatus(po.getStatus());
		por.setReturnDate(po.getPlantHireRequest().getEndDate());

		
		return webResource.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, por);
	}
}
