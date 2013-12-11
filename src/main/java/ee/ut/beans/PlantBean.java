package ee.ut.beans;


import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
@RooJavaBean
public class PlantBean {
	String name;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	Date startDate;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	Date endDate;
	Long selectedPlantId;
	String description;
	Double costPerDay;
	String supplier;
}
