package ee.ut.rest;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
@XmlRootElement(name = "constructionSite")
public class InvoiceResource {
	private String To;
	private Date SentDate;
	private String Subject;
	private String Text;
}
