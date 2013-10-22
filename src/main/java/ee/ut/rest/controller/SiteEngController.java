package ee.ut.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ee.ut.model.SiteEngineer;
import ee.ut.rest.SiteEngineerResource;
import ee.ut.rest.SiteEngineerResourceAssembler;

@Controller
@RequestMapping("/rest/engineer")
public class SiteEngController {

	@RequestMapping
	(method = RequestMethod.GET, value ="/{id}")
	public ResponseEntity<SiteEngineerResource> getSiteEngineer(@PathVariable Long id) {
		SiteEngineer se = SiteEngineer.findSiteEngineer(id);
		if (se != null) {
			SiteEngineerResourceAssembler assembler = new SiteEngineerResourceAssembler();
			return new ResponseEntity<SiteEngineerResource>(assembler.toResource(se), HttpStatus.OK);
			
		}
		return new ResponseEntity<SiteEngineerResource>(HttpStatus.NOT_FOUND);
	}
}
