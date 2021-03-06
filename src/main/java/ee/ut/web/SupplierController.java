package ee.ut.web;
import ee.ut.model.Supplier;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/suppliers")
@Controller
@RooWebScaffold(path = "suppliers", formBackingObject = Supplier.class)
public class SupplierController {
}
