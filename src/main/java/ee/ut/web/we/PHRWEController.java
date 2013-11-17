package ee.ut.web.we;
import ee.ut.model.PlantHireRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/we/phrs")
@Controller
@RooWebScaffold(path = "we/phrs", formBackingObject = PlantHireRequest.class, create = false)
public class PHRWEController {

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid PlantHireRequest plantHirerequest, BindingResult
	bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
	if (bindingResult.hasErrors()) {
	populateEditForm(uiModel, plantHirerequest);
	return "we/phrs/update";
	}
	PlantHireRequest originalResource =
	PlantHireRequest.findPlantHireRequest(plantHirerequest.getId());
	originalResource.setStatus(plantHirerequest.getStatus());
	uiModel.asMap().clear();
	originalResource.merge();
	return "redirect:/we/phrs/" +
	encodeUrlPathSegment(originalResource.getId().toString(), httpServletRequest);
	}
}
