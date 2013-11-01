package ee.ut.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import ee.ut.model.PlantHireRequest;
import ee.ut.rest.controller.PlantHireReqController;
import ee.ut.soap.client.PlantResource;

public class PlantHireRequestResourceAssembler extends ResourceAssemblerSupport<PlantHireRequest, PlantHireRequestResource>{
	
	public PlantHireRequestResourceAssembler() {
		super(PlantHireReqController.class, PlantHireRequestResource.class);
	}

	public static PlantHireRequestResource create(PlantHireRequest phr) throws NoSuchMethodException, SecurityException {
		PlantHireRequestResource plantHireRequest = new PlantHireRequestResource();
		plantHireRequest.setStartDate(phr.getStartDate());
		plantHireRequest.setEndDate(phr.getEndDate());
		plantHireRequest.setTotalCost(phr.getTotalCost());
		plantHireRequest.setStatus(phr.getStatus().name());
		
		ConstructionSiteResourceAssembler csAssembler = new ConstructionSiteResourceAssembler();
		csAssembler.toResource(phr.getConstructionSite());
		
		plantHireRequest.setConstructionSite(csAssembler.toResource(phr.getConstructionSite()));
		
		RequestedPlantResource rpr = new RequestedPlantResource();
		rpr.setDescription(phr.getRequestedPlant().getDescription());
		rpr.setExternalId(phr.getRequestedPlant().getExternalId());
		SupplierResource supplier = new SupplierResource();
		supplier.setName(phr.getRequestedPlant().getSupplier().getName());
		rpr.setSupplier(supplier);
		plantHireRequest.setRequestedPlant(rpr);
		
		SiteEngineerResourceAssembler seAssembler = new SiteEngineerResourceAssembler();
		seAssembler.toResource(phr.getSiteEngineer());
		plantHireRequest.setSiteEngineer(seAssembler.toResource(phr.getSiteEngineer()));
		
		return plantHireRequest;
	}

	@Override
	public PlantHireRequestResource toResource(PlantHireRequest phr) {
		PlantHireRequestResource phrr = createResourceWithId(phr.getId(), phr);
		phrr.setStartDate(phr.getStartDate());
		phrr.setEndDate(phr.getEndDate());
		phrr.setTotalCost(phr.getTotalCost());
		
		//vaja teha veel
		if (phr.getRequestedPlant()!=null){
			
			RequestedPlantResource rpr;
			
		}
		
		return null;
	}
}
