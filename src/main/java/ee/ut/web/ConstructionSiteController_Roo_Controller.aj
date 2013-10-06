// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ee.ut.web;

import ee.ut.model.ConstructionSite;
import ee.ut.model.SiteEngineer;
import ee.ut.web.ConstructionSiteController;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect ConstructionSiteController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String ConstructionSiteController.create(@Valid ConstructionSite constructionSite, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, constructionSite);
            return "constructionsites/create";
        }
        uiModel.asMap().clear();
        constructionSite.persist();
        return "redirect:/constructionsites/" + encodeUrlPathSegment(constructionSite.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String ConstructionSiteController.createForm(Model uiModel) {
        populateEditForm(uiModel, new ConstructionSite());
        return "constructionsites/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String ConstructionSiteController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("constructionsite", ConstructionSite.findConstructionSite(id));
        uiModel.addAttribute("itemId", id);
        return "constructionsites/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String ConstructionSiteController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("constructionsites", ConstructionSite.findConstructionSiteEntries(firstResult, sizeNo));
            float nrOfPages = (float) ConstructionSite.countConstructionSites() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("constructionsites", ConstructionSite.findAllConstructionSites());
        }
        return "constructionsites/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String ConstructionSiteController.update(@Valid ConstructionSite constructionSite, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, constructionSite);
            return "constructionsites/update";
        }
        uiModel.asMap().clear();
        constructionSite.merge();
        return "redirect:/constructionsites/" + encodeUrlPathSegment(constructionSite.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String ConstructionSiteController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, ConstructionSite.findConstructionSite(id));
        return "constructionsites/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String ConstructionSiteController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        ConstructionSite constructionSite = ConstructionSite.findConstructionSite(id);
        constructionSite.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/constructionsites";
    }
    
    void ConstructionSiteController.populateEditForm(Model uiModel, ConstructionSite constructionSite) {
        uiModel.addAttribute("constructionSite", constructionSite);
        uiModel.addAttribute("siteengineers", SiteEngineer.findAllSiteEngineers());
    }
    
    String ConstructionSiteController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
