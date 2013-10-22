package ee.ut.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ee.ut.model.ConstructionSite;
import ee.ut.rest.ConstructionSiteResource;
import ee.ut.rest.ConstructionSiteResourceAssembler;

@Controller
@RequestMapping("/rest/constructionsite")
public class ConstructionSitController {

	@RequestMapping
	(method = RequestMethod.GET, value ="/{id}")
	public ResponseEntity<ConstructionSiteResource> getConstructionSite(@PathVariable Long id) {
		ConstructionSite se = ConstructionSite.findConstructionSite(id);
		if (se != null) {
			ConstructionSiteResourceAssembler assembler = new ConstructionSiteResourceAssembler();
			return new ResponseEntity<ConstructionSiteResource>(assembler.toResource(se), HttpStatus.OK);
			
		}
		return new ResponseEntity<ConstructionSiteResource>(HttpStatus.NOT_FOUND);
	}
}
