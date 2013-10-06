package ee.ut.web;
import ee.ut.model.RequestedPlant;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/requestedplants")
@Controller
@RooWebScaffold(path = "requestedplants", formBackingObject = RequestedPlant.class)
public class RequestedPlantController {
}
