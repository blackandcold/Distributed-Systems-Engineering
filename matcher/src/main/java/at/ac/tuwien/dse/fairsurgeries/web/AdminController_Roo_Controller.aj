// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.web;

import at.ac.tuwien.dse.fairsurgeries.domain.Admin;
import at.ac.tuwien.dse.fairsurgeries.service.AdminService;
import at.ac.tuwien.dse.fairsurgeries.web.AdminController;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect AdminController_Roo_Controller {
    
    @Autowired
    AdminService AdminController.adminService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String AdminController.create(@Valid Admin admin, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, admin);
            return "admins/create";
        }
        uiModel.asMap().clear();
        adminService.saveAdmin(admin);
        return "redirect:/admins/" + encodeUrlPathSegment(admin.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String AdminController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Admin());
        return "admins/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String AdminController.show(@PathVariable("id") BigInteger id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("admin", adminService.findAdmin(id));
        uiModel.addAttribute("itemId", id);
        return "admins/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String AdminController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("admins", adminService.findAdminEntries(firstResult, sizeNo));
            float nrOfPages = (float) adminService.countAllAdmins() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("admins", adminService.findAllAdmins());
        }
        addDateTimeFormatPatterns(uiModel);
        return "admins/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String AdminController.update(@Valid Admin admin, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, admin);
            return "admins/update";
        }
        uiModel.asMap().clear();
        adminService.updateAdmin(admin);
        return "redirect:/admins/" + encodeUrlPathSegment(admin.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String AdminController.updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, adminService.findAdmin(id));
        return "admins/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String AdminController.delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Admin admin = adminService.findAdmin(id);
        adminService.deleteAdmin(admin);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/admins";
    }
    
    void AdminController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("admin_dateofbirth_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    void AdminController.populateEditForm(Model uiModel, Admin admin) {
        uiModel.addAttribute("admin", admin);
        addDateTimeFormatPatterns(uiModel);
    }
    
    String AdminController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
