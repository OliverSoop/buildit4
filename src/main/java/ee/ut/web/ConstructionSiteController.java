package ee.ut.web;
import ee.ut.model.ConstructionSite;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/constructionsites")
@Controller
@RooWebScaffold(path = "constructionsites", formBackingObject = ConstructionSite.class)
public class ConstructionSiteController {
}
