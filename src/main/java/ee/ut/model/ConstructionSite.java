package ee.ut.model;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class ConstructionSite {

    /**
     */
    private String name;

    /**
     */
    private String location;

    /**
     */
    @OneToMany(cascade = CascadeType.ALL)
    private Set<SiteEngineer> siteEngineers = new HashSet<SiteEngineer>();
}
