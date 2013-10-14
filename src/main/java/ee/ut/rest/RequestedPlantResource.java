package ee.ut.rest;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
@XmlRootElement(name = "requestedPlant")
public class RequestedPlantResource {
	private String externalId;
	private String description;
	private SupplierResource supplier;

}
