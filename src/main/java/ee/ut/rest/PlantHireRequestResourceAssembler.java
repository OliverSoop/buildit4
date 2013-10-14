package ee.ut.rest;

import ee.ut.model.PlantHireRequest;

public class PlantHireRequestResourceAssembler {

	public static PlantHireRequestResource create(PlantHireRequest phr) {
		PlantHireRequestResource plantHireRequest = new PlantHireRequestResource();
		plantHireRequest.setStartDate(phr.getStartDate());
		plantHireRequest.setEndDate(phr.getEndDate());
		plantHireRequest.setTotalCost(phr.getTotalCost());
		
		ConstructionSiteResource constructionSite = new ConstructionSiteResource();
		constructionSite.setLocation(phr.getConstructionSite().getLocation());
		constructionSite.setName(phr.getConstructionSite().getName());
		plantHireRequest.setConstructionSite(constructionSite);
		
		RequestedPlantResource rpr = new RequestedPlantResource();
		rpr.setDescription(phr.getRequestedPlant().getDescription());
		rpr.setExternalId(phr.getRequestedPlant().getExternalId());
		SupplierResource supplier = new SupplierResource();
		supplier.setName(phr.getRequestedPlant().getSupplier().getName());
		rpr.setSupplier(supplier);
		plantHireRequest.setRequestedPlant(rpr);
		
		
		SiteEngineerResource siteEngineer = new SiteEngineerResource();
		siteEngineer.setName(phr.getSiteEngineer().getName());
		plantHireRequest.setSiteEngineer(siteEngineer);
		
		return plantHireRequest;
	}
}
