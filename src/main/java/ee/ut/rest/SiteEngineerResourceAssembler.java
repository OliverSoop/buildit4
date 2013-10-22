package ee.ut.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.lang.reflect.Method;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import ee.ut.model.SiteEngineer;
import ee.ut.rest.controller.SiteEngController;
import ee.ut.util.ExtendedLink;

public class SiteEngineerResourceAssembler extends ResourceAssemblerSupport<SiteEngineer, SiteEngineerResource>{

	public SiteEngineerResourceAssembler() {
		super(SiteEngController.class, SiteEngineerResource.class);
	}

	@Override
	public SiteEngineerResource toResource(SiteEngineer arg) {
		SiteEngineerResource engineer = new SiteEngineerResource();
		engineer.setName(arg.getName());
		
		try {
			Method seMethod = SiteEngController.class.getMethod("getSiteEngineer", Long.class);
			String seLink = linkTo(seMethod, arg.getId()).toUri().toString();
			engineer.add(new ExtendedLink(seLink, "self", "GET"));
		} catch (NoSuchMethodException e) {
			// No self link will be sent
		} catch (SecurityException e) {
			// No self link will be sent
		}
		
		return engineer;
	}

}
