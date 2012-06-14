package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.math.BigInteger;
import java.util.Arrays;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;

@Controller
@RequestMapping("/actors/patient")
public class ActorPatientController {

	@Autowired
	private LogEntryService logEntryService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private OPSlotService opSlotService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private PatientService patientService;

	
	@RequestMapping(value = "{patientId}", method = RequestMethod.GET)
	public String showMenu(@PathVariable BigInteger patientId, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPatient . () for ID: " + patientId);

		Patient patient = patientService.findPatient(patientId);

		if (patient != null) {
			uiModel.addAttribute("patient", patient);

			return "actors/patient/showmenu";
		} else {
			return "redirect:/resourceNotFound";
		}
	}
	
	@RequestMapping(value = "/viewslots", method = RequestMethod.POST)
	public String viewSlots(@ModelAttribute Patient patient, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPatient . viewSlots() for patient: " + patient);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);

		OPSlot filterExample = new OPSlot();
		filterExample.setPatient(patient);
		
		uiModel.addAttribute("opSlots", opSlotService.findByPatient(patient));
		uiModel.addAttribute("surgeryTypes", Arrays.asList(SurgeryType.values()));
		uiModel.addAttribute("hospitals", hospitalService.findAllHospitals());
		uiModel.addAttribute("doctors", doctorService.findAllDoctors());
		uiModel.addAttribute("patients", Arrays.asList(patient));
		uiModel.addAttribute("OPSlot_datefrom_date_format", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("OPSlot_dateto_date_format", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("opSlotExample", filterExample);
		uiModel.addAttribute("showPatientName", false);

		return "actors/public/slots";
	}
	
	@RequestMapping(value = "/viewnotifications", method = RequestMethod.POST)
	public String viewNotifications(@ModelAttribute Patient patient, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPatient . viewNotifications() for patient: " + patient);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		
		return "actors/patient/viewnotifications";
	}

}