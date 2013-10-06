package ee.ut.model;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.OneToOne;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class RequestedPlant {

    /**
     */
    private String externalId;

    /**
     */
    private String description;

    /**
     */
    @OneToOne
    private Supplier supplier;
}
