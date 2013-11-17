package ee.ut.model;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import ee.ut.domain.PurchaseOrderStatus;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class PurchaseOrder {

    /**
     */
    @Enumerated
    private PurchaseOrderStatus status;

    /**
     */
    @OneToOne
    private PlantHireRequest plantHireRequest;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateCreated;

    /**
     */
    private String ExternalID;

	public String getExternalID() {
        return this.ExternalID;
    }
}
