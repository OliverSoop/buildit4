package ee.ut.model;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ee.ut.soap.client.PlantResource;
import ee.ut.soap.client.PlantResourceList;
import ee.ut.soap.client.PlantSOAPService;
import ee.ut.soap.client.PlantSOAPServiceService;

@RunWith(JUnit4.class)
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



//	@Autowired
//    RequestedPlantDataOnDemand dod;

//	@Test
//    public void testCountRequestedPlants() {
//        Assert.assertNotNull("Data on demand for 'RequestedPlant' failed to initialize correctly", dod.getRandomRequestedPlant());
//        long count = RequestedPlant.countRequestedPlants();
//        Assert.assertTrue("Counter for 'RequestedPlant' incorrectly reported there were no entries", count > 0);
//    }

//	@Test
//    public void testFindRequestedPlant() {
//        RequestedPlant obj = dod.getRandomRequestedPlant();
//        Assert.assertNotNull("Data on demand for 'RequestedPlant' failed to initialize correctly", obj);
//        Long id = obj.getId();
//        Assert.assertNotNull("Data on demand for 'RequestedPlant' failed to provide an identifier", id);
//        obj = RequestedPlant.findRequestedPlant(id);
//        Assert.assertNotNull("Find method for 'RequestedPlant' illegally returned null for id '" + id + "'", obj);
//        Assert.assertEquals("Find method for 'RequestedPlant' returned the incorrect identifier", id, obj.getId());
//    }

//	@Test
//    public void testFindAllRequestedPlants() {
//        Assert.assertNotNull("Data on demand for 'RequestedPlant' failed to initialize correctly", dod.getRandomRequestedPlant());
//        long count = RequestedPlant.countRequestedPlants();
//        Assert.assertTrue("Too expensive to perform a find all test for 'RequestedPlant', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
//        List<RequestedPlant> result = RequestedPlant.findAllRequestedPlants();
//        Assert.assertNotNull("Find all method for 'RequestedPlant' illegally returned null", result);
//        Assert.assertTrue("Find all method for 'RequestedPlant' failed to return any data", result.size() > 0);
//    }

//	@Test
//    public void testFindRequestedPlantEntries() {
//        Assert.assertNotNull("Data on demand for 'RequestedPlant' failed to initialize correctly", dod.getRandomRequestedPlant());
//        long count = RequestedPlant.countRequestedPlants();
//        if (count > 20) count = 20;
//        int firstResult = 0;
//        int maxResults = (int) count;
//        List<RequestedPlant> result = RequestedPlant.findRequestedPlantEntries(firstResult, maxResults);
//        Assert.assertNotNull("Find entries method for 'RequestedPlant' illegally returned null", result);
//        Assert.assertEquals("Find entries method for 'RequestedPlant' returned an incorrect number of entries", count, result.size());
//    }

//	@Test
//    public void testFlush() {
//        RequestedPlant obj = dod.getRandomRequestedPlant();
//        Assert.assertNotNull("Data on demand for 'RequestedPlant' failed to initialize correctly", obj);
//        Long id = obj.getId();
//        Assert.assertNotNull("Data on demand for 'RequestedPlant' failed to provide an identifier", id);
//        obj = RequestedPlant.findRequestedPlant(id);
//        Assert.assertNotNull("Find method for 'RequestedPlant' illegally returned null for id '" + id + "'", obj);
//        boolean modified =  dod.modifyRequestedPlant(obj);
//        Integer currentVersion = obj.getVersion();
//        obj.flush();
//        Assert.assertTrue("Version for 'RequestedPlant' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
//    }

//	@Test
//    public void testMergeUpdate() {
//        RequestedPlant obj = dod.getRandomRequestedPlant();
//        Assert.assertNotNull("Data on demand for 'RequestedPlant' failed to initialize correctly", obj);
//        Long id = obj.getId();
//        Assert.assertNotNull("Data on demand for 'RequestedPlant' failed to provide an identifier", id);
//        obj = RequestedPlant.findRequestedPlant(id);
//        boolean modified =  dod.modifyRequestedPlant(obj);
//        Integer currentVersion = obj.getVersion();
//        RequestedPlant merged = obj.merge();
//        obj.flush();
//        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
//        Assert.assertTrue("Version for 'RequestedPlant' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
//    }

//	@Test
//    public void testPersist() {
//        Assert.assertNotNull("Data on demand for 'RequestedPlant' failed to initialize correctly", dod.getRandomRequestedPlant());
//        RequestedPlant obj = dod.getNewTransientRequestedPlant(Integer.MAX_VALUE);
//        Assert.assertNotNull("Data on demand for 'RequestedPlant' failed to provide a new transient entity", obj);
//        Assert.assertNull("Expected 'RequestedPlant' identifier to be null", obj.getId());
//        try {
//            obj.persist();
//        } catch (final ConstraintViolationException e) {
//            final StringBuilder msg = new StringBuilder();
//            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
//                final ConstraintViolation<?> cv = iter.next();
//                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
//            }
//            throw new IllegalStateException(msg.toString(), e);
//        }
//        obj.flush();
//        Assert.assertNotNull("Expected 'RequestedPlant' identifier to no longer be null", obj.getId());
//    }

//	@Test
//    public void testRemove() {
//        RequestedPlant obj = dod.getRandomRequestedPlant();
//        Assert.assertNotNull("Data on demand for 'RequestedPlant' failed to initialize correctly", obj);
//        Long id = obj.getId();
//        Assert.assertNotNull("Data on demand for 'RequestedPlant' failed to provide an identifier", id);
//        obj = RequestedPlant.findRequestedPlant(id);
//        obj.remove();
//        obj.flush();
//        Assert.assertNull("Failed to remove 'RequestedPlant' with identifier '" + id + "'", RequestedPlant.findRequestedPlant(id));
//    }
}
