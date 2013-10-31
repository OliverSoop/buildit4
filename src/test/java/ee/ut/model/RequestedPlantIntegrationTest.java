package ee.ut.model;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import ee.ut.domain.PurchaseOrderStatus;
import ee.ut.rest.PurchaseOrderResource;
import ee.ut.soap.client.PlantResource;
import ee.ut.soap.client.PlantResourceList;
import ee.ut.soap.client.PlantSOAPService;
import ee.ut.soap.client.PlantSOAPServiceService;

@RooIntegrationTest(entity = RequestedPlant.class)
public class RequestedPlantIntegrationTest {

	public PlantResourceList getAllPlants(){
	    PlantSOAPService catalog = new PlantSOAPServiceService().getPlantSOAPServicePort();
		PlantResourceList plants = catalog.getAllPlants();
		return plants;
	}
	
	
	public Boolean createPO(ee.ut.soap.client.PurchaseOrderResource inputPO){
	    PlantSOAPService catalog = new PlantSOAPServiceService().getPlantSOAPServicePort();
	    try {
			catalog.createPurchaseOrder(inputPO);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
    @Test
    public void testGetAllPlants() {
    	List<PlantResource> plants = getAllPlants().getPlants();
    	String result = "";
        for(PlantResource plant : plants) {
        	result = "Name: "+plant.getName() + " Description: " + plant.getDescription() + " Cost: " + plant.getCostPerDay();
        	
        }
        assertTrue(result.equals("Name: Hammer Description: Hammer time Cost: 4.5"));
    }
    
    @Test
    public void testCreatePO() {
    	Date today = new Date();
        //Converting date to XMLGregorianCalendar in Java
        XMLGregorianCalendar xgcal = toXMLGregorianCalendar(today);
    	
    	ee.ut.soap.client.PurchaseOrderResource PO = new ee.ut.soap.client.PurchaseOrderResource();
    	PO.setConstructionSite("Liivi 2");
    	PO.setEndDate(xgcal);
    	PO.setExternalId("1");
    	PO.setPlantId(01L);
    	PO.setPoRecievedDate(xgcal);
    	PO.setReturnDate(xgcal);
    	PO.setSiteEngineer("Engineer");
    	PO.setStartDate(xgcal);
    	PO.setTotalCost(5.5);
        assertTrue(createPO(PO));
    }
    
    public static XMLGregorianCalendar toXMLGregorianCalendar(Date date){
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;
        try {
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        } catch (DatatypeConfigurationException ex) {
            System.out.println("Error on making date");
        }
        return xmlCalendar;
    }


}
