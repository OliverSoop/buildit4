package ee.ut.rest.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.hateoas.Link;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import ee.ut.rest.ConstructionSiteResource;
import ee.ut.rest.PlantHireRequestResource;
import ee.ut.rest.PlantResource;
import ee.ut.rest.PurchaseOrderResource;
import ee.ut.rest.RequestedPlantResource;
import ee.ut.rest.SiteEngineerResource;
import ee.ut.rest.SupplierResource;

@RunWith(JUnit4.class)
public class PlantHireReqLifeCycleTest {
	
	private static String DOMAIN_URL = "http://buildit4.herokuapp.com/";
	private static String DOMAIN_URL2 = "http://rentit4.herokuapp.com/";

	@Test
	public void testPlantHireReqLifeCycle() throws IOException{
		//Create a Plant Hire Request using the RESTful service in Heroku
		ClientResponse response = createPlantHireRequestResource();
    	assertTrue(response.getStatus() == ClientResponse.Status.CREATED.getStatusCode());
    	
    	//Obtain the Plant Hire Request resource (PHRresource) from the response of previous point.
    	URI location = response.getLocation();
    	response = getPlantHireRequestResource(location);
    	assertTrue(response.getStatus() == ClientResponse.Status.OK.getStatusCode());
    	PlantHireRequestResource re = response.getEntity(PlantHireRequestResource.class);
    	Link acceptLink = re.get_link("acceptPHR");
    	
    	//Approve the obtained Plant Hire Request and
    	response= acceptPlantHireRequestResource(acceptLink);
    	assertTrue(response.getStatus() == ClientResponse.Status.OK.getStatusCode());
    	System.out.println("Vla" + response.getStatus());
    	System.out.println("T" + response.getLength());
    	StringWriter writer = new StringWriter();
    	IOUtils.copy(response.getEntityInputStream(), writer, "UTF-8");
    	String theString = writer.toString();
    	System.out.println(theString + "\n\n");
    	
    	//Obtain the Purchase Order resource (POresource) from the response of previous point.
    	PurchaseOrderResource por = response.getEntity(PurchaseOrderResource.class);
    	
    	assertNotNull(por);
    	//Query the Purchase Order resource by calling the URL in the Hyperlink contained in POresource.
    	response= getPOResource(por.get_link("self"));
    	assertTrue(response.getStatus() == ClientResponse.Status.OK.getStatusCode());
    	//Assert that the representation obtained in previous point is not null
    	assertNotNull(response);
    	
	}
	
	private ClientResponse createPlantHireRequestResource(){
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("provider", "admin"));
    	WebResource webResource = client.resource(DOMAIN_URL + "/rest/phr");
    	
    	PlantHireRequestResource phr = new PlantHireRequestResource();
    	phr.setStartDate(new Date());
    	phr.setEndDate(new Date());
    	phr.setTotalCost(40.2F);
    	phr.setProviderURL(DOMAIN_URL2);
    	
    	ConstructionSiteResource site = new ConstructionSiteResource();
    	site.setLocation("Neighbour");
    	site.setName("Construction site");
    	phr.setConstructionSite(site);
    	
    	SiteEngineerResource engineer = new SiteEngineerResource();
    	engineer.setName("Engineer");
    	phr.setSiteEngineer(engineer);
    	
    	RequestedPlantResource rpr = new RequestedPlantResource();
    	rpr.setDescription("Excavator");
    	rpr.setExternalId(Long.toString(createPlant()));
    	
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
		client.addFilter(new HTTPBasicAuthFilter("provider", "admin"));
    	WebResource webResource = client.resource(location);
    	return webResource.type(MediaType.APPLICATION_XML)
												.accept(MediaType.APPLICATION_XML)
												.get(ClientResponse.class);
    }

	private ClientResponse acceptPlantHireRequestResource(Link location){
		
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("provider", "admin"));
		WebResource webResource = client.resource(location.getHref());
		return  webResource.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class);
	}

	private ClientResponse getPOResource(Link porLink){
		
		Client client = Client.create();
    	WebResource webResource = client.resource(porLink.getHref());
	
    	return webResource.type(MediaType.APPLICATION_XML)
												.accept(MediaType.APPLICATION_XML)
												.get(ClientResponse.class);
	}
	
	private Long createPlant() {
		PlantResource plantResource = new PlantResource();
		plantResource.setCostPerDay(2.0d);
		plantResource.setDescription("Excavator");
		plantResource.setName("Excavator");
		Client client = Client.create();
    	client.addFilter(new HTTPBasicAuthFilter("employee", "employee"));
    	WebResource webResource = client.resource(DOMAIN_URL2 + "rest/plant/");
    	
    	ClientResponse res = webResource.type(MediaType.APPLICATION_XML)
					.accept(MediaType.APPLICATION_XML)
					.post(ClientResponse.class, plantResource);
    	URI path = res.getLocation();
    	return Long.valueOf(path.getRawPath().substring(path.getRawPath().lastIndexOf('/') + 1));
	}
}