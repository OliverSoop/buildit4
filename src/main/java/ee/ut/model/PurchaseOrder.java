package ee.ut.model;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import ee.ut.domain.PurchaseOrderStatus;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

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
}
