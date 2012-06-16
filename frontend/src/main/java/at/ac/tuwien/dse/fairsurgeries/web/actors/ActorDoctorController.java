package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletRequest;

import org.joda.time.format.DateTimeFormat;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Notification;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlotStatus;
import at.ac.tuwien.dse.fairsurgeries.domain.Reservation;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.NotificationService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;
import at.ac.tuwien.dse.fairsurgeries.web.MessageController;

@Controller
@RequestMapping("/actors/doctor")
public class ActorDoctorController {

	@Autowired
	private AmqpTemplate template;
	@Autowired
	private LogEntryService logEntryService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private OPSlotService slotService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private NotificationService notificationService;
	

	/**
	 * This method logs in as as the doctor with the given id and shows the menu of possible actions if successful.
	 * @param doctorId the id of the doctor we want to login as
	 * @param uiModel the ui model object
	 * @return identifier of the next page we want to visit
	 */
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

	/**
	 * This method updates the ui model to show a list of slots matching the given criteria.
	 * @param doctor the logged in doctor
	 * @param opSlot the example slot 
	 * @param uiModel the ui model
	 * @param request the request
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/slots", method = RequestMethod.POST)
	public String listSlots(@ModelAttribute Doctor doctor, @ModelAttribute OPSlot opSlot, Model uiModel, ServletRequest request) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorDoctor . viewSlots() for doctor: " + doctor);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		
		if (opSlot == null) {
			opSlot = new OPSlot();
		}
		
		if(doctor != null) {
			opSlot.setDoctor(doctor);
		}
		
		String status = request.getParameter("status");
		
		if (status == null || status.isEmpty()) {
			this.setupModel(uiModel, opSlot, null);
		} else {
			this.setupModel(uiModel, opSlot, OPSlotStatus.valueOf(status));
		}
		
		return "actors/doctor/slots";
	}

	/**
	 * This method updates the ui model to show the list of notifications for the logged in doctor. 
	 * @param doctor the logged in doctor
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/notifications", method = RequestMethod.POST)
	public String listNotifications(@ModelAttribute Doctor doctor, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorDoctor. viewNotifications() for doctor: " + doctor);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);

		List<Notification> notifications = notificationService.findByDoctor(doctor);
		uiModel.addAttribute("notifications", notifications);

		return "actors/doctor/notifications";
	}

	/**
	 * This method updates the ui model to display all possible actions the doctor can manage
	 * @param doctor the logged in doctor
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/manageslots", method = RequestMethod.POST)
	public String manageSlots(@ModelAttribute Doctor doctor, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorDoctor . manageSlots() for doctor: " + doctor);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		
		uiModel.addAttribute("opSlot", new OPSlot());
		uiModel.addAttribute("opSlots", slotService.findByDoctor(doctor));
		uiModel.addAttribute("dateFormat", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));

		return "actors/doctor/manageslots";
	}

	/**
	 * This method updates the ui model to display the possibilities for "manageReservations"
	 * @param doctor the logged in doctor
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/reservations", method = RequestMethod.POST)
	public String manageReservation(@ModelAttribute Doctor doctor, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorDoctorController . manageReservations()");
		logEntryService.log(Constants.Component.Frontend.toString(), "doctor: " + doctor);

		Reservation reservation = new Reservation();

		uiModel.addAttribute("doctors", Arrays.asList(doctor));
		uiModel.addAttribute("reservation", reservation);
		uiModel.addAttribute("surgeryTypes", Arrays.asList(SurgeryType.values()));
		uiModel.addAttribute("patients", patientService.findAllPatients());
		uiModel.addAttribute("dateFormat", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));

		return "actors/doctor/reservations";
	}

	/**
	 * This method handles a reservation by posting a message into the MatcherIn-Queue.
	 * @param reservation the reservation to post
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/doreservation", method = RequestMethod.POST, produces = "text/html")
	public String doReservation(@ModelAttribute Reservation reservation, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorDoctorController . doReservation()");

		logEntryService.log("Reservation", "patient: " + reservation.getPatient());
		logEntryService.log("Reservation", "doctor: " + reservation.getDoctor());
		logEntryService.log("Reservation", "type: " + reservation.getSurgeryType());
		logEntryService.log("Reservation", "radius: " + reservation.getRadius());
		logEntryService.log("Reservation", "from: " + reservation.getDateFrom());
		logEntryService.log("Reservation", "to: " + reservation.getDateTo());
		
		if (reservation != null) {
			MessageController.sendMessage(template, Constants.Queue.MatcherIn, reservation);
		}

		String redirectUrl = "/actors/doctor/" + reservation.getDoctor().getId();
		
		return "redirect:" + redirectUrl;
	}
	
	/**
	 * This method handles the cancelation of a reserved op slot and forwards to the menu page of a doctor.
	 * @param id the id of the slot to cancel
	 * @param page the page
	 * @param size the size
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/cancelSlotReservation/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String cancelSlotReservation(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        OPSlot slot = slotService.findOPSlot(id);
        Doctor doctor = slot.getDoctor();
        
        slot.setPatient(null);
        slot.setDoctor(null);
        slot.setHospital(null);
        slot.setSurgeryType(null);
        
        slotService.updateOPSlot(slot);
        uiModel.asMap().clear();
        
        String redirectUrl = "/actors/doctor/" + doctor.getId();
		
		return "redirect:" + redirectUrl;
    }
	
	/**
	 * Private method to update the ui model with the given filter criteria
	 * @param uiModel the ui model
	 * @param slotFilter the example slot, used for findByExample
	 * @param status the status of the slot
	 */
	private void setupModel(Model uiModel, OPSlot slotFilter, OPSlotStatus status) {
		Doctor doctor = slotFilter.getDoctor();
		
		uiModel.addAttribute("opSlots", slotService.findByExample(slotFilter, status));
		uiModel.addAttribute("surgeryTypes", Arrays.asList(SurgeryType.values()));
		uiModel.addAttribute("statusList", Arrays.asList(OPSlotStatus.values()));
		uiModel.addAttribute("hospitals", Arrays.asList(doctor));
		uiModel.addAttribute("doctors", Arrays.asList(doctor));
		uiModel.addAttribute("dateFormat", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("slotFilter", slotFilter);
		uiModel.addAttribute("status", status);
	}
}
