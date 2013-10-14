package ee.ut.rest.controller;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import ee.ut.rest.ConstructionSiteResource;
import ee.ut.rest.PlantHireRequestResource;
import ee.ut.rest.RequestedPlantResource;
import ee.ut.rest.SiteEngineerResource;
import ee.ut.rest.SupplierResource;

@RunWith(JUnit4.class)
public class PlantHireReqControllerTest {
	
	private static String DOMAIN_URL = "http://buildit4.heroku.com/";
	

    @Test
    public void testCreatePHR() {
    	ClientResponse response = createPlantHireRequestResource();
    	assertTrue(response.getStatus() == ClientResponse.Status.CREATED.getStatusCode());
    }
    
    @Test
    public void testDeletePHR() {
    	ClientResponse response = createPlantHireRequestResource();
    	URI uri = response.getLocation();

    	Client client = Client.create();
    	WebResource webResource = client.resource(uri);
    	ClientResponse deleteResponse = webResource.type(MediaType.APPLICATION_XML)
										.accept(MediaType.APPLICATION_XML)
										.delete(ClientResponse.class);
    	assertTrue(deleteResponse.getStatus() == ClientResponse.Status.NO_CONTENT.getStatusCode());
    }
    
    @Test
    public void testGetPHR() {
    	ClientResponse response = createPlantHireRequestResource();
    	URI location = response.getLocation();
    	assertTrue(getPlantHireRequestResource(location).getStatus() == ClientResponse.Status.OK.getStatusCode());
    }
    
    private ClientResponse getPlantHireRequestResource(URI location) {
    	Client client = Client.create();
    	WebResource webResource = client.resource(location);
    	return webResource.type(MediaType.APPLICATION_XML)
												.accept(MediaType.APPLICATION_XML)
												.get(ClientResponse.class);
    }
    
    private ClientResponse createPlantHireRequestResource() {
    	Client client = Client.create();
    	WebResource webResource = client.resource(DOMAIN_URL + "/rest/phr");
    	
    	PlantHireRequestResource phr = new PlantHireRequestResource();
    	phr.setStartDate(new Date());
    	phr.setEndDate(new Date());
    	phr.setTotalCost(40.2F);
    	
    	ConstructionSiteResource site = new ConstructionSiteResource();
    	site.setLocation("Neighbour");
    	site.setName("Construction site");
    	phr.setConstructionSite(site);
    	
    	SiteEngineerResource engineer = new SiteEngineerResource();
    	engineer.setName("Engineer");
    	phr.setSiteEngineer(engineer);
    	
    	RequestedPlantResource rpr = new RequestedPlantResource();
    	rpr.setDescription("Excavator");
    	rpr.setExternalId("C0001");
    	
    	SupplierResource supplier = new SupplierResource();
    	supplier.setName("Toshiba");
    	rpr.setSupplier(supplier);
    	phr.setRequestedPlant(rpr);
    	return  webResource.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, phr);
    }
    
}
