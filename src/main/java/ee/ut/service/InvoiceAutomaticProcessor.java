package ee.ut.service;

import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;


import org.w3c.dom.Document;

import ee.ut.model.PlantHireRequest;
import ee.ut.model.PurchaseOrder;
import ee.ut.rest.InvoiceResource;


@Component
public class InvoiceAutomaticProcessor {
	
//	@ServiceActivator
	
	//TO-DO
//	InvoiceAutomaticProcessor and InvoiceHumanAssistedHandling classes will look similar to
//	InvoiceMailPreprocessor, where the class is annotated with @Component and the method to be
//	executed with @ServiceActivator. In the classes InvoiceAutomaticProcessor and
//	InvoiceHumanAssistedHandling you will specify the code for making payment of the invoices or
//	sending them for approval.
	
	@ServiceActivator
	public MailMessage process(Document invoice) throws JAXBException {
		
		MailMessage mailMessage = new SimpleMailMessage();
		JAXBContext jaxbCtx = JAXBContext.newInstance(InvoiceResource.class);
		InvoiceResource invoiceRes = (InvoiceResource) jaxbCtx
		.createUnmarshaller().unmarshal(invoice);

		Float invoiceTotal = invoiceRes.getTotal();
		String POid = invoiceRes.getPurchaseOrderHRef();
		PurchaseOrder po = PurchaseOrder.findPurchaseOrder(Long.parseLong(POid));
		PlantHireRequest phr = new PlantHireRequest();
		try {
			phr = po.getPlantHireRequest();
		} catch (Exception e) {
			mailMessage.setTo("buildit4app@gmail.com");
			mailMessage.setSentDate(new Date());
			mailMessage.setSubject("Error on invoice");
			mailMessage.setText("Did not find PO");
			return mailMessage;
		}
		
		Float phrTotal = phr.getTotalCost();
		
		mailMessage.setTo("buildit4app@gmail.com");
		mailMessage.setSentDate(new Date());
		mailMessage.setSubject("The payment is being processed");
		
		if(invoiceTotal.equals(phrTotal)){
			mailMessage.setText("Total match, well done");
			
		}else{
			mailMessage.setText("Totals dont match. Invoice total: " + invoiceTotal +
					" and our total: " + phrTotal);
		}
		
		return mailMessage;
	}


	
}
