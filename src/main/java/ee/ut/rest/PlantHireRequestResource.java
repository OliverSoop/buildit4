package ee.ut.rest;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;

import ee.ut.util.ResourceSupport;

@RooJavaBean
@XmlRootElement(name = "plantHireRequest")
public class PlantHireRequestResource extends ResourceSupport {
    private Date startDate;
    private Date endDate;
    private Float totalCost;
    private RequestedPlantResource requestedPlant;
    private SiteEngineerResource siteEngineer;
    private ConstructionSiteResource constructionSite;
    private String status;
}
