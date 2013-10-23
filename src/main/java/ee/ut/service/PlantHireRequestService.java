package ee.ut.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.ut.domain.PlantHireRequestStatus;
import ee.ut.model.ConstructionSite;
import ee.ut.model.PlantHireRequest;
import ee.ut.model.RequestedPlant;
import ee.ut.model.SiteEngineer;
import ee.ut.model.Supplier;
import ee.ut.repository.ConstructionSiteRepository;
import ee.ut.rest.PlantHireRequestResource;
import ee.ut.rest.RequestedPlantResource;

@Service
public class PlantHireRequestService {
	@Autowired
	private ConstructionSiteRepository csRep;

	public PlantHireRequest createPHR(PlantHireRequestResource phr) throws ConstructionSiteNotFoundException {
//		List<ConstructionSite> csSites = csRep.findByNameAndLocation(phr.getConstructionSite().getName(), phr.getConstructionSite().getLocation());
//		if (!csSites.isEmpty()) {
			PlantHireRequest plantHireRequest = new PlantHireRequest();
			plantHireRequest.setStartDate(phr.getStartDate());
			plantHireRequest.setEndDate(phr.getEndDate());
			plantHireRequest.setTotalCost(phr.getTotalCost());
			plantHireRequest.setStatus(PlantHireRequestStatus.PENDING_CONFIRMATION);
//			plantHireRequest.setConstructionSite(csSites.get(0));
			
			ConstructionSite site = new ConstructionSite();
			site.setName(phr.getConstructionSite().getName());
			site.setLocation(phr.getConstructionSite().getLocation());
			site.persist();
			
			plantHireRequest.setConstructionSite(site);
			
			//TODO SiteEngineer should be retrieved from DB as well
			SiteEngineer siteEngineer = new SiteEngineer();
			siteEngineer.setName(phr.getSiteEngineer().getName());
			siteEngineer.persist();
			
			plantHireRequest.setSiteEngineer(siteEngineer);
			
			//TODO Probably should have this in the DB already as well
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
			return plantHireRequest;
//		} else {
//			throw new ConstructionSiteNotFoundException("Construction site not found");
//		}
	}
	
	
	@SuppressWarnings("serial")
	public class ConstructionSiteNotFoundException extends Exception {
		public ConstructionSiteNotFoundException(String message) {super(message);}
	}
}
