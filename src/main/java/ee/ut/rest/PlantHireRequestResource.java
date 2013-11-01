package ee.ut.rest;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;




import ee.ut.util.DateAdapter;
import ee.ut.util.ResourceSupport;

@XmlRootElement(name = "plantHireRequest")
public class PlantHireRequestResource extends ResourceSupport {
    private Date startDate;
    private Date endDate;
    private Float totalCost;
    private RequestedPlantResource requestedPlant;
    private SiteEngineerResource siteEngineer;
    private ConstructionSiteResource constructionSite;
    private String status;
    
    @XmlJavaTypeAdapter(DateAdapter.class)
	public Date getStartDate() {
        return this.startDate;
    }

	public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
	
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getEndDate() {
        return this.endDate;
    }

	public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

	public Float getTotalCost() {
        return this.totalCost;
    }

	public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

	public RequestedPlantResource getRequestedPlant() {
        return this.requestedPlant;
    }

	public void setRequestedPlant(RequestedPlantResource requestedPlant) {
        this.requestedPlant = requestedPlant;
    }

	public SiteEngineerResource getSiteEngineer() {
        return this.siteEngineer;
    }

	public void setSiteEngineer(SiteEngineerResource siteEngineer) {
        this.siteEngineer = siteEngineer;
    }

	public ConstructionSiteResource getConstructionSite() {
        return this.constructionSite;
    }

	public void setConstructionSite(ConstructionSiteResource constructionSite) {
        this.constructionSite = constructionSite;
    }

	public String getStatus() {
        return this.status;
    }

	public void setStatus(String status) {
        this.status = status;
    }
}
