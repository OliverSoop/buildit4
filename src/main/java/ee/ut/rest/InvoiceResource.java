package ee.ut.rest;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
@XmlRootElement(name = "invoice")
public class InvoiceResource {
	private Float total;
	private String purchaseOrderHRef;
	private String returnEmail;
}
