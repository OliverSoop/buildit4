package ee.ut.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ee.ut.domain.PurchaseOrderStatus;
import ee.ut.model.PurchaseOrder;

@Controller
@RequestMapping("/rest/po")
public class PurchaseOrdController {

	@RequestMapping(method = RequestMethod.POST, value ="/{id}/reject")
	public ResponseEntity<Void> rejectPO(@PathVariable Long id) {
		PurchaseOrder po = PurchaseOrder.findPurchaseOrder(id);
		po.setStatus(PurchaseOrderStatus.REJECTED);
		po.persist();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value ="/{id}/accept")
	public ResponseEntity<Void> acceptPO(@PathVariable Long id) {
		PurchaseOrder po = PurchaseOrder.findPurchaseOrder(id);
		po.setStatus(PurchaseOrderStatus.ACCEPTED);
		po.persist();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}