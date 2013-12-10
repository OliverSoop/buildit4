package ee.ut.beans;

import java.util.Date;

import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;

import ee.ut.domain.PurchaseOrderStatus;

@RooJavaBean
public class PurchaseOrderExtensionBean {
	
	private Long id;
	
    private Integer version;
	
	@Enumerated
    private PurchaseOrderStatus status;

    /**
     */
	
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date startDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date endDate;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date dateCreated;

    /**
     */
    private String ExternalID;
}
