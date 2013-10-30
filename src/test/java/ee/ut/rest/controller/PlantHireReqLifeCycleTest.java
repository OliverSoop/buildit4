package ee.ut.rest.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import ee.ut.rest.ConstructionSiteResource;
import ee.ut.rest.PlantHireRequestResource;
import ee.ut.rest.PurchaseOrderResource;
import ee.ut.rest.RequestedPlantResource;
import ee.ut.rest.SiteEngineerResource;
import ee.ut.rest.SupplierResource;

@RunWith(JUnit4.class)
public class PlantHireReqLifeCycleTest {
	
	private static String DOMAIN_URL = "http://buildit4.herokuapp.com/";
	private static String DOMAIN_URL2 = "http://rentit4.herokuapp.com/";

	@Test
	public void testPlantHireReqLifeCycle(){
		//Create a Plant Hire Request using the RESTful service in Heroku
		ClientResponse response = createPlantHireRequestResource();
    	assertTrue(response.getStatus() == ClientResponse.Status.CREATED.getStatusCode());
    	
    	//Obtain the Plant Hire Request resource (PHRresource) from the response of previous point.
    	URI location = response.getLocation();
    	response= getPlantHireRequestResource(location);
    	assertTrue(response.getStatus() == ClientResponse.Status.OK.getStatusCode());
    		
    	//Approve the obtained Plant Hire Request and 
    	response= acceptPlantHireRequestResource(location);
    	assertTrue(response.getStatus() == ClientResponse.Status.OK.getStatusCode());
    	
    	//Obtain the Purchase Order resource (POresource) from the response of previous point.
    	PurchaseOrderResource por = getReturnedPOResource(response);
    	
    	//Query the Purchase Order resource by calling the URL in the Hyperlink contained in POresource.
    	response= getPOResource(por.getId().toString());
    	assertTrue(response.getStatus() == ClientResponse.Status.OK.getStatusCode());
    	//Assert that the representation obtained in previous point is not null
    	assertNotNull(response);
    	
	}
	
	private ClientResponse createPlantHireRequestResource(){
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
	
	private ClientResponse getPlantHireRequestResource(URI location) {
		Client client = Client.create();
    	WebResource webResource = client.resource(location);
    	return webResource.type(MediaType.APPLICATION_XML)
												.accept(MediaType.APPLICATION_XML)
												.get(ClientResponse.class);
    }

	private ClientResponse acceptPlantHireRequestResource(URI location){
		
		Client client = Client.create();
		WebResource webResource = client.resource(location.toString() + "/accept");
		return  webResource.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class);
	}

	private ClientResponse getPOResource(String por_Id){
		
		Client client = Client.create();
    	WebResource webResource = client.resource(DOMAIN_URL2 + "rest/purchaseorders/" + por_Id);
	
    	return webResource.type(MediaType.APPLICATION_XML)
												.accept(MediaType.APPLICATION_XML)
												.get(ClientResponse.class);
	}
	
	private PurchaseOrderResource getReturnedPOResource(ClientResponse response){
		
		JAXBContext context;
		PurchaseOrderResource por = null;
		try {
			context = JAXBContext.newInstance(PurchaseOrderResource.class);
			Unmarshaller um = context.createUnmarshaller();
			por = (PurchaseOrderResource) um.unmarshal(response.getEntityInputStream());
		} catch (JAXBException e) {
			// TODO add code to this part
		}
		
		return por;
		
	}
}