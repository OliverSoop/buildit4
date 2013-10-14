package ee.ut.rest;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
@XmlRootElement(name = "plantHireRequest")
public class PlantHireRequestResource {

    private Date startDate;
    private Date endDate;
    private Float totalCost;
    private RequestedPlantResource requestedPlant;
    private SiteEngineerResource siteEngineer;
    private ConstructionSiteResource constructionSite;
}
