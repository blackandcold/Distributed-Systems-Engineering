package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.util.Arrays;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;

@Controller
@RequestMapping("/actors/public")
public class ActorPublicPersonController {

	@Autowired
	private OPSlotService opSlotService;

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private LogEntryService logEntryService;

	@RequestMapping(value = "/slots", method = RequestMethod.GET, produces = "text/html")
	public String listSlots(Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPublicPersonController . listSlots()");

		uiModel.addAttribute("headline", "Public Person");
		uiModel.addAttribute("opSlots", opSlotService.findAllOPSlots());
		uiModel.addAttribute("surgeryTypes", Arrays.asList(SurgeryType.values()));
		uiModel.addAttribute("hospitals", hospitalService.findAllHospitals());
		uiModel.addAttribute("doctors", doctorService.findAllDoctors());
		uiModel.addAttribute("patients", patientService.findAllPatients());
		uiModel.addAttribute("opSlotExample", new OPSlot());
		uiModel.addAttribute("OPSlot_datefrom_date_format", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("OPSlot_dateto_date_format", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("showPatientName", false);

		return "actors/public/slots";
	}

	@RequestMapping(value = "/slots", method = RequestMethod.POST, produces = "text/html")
	public String listFilteredSlots(@ModelAttribute OPSlot opSlot, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPublicPersonController . listFilteredSlots() example=" + opSlot);

		uiModel.addAttribute("headline", "Public Person");
		uiModel.addAttribute("opSlots", opSlotService.findByExample(opSlot));
		uiModel.addAttribute("surgeryTypes", Arrays.asList(SurgeryType.values()));
		uiModel.addAttribute("hospitals", hospitalService.findAllHospitals());
		uiModel.addAttribute("doctors", doctorService.findAllDoctors());
		uiModel.addAttribute("patients", patientService.findAllPatients());
		uiModel.addAttribute("OPSlot_datefrom_date_format", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("OPSlot_dateto_date_format", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("opSlotExample", opSlot);
		uiModel.addAttribute("showPatientName", false);

		return "actors/public/slots";
	}

}
