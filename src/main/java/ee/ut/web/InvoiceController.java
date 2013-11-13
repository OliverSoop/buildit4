package ee.ut.web;
import java.util.Date;

import ee.ut.domain.InvoiceStatus;
import ee.ut.domain.PurchaseOrderStatus;
import ee.ut.model.Invoice;
import ee.ut.model.PlantHireRequest;
import ee.ut.model.PurchaseOrder;
import ee.ut.rest.InvoiceResource;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;

@RequestMapping("/invoices")
@Controller
@RooWebScaffold(path = "invoices", formBackingObject = Invoice.class)
public class InvoiceController {

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Invoice invoice, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, invoice);
            return "invoices/update";
        }
        uiModel.asMap().clear();
        invoice.merge();
        if(invoice.getStatus()==InvoiceStatus.APPROVED){
        	process(invoice);
        }
        return "redirect:/invoices/" + encodeUrlPathSegment(invoice.getId().toString(), httpServletRequest);
    }
	
	@Autowired
    private JavaMailSender mailSender;
	
	@ServiceActivator
	public void process(Invoice invoice) {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo(invoice.getReturnEmail());
		mailMessage.setSentDate(new Date());
		mailMessage.setSubject("Status changed to APPROVED");
		mailMessage.setText("You may start paying. Assosiated PO: " + invoice.getPurchaseOrderHRef());
		mailSender.send(mailMessage);
	}
}
