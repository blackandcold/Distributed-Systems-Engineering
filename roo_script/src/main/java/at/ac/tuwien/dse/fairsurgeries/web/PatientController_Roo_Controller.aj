// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.web;

import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;
import at.ac.tuwien.dse.fairsurgeries.web.PatientController;
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

privileged aspect PatientController_Roo_Controller {
    
    @Autowired
    PatientService PatientController.patientService;
    
    @Autowired
    OPSlotService PatientController.oPSlotService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String PatientController.create(@Valid Patient patient, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, patient);
            return "patients/create";
        }
        uiModel.asMap().clear();
        patientService.savePatient(patient);
        return "redirect:/patients/" + encodeUrlPathSegment(patient.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String PatientController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Patient());
        return "patients/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String PatientController.show(@PathVariable("id") BigInteger id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("patient", patientService.findPatient(id));
        uiModel.addAttribute("itemId", id);
        return "patients/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String PatientController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("patients", patientService.findPatientEntries(firstResult, sizeNo));
            float nrOfPages = (float) patientService.countAllPatients() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("patients", patientService.findAllPatients());
        }
        addDateTimeFormatPatterns(uiModel);
        return "patients/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String PatientController.update(@Valid Patient patient, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, patient);
            return "patients/update";
        }
        uiModel.asMap().clear();
        patientService.updatePatient(patient);
        return "redirect:/patients/" + encodeUrlPathSegment(patient.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String PatientController.updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, patientService.findPatient(id));
        return "patients/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String PatientController.delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Patient patient = patientService.findPatient(id);
        patientService.deletePatient(patient);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/patients";
    }
    
    void PatientController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("patient_dateofbirth_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    void PatientController.populateEditForm(Model uiModel, Patient patient) {
        uiModel.addAttribute("patient", patient);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("opslots", oPSlotService.findAllOPSlots());
    }
    
    String PatientController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
