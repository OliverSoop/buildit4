package ee.ut.web;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ee.ut.beans.PlantBean;
import ee.ut.beans.PlantHireRequestBean;
import ee.ut.beans.PurchaseOrderBean;
import ee.ut.domain.PlantHireRequestStatus;
import ee.ut.domain.PurchaseOrderStatus;
import ee.ut.model.RequestedPlant;
import ee.ut.soap.client.PlantResource;
import ee.ut.soap.client.PlantResourceList;
import ee.ut.soap.client.PlantSOAPService;
import ee.ut.soap.client.PlantSOAPServiceService;

@RequestMapping("/purchaseorders/newPO/**")
@Controller
public class POController {

    @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping(method = RequestMethod.GET, value ="search")
    public String getPlants (ModelMap modelMap){
    	addDateTimeFormatPatterns(modelMap);
    	modelMap.put("plantquery", new PlantBean());
    	return "purchaseorders/newPO/search";
    }
    //VIEW WITH DATES
    void addDateTimeFormatPatterns(ModelMap modelMap) {
    	modelMap.put("plantHRBean_startr_date_format",
    	DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    	modelMap.put("plantHRBean_endr_date_format",
    	DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }
    //QUERY PLANTS
    @RequestMapping(method = RequestMethod.POST, value="search")
    public String getPlants(@Valid PlantBean bean, Model uiModel, HttpServletRequest request){
    	Calendar startDate = Calendar.getInstance();
    	if (bean.getStartDate() != null) {
    		startDate.setTime(bean.getStartDate());
    	}
    	Calendar endDate = Calendar.getInstance();
    	if (bean.getEndDate() != null) {
    		endDate.setTime(bean.getEndDate());
    	}
    	List<PlantResource> plants = getAvailablePlants(bean.getStartDate(), bean.getEndDate());
    	uiModel.addAttribute("plantList", plants);
    	uiModel.addAttribute("plantquery", bean);
    	return "purchaseorders/newPO/list";
    }
    
    public static List<PlantResource> getAvailablePlants(Date sDate, Date eDate){
		PlantSOAPService catalog = new PlantSOAPServiceService().getPlantSOAPServicePort();
		XMLGregorianCalendar startDate = toXMLGregorianCalendar(sDate);
		XMLGregorianCalendar endDate = toXMLGregorianCalendar(eDate);
		PlantResourceList queryPlants = catalog.getAvailablePlants(startDate,endDate);
		List<PlantResource> plantsList = queryPlants.getPlants();
	
		return plantsList;
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
    
    @RequestMapping(method = RequestMethod.POST, value ="create")
    public String create(@Valid PlantBean pBean, Model uiModel, HttpServletRequest request){
    	RequestedPlant rPlant = new RequestedPlant();
    	rPlant.setExternalId(Long.toString(pBean.getSelectedPlantId()));
    	rPlant.setDescription(pBean.getDescription());
    	rPlant.persist();
    	
    	PlantHireRequestBean pHRBean = new PlantHireRequestBean();
    	pHRBean.setStartDate(pBean.getStartDate());
    	pHRBean.setEndDate(pBean.getEndDate());
    	pHRBean.setTotalCost(calcTotalcost(pBean.getStartDate(),pBean.getEndDate(), pBean.getCostPerDay()));
    	pHRBean.setRequestedPlant(rPlant);
    	pHRBean.setStatus(PlantHireRequestStatus.OPEN);
    	
    	PurchaseOrderBean pOBean = new PurchaseOrderBean();
    	pOBean.setStatus(PurchaseOrderStatus.CREATED);
    	
    	uiModel.addAttribute("plantHireRequest", pHRBean);
    	uiModel.addAttribute("po", pOBean);
    	uiModel.addAttribute("plantquery", pBean);
    	return "purchaseorders/newPO/create";
    }
    
    public Double calcTotalcost(Date sDate, Date eDate, Double costPerDay){
    	int days = Days.daysBetween(new DateTime(sDate), new DateTime(eDate)).getDays();
    	Double totalCost = days * costPerDay;
    	return totalCost;
    }
}
