// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.web;

import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;
import at.ac.tuwien.dse.fairsurgeries.web.OPSlotController;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

privileged aspect OPSlotController_Roo_Controller {
    
    @Autowired
    OPSlotService OPSlotController.oPSlotService;
    
    @Autowired
    DoctorService OPSlotController.doctorService;
    
    @Autowired
    HospitalService OPSlotController.hospitalService;
    
    @Autowired
    PatientService OPSlotController.patientService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String OPSlotController.create(@Valid OPSlot OPSlot_, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, OPSlot_);
            return "opslots/create";
        }
        uiModel.asMap().clear();
        oPSlotService.saveOPSlot(OPSlot_);
        return "redirect:/opslots/" + encodeUrlPathSegment(OPSlot_.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String OPSlotController.createForm(Model uiModel) {
        populateEditForm(uiModel, new OPSlot());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (hospitalService.countAllHospitals() == 0) {
            dependencies.add(new String[] { "hospital", "hospitals" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "opslots/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String OPSlotController.show(@PathVariable("id") BigInteger id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("opslot_", oPSlotService.findOPSlot(id));
        uiModel.addAttribute("itemId", id);
        return "opslots/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String OPSlotController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("opslots", oPSlotService.findOPSlotEntries(firstResult, sizeNo));
            float nrOfPages = (float) oPSlotService.countAllOPSlots() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("opslots", oPSlotService.findAllOPSlots());
        }
        addDateTimeFormatPatterns(uiModel);
        return "opslots/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String OPSlotController.update(@Valid OPSlot OPSlot_, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, OPSlot_);
            return "opslots/update";
        }
        uiModel.asMap().clear();
        oPSlotService.updateOPSlot(OPSlot_);
        return "redirect:/opslots/" + encodeUrlPathSegment(OPSlot_.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String OPSlotController.updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, oPSlotService.findOPSlot(id));
        return "opslots/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String OPSlotController.delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        OPSlot OPSlot_ = oPSlotService.findOPSlot(id);
        oPSlotService.deleteOPSlot(OPSlot_);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/opslots";
    }
    
    void OPSlotController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("OPSlot__datefrom_date_format", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("OPSlot__dateto_date_format", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
    }
    
    void OPSlotController.populateEditForm(Model uiModel, OPSlot OPSlot_) {
        uiModel.addAttribute("OPSlot_", OPSlot_);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("doctors", doctorService.findAllDoctors());
        uiModel.addAttribute("hospitals", hospitalService.findAllHospitals());
        uiModel.addAttribute("patients", patientService.findAllPatients());
        uiModel.addAttribute("surgerytypes", Arrays.asList(SurgeryType.values()));
    }
    
    String OPSlotController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
