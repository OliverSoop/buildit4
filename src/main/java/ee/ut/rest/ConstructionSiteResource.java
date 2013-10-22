package ee.ut.rest;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;

import ee.ut.util.ResourceSupport;

@RooJavaBean
@XmlRootElement(name = "constructionSite")
public class ConstructionSiteResource extends ResourceSupport {
	private String name;
	private String location;
}
