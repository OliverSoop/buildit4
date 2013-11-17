package ee.ut.web.se;
import ee.ut.domain.PlantHireRequestStatus;
import ee.ut.model.PlantHireRequest;
import ee.ut.model.SiteEngineer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/se/phrs")
@Controller
@RooWebScaffold(path = "se/phrs", formBackingObject = PlantHireRequest.class, delete = false)
public class PHRSEController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid PlantHireRequest plantHireRequest, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, plantHireRequest);
            return "se/phrs/create";
        }
        uiModel.asMap().clear();
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        SiteEngineer se = SiteEngineer.findAllSiteEngineers().get(0);
        plantHireRequest.setStatus(PlantHireRequestStatus.PENDING_CONFIRMATION);
        plantHireRequest.setSiteEngineer(se);
        plantHireRequest.persist();

        return "redirect:/se/phrs/" + encodeUrlPathSegment(plantHireRequest.getId().toString(), httpServletRequest);
    }
}
