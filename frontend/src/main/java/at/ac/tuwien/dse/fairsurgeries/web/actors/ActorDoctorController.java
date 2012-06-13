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

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationDTO;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;

@Controller
@RequestMapping("/actors/doctor")
public class ActorDoctorController {
	
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
	
	
	
	@RequestMapping(value = "{doctorId}", method = RequestMethod.GET)
	public String showMenu(@PathVariable BigInteger doctorId, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorDoctor . () for ID: " + doctorId);

		Doctor doctor = doctorService.findDoctor(doctorId);

		if (doctor != null) {
			uiModel.addAttribute("doctor", doctor);

			return "actors/doctor/showmenu";
		} else {
			return "redirect:/resourceNotFound";
		}
	}
	
	@RequestMapping(value = "/viewslots", method = RequestMethod.POST)
	public String viewSlots(@ModelAttribute Doctor doctor, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorDoctor . viewSlots() for doctor: " + doctor);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		
		return "actors/doctor/viewslots";
	}
	
	@RequestMapping(value = "/viewnotifications", method = RequestMethod.POST)
	public String viewNotifications(@ModelAttribute Doctor doctor, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorDoctor . viewNotifications() for doctor: " + doctor);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		
		return "actors/doctor/viewnotifications";
	}
	
	@RequestMapping(value = "/manageslots", method = RequestMethod.POST)
	public String manageSlots(@ModelAttribute Doctor doctor, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorDoctor . manageSlots() for doctor: " + doctor);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		uiModel.addAttribute("opSlot", new OPSlot());
		uiModel.addAttribute("opSlots", opSlotService.findByDoctor(doctor));
		
		uiModel.addAttribute("OPSlot__datefrom_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("OPSlot__dateto_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        
        /*uiModel.addAttribute("doctors", doctorService.findAllDoctors());
        uiModel.addAttribute("hospitals", hospitalService.findAllHospitals());
        uiModel.addAttribute("patients", patientService.findAllPatients());
        uiModel.addAttribute("surgerytypes", Arrays.asList(SurgeryType.values()));*/
		
		return "actors/doctor/manageslots";
	}

	@RequestMapping(value="/reservations", method = RequestMethod.POST)
	public String manageReservation(@ModelAttribute Doctor doctor, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorDoctorController . manageReservations()");
		logEntryService.log(Constants.Component.Frontend.toString(), "doctor: " + doctor);
		
		ReservationDTO reservation = new ReservationDTO(null, null, null, 27., null, null);
		
		uiModel.addAttribute("doctors", Arrays.asList(doctor));
		uiModel.addAttribute("reservation", reservation);
		uiModel.addAttribute("surgeryTypes", Arrays.asList(SurgeryType.values()));
		uiModel.addAttribute("patients", patientService.findAllPatients());
		
		return "actors/doctor/reservations";
	}
	
	@RequestMapping(value="/doreservation", method = RequestMethod.POST, produces = "text/html")
	public String doReservation(BigInteger doctorId, BigInteger patientId, SurgeryType surgeryType, double radius, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorDoctorController . doReservation()");
		
		logEntryService.log("TEST", "doctorId: " + doctorId);
		logEntryService.log("TEST", "patientId: " + patientId);
		logEntryService.log("TEST", "SurgeryType: " + surgeryType);
		logEntryService.log("TEST", "Radius: " + radius);	
		logEntryService.log("TEST", "uiModel: " + uiModel.asMap().toString());
		
		return "actors/doctor/reservations";
	}
}
