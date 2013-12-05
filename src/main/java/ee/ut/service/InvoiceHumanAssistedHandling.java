package ee.ut.service;

import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import ee.ut.domain.InvoiceStatus;
import ee.ut.domain.PurchaseOrderStatus;
import ee.ut.model.Invoice;
import ee.ut.model.PlantHireRequest;
import ee.ut.model.PurchaseOrder;
import ee.ut.rest.InvoiceResource;


@Component
public class InvoiceHumanAssistedHandling {
	
	@ServiceActivator
	public MailMessage process(Document invoice) throws JAXBException {
		
		MailMessage mailMessage = new SimpleMailMessage();
		JAXBContext jaxbCtx = JAXBContext.newInstance(InvoiceResource.class);
		InvoiceResource invoiceRes = (InvoiceResource) jaxbCtx
		.createUnmarshaller().unmarshal(invoice);
		
		Invoice invoiceNew = new Invoice();
		invoiceNew.setPurchaseOrderHRef("http://buildit4.herokuapp.com/purchaseorders/"+invoiceRes.getPurchaseOrderHRef());
		invoiceNew.setReturnEmail(invoiceRes.getReturnEmail());
		invoiceNew.setStatus(InvoiceStatus.DISAPPROVED);
		invoiceNew.setTotal(invoiceRes.getTotal());
		invoiceNew.setExternalId(invoiceRes.getExternalId());
		invoiceNew.setDeadline(invoiceRes.getDeadline());
		
		Float invoiceTotal = invoiceRes.getTotal();
		String POid = invoiceRes.getPurchaseOrderHRef();
		PurchaseOrder po = PurchaseOrder.findPurchaseOrder(Long.parseLong(POid));
		PlantHireRequest phr = new PlantHireRequest();
		try {
			phr = po.getPlantHireRequest();
		} catch (Exception e) {
			mailMessage.setTo(invoiceRes.getReturnEmail());
			mailMessage.setSentDate(new Date());
			mailMessage.setSubject("Error on invoice");
			mailMessage.setText("Did not find purchase order. Id: " + POid + " . Error: " + e);
			return mailMessage;
		}
		
		PurchaseOrderStatus poStatus = po.getStatus();
		
		Float phrTotal = phr.getTotalCost();
		
		mailMessage.setTo(invoiceRes.getReturnEmail());
		mailMessage.setSentDate(new Date());
		mailMessage.setSubject("The payment is being processed");
		
		if(invoiceTotal.equals(phrTotal) && poStatus != poStatus.PAID){
			mailMessage.setText("Total match and it is unpaid, well done");
			invoiceNew.persist();
			
		}else{
			mailMessage.setSubject("Error on invoivce");
			mailMessage.setText("Totals dont match or this PO is paid. Invoice total: " + invoiceTotal +
					" and our total: " + phrTotal + ". PO status: " + poStatus);
		}
		
		return mailMessage;
	}

}
