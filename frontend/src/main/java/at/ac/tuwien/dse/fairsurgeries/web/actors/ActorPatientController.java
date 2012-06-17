package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONSerializer;

import at.ac.tuwien.dse.fairsurgeries.domain.Notification;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlotStatus;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.NotificationService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;

/**
 * The controller managing all requests for the actor role "Patient"
 */
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
	@Autowired
	private NotificationService notificationService;

	/**
	 * This method logs in as as the patient with the given id and shows the
	 * menu of possible actions if successful.
	 * 
	 * @param patientId
	 *            the id of the patient we want to login as
	 * @param uiModel
	 *            the ui model object
	 * @return identifier of the next page we want to visit
	 */
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

	/**
	 * This method updates the ui model to show a list of slots matching the
	 * given criteria.
	 * 
	 * @param patient
	 *            the logged in patient
	 * @param opSlot
	 *            the example slot
	 * @param uiModel
	 *            the ui model
	 * @param request
	 *            the request
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/slots", method = RequestMethod.POST)
	public String listSlots(@ModelAttribute Patient patient, @ModelAttribute OPSlot opSlot, Model uiModel, ServletRequest request) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPatient . listFilteredSlots() for patient + " + patient);
		logEntryService.log(Constants.Component.Frontend.toString(), "slot: " + (opSlot == null ? "null" : opSlot));

		if (patient != null) {
			opSlot.setPatient(patient);
		}

		String status = request.getParameter("status");
		if (status == null || status.isEmpty())
			this.setupModel(uiModel, opSlot, null);
		else
			this.setupModel(uiModel, opSlot, OPSlotStatus.valueOf(status));

		return "actors/patient/slots";
	}

	/**
	 * This method updates the ui model to show the list of notifications for
	 * the logged in patient.
	 * 
	 * @param patient
	 *            the logged in patient
	 * @param uiModel
	 *            the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/notifications", method = RequestMethod.POST)
	public String listNotifications(@ModelAttribute Patient patient, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPatient. viewNotifications() for patient: " + patient);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);

		List<Notification> notifications = notificationService.findByPatient(patient);
		uiModel.addAttribute("notifications", notifications);

		return "actors/patient/notifications";
	}

	/**
	 * Private method to update the ui model with the given filter criteria
	 * 
	 * @param uiModel
	 *            the ui model
	 * @param slotFilter
	 *            the example slot, used for findByExample
	 * @param status
	 *            the status of the slot
	 */
	private void setupModel(Model uiModel, OPSlot slotFilter, OPSlotStatus status) {
		Patient patient = slotFilter.getPatient();

		uiModel.addAttribute("opSlots", opSlotService.findByExample(slotFilter, status));
		uiModel.addAttribute("surgeryTypes", Arrays.asList(SurgeryType.values()));
		uiModel.addAttribute("statusList", Arrays.asList(OPSlotStatus.values()));
		uiModel.addAttribute("hospitals", hospitalService.findAllHospitals());
		uiModel.addAttribute("doctors", doctorService.findAllDoctors());
		uiModel.addAttribute("patients", Arrays.asList(patient));
		uiModel.addAttribute("dateFormat", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("slotFilter", slotFilter);
		uiModel.addAttribute("status", status);
	}

	/**
	 * @param patientId the ID of the Patient
	 * @return Returns JSON Header and Content of the Result
	 */
	@RequestMapping(value = "/listSlotsJSON/{patientId}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listSlotsJSON(@PathVariable BigInteger patientId, Model model) {
    	logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPatient . listSlotsJSON()");

    	List<OPSlot> opSlots;
    	Patient patient;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
    	
    	if(patientId != null)
    		patient = patientService.findPatient(patientId);
    	else
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);

        if(patient == null)
        	return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            
        //set the example to fetch the slots
    	OPSlot exampleSlot = new OPSlot();
    	exampleSlot.setPatient(patient);
    	   	
		logEntryService.log(Constants.Component.Frontend.toString(), "Example generated . listSlotsJSON(): " + exampleSlot);
		opSlots = opSlotService.findByExample(exampleSlot);

    	//Generate JSON
    	JSONSerializer serializer = new JSONSerializer();
        String outputJSON = serializer.serialize(opSlots);
        
    	//Output header with content
        return new ResponseEntity<String>(outputJSON, headers, HttpStatus.OK);
    }
	
	/**
	 * @param patientId the ID of the Patient
	 * @return Returns JSON Header and Content of the Result
	 */
	@RequestMapping(value = "/listNotificationsJSON/{patientId}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listNotificationsJSON(@PathVariable BigInteger patientId, Model model) {
    	logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPatient . listNotificationsJSON() with id: " + patientId);

    	Patient patient;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
    	
    	if(patientId != null)
    		patient = patientService.findPatient(patientId);
    	else
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);

        if(patient == null)
        	return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
 
		List<Notification> notifications = notificationService.findByPatient(patient);


    	//Generate JSON
    	JSONSerializer serializer = new JSONSerializer();
        String outputJSON = serializer.serialize(notifications);
        
    	//Output header with content
        return new ResponseEntity<String>(outputJSON, headers, HttpStatus.OK);
    }
	
}