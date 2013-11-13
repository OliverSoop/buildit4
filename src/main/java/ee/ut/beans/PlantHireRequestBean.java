package ee.ut.beans;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;

import ee.ut.domain.PlantHireRequestStatus;
import ee.ut.model.ConstructionSite;
import ee.ut.model.RequestedPlant;
import ee.ut.model.SiteEngineer;

@RooJavaBean
public class PlantHireRequestBean {

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date endDate;

    private Double totalCost;

    private ConstructionSite constructionSite;

    
    private RequestedPlant requestedPlant;

    
    private SiteEngineer siteEngineer;

    
    private PlantHireRequestStatus status;
}
