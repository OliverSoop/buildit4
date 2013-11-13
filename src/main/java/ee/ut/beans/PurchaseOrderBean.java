package ee.ut.beans;

import java.util.Date;

import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;

import ee.ut.domain.PurchaseOrderStatus;
import ee.ut.model.PlantHireRequest;

@RooJavaBean
public class PurchaseOrderBean {
	   /**
     */
    @Enumerated
    private PurchaseOrderStatus status;

    /**
     */

    private PlantHireRequest plantHireRequest;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date dateCreated;

    /**
     */
    private String ExternalID;
}
