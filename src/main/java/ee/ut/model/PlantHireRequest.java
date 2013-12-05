package ee.ut.model;
import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.Enumerated;
import ee.ut.domain.PlantHireRequestStatus;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class PlantHireRequest {

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date startDate;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date endDate;

    /**
     */
    private Float totalCost;
    
    /**
     */
    private String providerURL;

    /**
     */
    @ManyToOne
    private ConstructionSite constructionSite;

    /**
     */
    @OneToOne
    private RequestedPlant requestedPlant;

    /**
     */
    @ManyToOne
    private SiteEngineer siteEngineer;

    /**
     */
    @Enumerated
    private PlantHireRequestStatus status;
}
