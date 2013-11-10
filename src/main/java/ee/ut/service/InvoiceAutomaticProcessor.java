package ee.ut.service;

import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;


import org.w3c.dom.Document;

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
		mailMessage.setTo(invoiceRes.getReturnEmail());
		mailMessage.setSentDate(new Date());
		mailMessage.setSubject("The payment is being processed");
		mailMessage.setText("This is an automated message");
		return mailMessage;
	}


	
}
