package ee.ut.rest;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;

import ee.ut.util.ResourceSupport;

@RooJavaBean
@XmlRootElement(name = "siteEngineer")
public class SiteEngineerResource extends ResourceSupport {
	private String name;
	private String email;
}
