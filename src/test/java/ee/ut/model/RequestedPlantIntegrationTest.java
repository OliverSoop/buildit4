package ee.ut.model;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

import com.sun.jersey.api.client.ClientResponse;

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
	
    @Test
    public void testGetAllPlants() {
    	List<PlantResource> plants = getAllPlants().getPlants();
    	
        for(PlantResource plant : plants) {
        	String result = "Name: "+plant.getName() + " Description: " + plant.getDescription() + " Cost: " + plant.getCostPerDay();
        	assertTrue(result.equals("Name: Hammer Description: Hammer time Cost: 4.5"));
        }
    }
    


}
