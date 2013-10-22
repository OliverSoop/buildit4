package ee.ut.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.lang.reflect.Method;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import ee.ut.model.ConstructionSite;
import ee.ut.rest.controller.ConstructionSitController;
import ee.ut.util.ExtendedLink;

public class ConstructionSiteResourceAssembler extends ResourceAssemblerSupport<ConstructionSite, ConstructionSiteResource>{

	public ConstructionSiteResourceAssembler() {
		super(ConstructionSitController.class, ConstructionSiteResource.class);
	}

	@Override
	public ConstructionSiteResource toResource(ConstructionSite arg) {
		ConstructionSiteResource constructionSite = new ConstructionSiteResource();
		constructionSite.setLocation(arg.getLocation());
		constructionSite.setName(arg.getName());
		
		try {
			Method seMethod = ConstructionSitController.class.getMethod("getConstructionSite", Long.class);
			String seLink = linkTo(seMethod, arg.getId()).toUri().toString();
			constructionSite.add(new ExtendedLink(seLink, "self", "GET"));
		} catch (NoSuchMethodException e) {
			// No self link will be sent
		} catch (SecurityException e) {
			// No self link will be sent
		}
		return constructionSite;
	}

}
