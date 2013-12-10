package ee.ut.web.se;
import ee.ut.beans.PurchaseOrderBean;
import ee.ut.beans.PurchaseOrderExtensionBean;
import ee.ut.domain.PurchaseOrderStatus;
import ee.ut.model.PlantHireRequest;
import ee.ut.model.PurchaseOrder;
import ee.ut.repository.PurchaseOrderRepository;
import ee.ut.rest.PurchaseOrderResource;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

@RequestMapping("/se/po")
@Controller
@RooWebScaffold(path = "se/po", formBackingObject = PurchaseOrder.class, create = false)
public class POSEController {

	@Autowired
    PurchaseOrderRepository purchaseOrderRepository;

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("purchaseorder", purchaseOrderRepository.findOne(id));
        uiModel.addAttribute("itemId", id);
        return "se/po/show";
    }
	
	@RequestMapping(value = "/{id}/request_extension", produces = "text/html")
    public String request_extension(@PathVariable("id") Long id, Model uiModel) {
		PurchaseOrder po = purchaseOrderRepository.findOne(id);
		PurchaseOrderExtensionBean bean = new PurchaseOrderExtensionBean();
		PlantHireRequest phr = po.getPlantHireRequest();
		bean.setId(po.getId());
		bean.setVersion(po.getVersion());
		bean.setDateCreated(po.getDateCreated());
		bean.setExternalID(po.getExternalID());
		bean.setEndDate(phr.getEndDate());
		bean.setStartDate(phr.getStartDate());
		bean.setStatus(po.getStatus());
        uiModel.addAttribute("purchaseOrder", bean);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("purchaseorderstatuses", Arrays.asList(PurchaseOrderStatus.values()));
        
        return "se/po/request_extension";
    }
	

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("purchaseorders", purchaseOrderRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / sizeNo, sizeNo)).getContent());
            float nrOfPages = (float) purchaseOrderRepository.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("purchaseorders", purchaseOrderRepository.findAll());
        }
        addDateTimeFormatPatterns(uiModel);
        return "se/po/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid PurchaseOrderExtensionBean purchaseOrderBean, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {

        uiModel.asMap().clear();
        PurchaseOrder po = PurchaseOrder.findPurchaseOrder(purchaseOrderBean.getId());
        if (po.getPlantHireRequest().getStartDate() != purchaseOrderBean.getStartDate()
        		|| po.getPlantHireRequest().getEndDate() != purchaseOrderBean.getEndDate()) {

        		Client client = Client.create();
            	client.addFilter(new HTTPBasicAuthFilter("customer", "customer"));
        		WebResource webResource = client.resource(po.getPlantHireRequest().getProviderURL()
        				+ "rest/purchaseorders/"+po.getExternalID()+"/modify");

        		PurchaseOrderResource por = new PurchaseOrderResource();
        		PlantHireRequest phr = po.getPlantHireRequest();
        		phr.setStartDate(purchaseOrderBean.getStartDate());
        		phr.setEndDate(purchaseOrderBean.getEndDate());
        		phr.persist();
        		
        		por.setExternalID(po.getId().toString());
        		por.setStartDate(purchaseOrderBean.getStartDate());
        		por.setEndDate(purchaseOrderBean.getEndDate());
        		por.setTotalCost(po.getPlantHireRequest().getTotalCost());
        		por.setPOrecievedDate(po.getDateCreated());
        		por.setStatus(po.getStatus());
        		por.setReturnDate(po.getPlantHireRequest().getEndDate());
        		por.setEmail(po.getPlantHireRequest().getSiteEngineer().getEmail());
        		ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
					.accept(MediaType.APPLICATION_XML)
					.post(ClientResponse.class, por);

        }
        
        return "redirect:/se/po/";
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, purchaseOrderRepository.findOne(id));
        return "se/po/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findOne(id);
        purchaseOrderRepository.delete(purchaseOrder);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/se/po";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("purchaseOrder_datecreated_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("phr_start_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("phr_end_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, PurchaseOrder purchaseOrder) {
        uiModel.addAttribute("purchaseOrder", purchaseOrder);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("purchaseorderstatuses", Arrays.asList(PurchaseOrderStatus.values()));
        uiModel.addAttribute("planthirerequests", PlantHireRequest.findAllPlantHireRequests());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
