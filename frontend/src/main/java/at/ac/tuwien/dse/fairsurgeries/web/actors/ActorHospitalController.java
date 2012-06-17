package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
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
import at.ac.tuwien.dse.fairsurgeries.web.MessageController;

/**
 * The controller managing all requests for the actor role "Hospital"
 */
@Controller
@RequestMapping("/actors/hospital")
public class ActorHospitalController {

	@Autowired
	private LogEntryService logEntryService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private OPSlotService slotService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private NotificationService notificationService;

	/**
	 * This method logs in as as the hospital with the given id and shows the
	 * menu of possible actions if successful.
	 * 
	 * @param hospitalId
	 *            the id of the hospital we want to login as
	 * @param uiModel
	 *            the ui model object
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "{hospitalId}", method = RequestMethod.GET)
	public String showMenu(@PathVariable BigInteger hospitalId, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . () for ID: " + hospitalId);
		Hospital hospital = hospitalService.findHospital(hospitalId);

		if (hospital != null) {
			uiModel.addAttribute("hospital", hospital);

			return "actors/hospital/showmenu";
		} else {
			return "redirect:/resourceNotFound";
		}
	}

	/**
	 * This method updates the ui model to show a list of slots matching the
	 * given criteria.
	 * 
	 * @param hospital
	 *            the logged in hospital
	 * @param opSlot
	 *            the example slot
	 * @param uiModel
	 *            the ui model
	 * @param request
	 *            the request
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/slots", method = RequestMethod.POST)
	public String listSlots(@ModelAttribute Hospital hospital, @ModelAttribute OPSlot opSlot, Model uiModel, ServletRequest request) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . listFilteredSlots() for hospital + " + hospital);
		logEntryService.log(Constants.Component.Frontend.toString(), "slot: " + (opSlot == null ? "null" : opSlot));

		if (hospital != null) {
			opSlot.setHospital(hospital);
		}

		String status = request.getParameter("status");
		if (status == null || status.isEmpty())
			this.setupModel(uiModel, opSlot, null);
		else
			this.setupModel(uiModel, opSlot, OPSlotStatus.valueOf(status));

		return "actors/hospital/slots";
	}

	/**
	 * This method updates the ui model to show the list of notifications for
	 * the logged in hospital.
	 * 
	 * @param hospital
	 *            the logged in hospital
	 * @param uiModel
	 *            the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/notifications", method = RequestMethod.POST)
	public String listNotifications(@ModelAttribute Hospital hospital, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . viewNotifications() for hospital: " + hospital);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);

		List<Notification> notifications = notificationService.findByHospital(hospital);
		uiModel.addAttribute("notifications", notifications);

		return "actors/hospital/notifications";
	}

	/**
	 * Updates the ui model to display a screen where a hospital can create new slots or delete slots
	 * @param hospital the logged in hospital
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/manageslots", method = RequestMethod.POST)
	public String manageSlots(@ModelAttribute Hospital hospital, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . manageSlots() for hospital: " + hospital);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		
		OPSlot opSlot = new OPSlot();
		opSlot.setHospital(hospital);
		uiModel.addAttribute("opSlot", opSlot);

		List<Hospital> hospitalList = new ArrayList<Hospital>();
		hospitalList.add(hospital);
		
		uiModel.addAttribute("hospitals", hospitalList);
		uiModel.addAttribute("dateFormat", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("slotsDeleteList", slotService.findAllFreeSlotsByHospital(hospital));

		return "actors/hospital/manageslots";
	}

	/**
	 * Saves a new OPSlot in the database and redirects to the menu of the logged in hospital
	 * @param opSlot the slot to persist
	 * @param hospital the logged in hospital
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/createslot", method = RequestMethod.POST)
	public String createSlot(@ModelAttribute OPSlot opSlot, @ModelAttribute Hospital hospital, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . createSlot() for opSlot: " + opSlot);

		slotService.saveOPSlot(opSlot);

		String redirectUrl = "/actors/hospital/" + opSlot.getHospital().getId();
		logEntryService.log(Constants.Component.Frontend.toString(), "Redirecting to: " + redirectUrl);

		return "redirect:" + redirectUrl;
	}

	/**
	 * Deletes the given slot and redirects to the menu of the logged in hospital
	 * @param opSlot the slot to delete
	 * @param hospital the logged in hospital
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/deleteslot", method = RequestMethod.POST)
	public String deleteSlot(@ModelAttribute OPSlot opSlot, @ModelAttribute Hospital hospital, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . deleteSlot() for opSlot: " + opSlot);

		slotService.deleteOPSlot(opSlot);

		String redirectUrl = "/actors/hospital/" + opSlot.getHospital().getId();
		logEntryService.log(Constants.Component.Frontend.toString(), "Redirecting to: " + redirectUrl);

		return "redirect:" + redirectUrl;
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
		Hospital hospital = slotFilter.getHospital();

		uiModel.addAttribute("opSlots", slotService.findByExample(slotFilter, status));
		uiModel.addAttribute("surgeryTypes", Arrays.asList(SurgeryType.values()));
		uiModel.addAttribute("statusList", Arrays.asList(OPSlotStatus.values()));
		uiModel.addAttribute("hospitals", Arrays.asList(hospital));
		uiModel.addAttribute("doctors", doctorService.findAllDoctors());
		uiModel.addAttribute("dateFormat", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("slotFilter", slotFilter);
		uiModel.addAttribute("status", status);
	}
	
	/* REST */
	
	/**
	 * list all notifications
	 * 
	 * @param hospitalId 
	 * 				the ID of the actor
	 * @return Returns 
	 * 				JSON Header and Content of the Result
	 */
	@RequestMapping(value = "/listNotificationsJSON/{hospitalId}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listNotificationsJSON(@PathVariable BigInteger hospitalId, Model model) {
    	logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . listNotificationsJSON()");

    	Hospital hospital;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
    	
    	if(hospitalId != null)
    		hospital = hospitalService.findHospital(hospitalId);
    	else
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);

        if(hospital == null)
        	return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
 
		List<Notification> notifications = notificationService.findByHospital(hospital);

    	//Generate JSON
    	JSONSerializer serializer = new JSONSerializer();
        String outputJSON = serializer.serialize(notifications);
        
    	//Output header with content
        return new ResponseEntity<String>(outputJSON, headers, HttpStatus.OK);
    }
	
	/**
	 * Lists all slots for a actor
	 * 
	 * @param hospitalId 
	 * 				the ID of the actor
	 * @return Returns 
	 * 				JSON Header and Content of the Result
	 */
	@RequestMapping(value = "/listSlotsJSON/{hospitalId}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listSlotsJSON(@PathVariable BigInteger hospitalId, Model model) {
    	logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . listSlotsJSON()");

    	Hospital hospital;
    	List<OPSlot> opSlots;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
    	
    	if(hospitalId != null)
    		hospital = hospitalService.findHospital(hospitalId);
    	else
            return new ResponseEntity<String>(headers, HttpStatus.BAD_REQUEST);

        if(hospital == null)
        	return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
 
        OPSlot exampleSlot = new OPSlot();
        exampleSlot.setHospital(hospital);
        
        
		opSlots = slotService.findByExample(exampleSlot);
		
    	//Generate JSON
    	JSONSerializer serializer = new JSONSerializer();
        String outputJSON = serializer.serialize(opSlots);
        
    	//Output header with content
        return new ResponseEntity<String>(outputJSON, headers, HttpStatus.OK);
    }	
	
	/**
	 * this method creates a slot
	 * 
	 * @param OPSlot 
	 * 				JSON entity of type OPSlot
	 * @return Returns 
	 * 				JSON Header and Content of the Result
	 */
	@RequestMapping(value = "/createSlotJSON/", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> createSlotJSON(@RequestParam("opslot") String opSlot, Model model) {
    	logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . createSlotJSON()");

        HttpHeaders headers = new HttpHeaders();
        OPSlot opSlotEntity = new JSONDeserializer<OPSlot>().deserialize( opSlot );
        
        headers.add("Content-Type", "application/json; charset=utf-8");

        if(opSlotEntity == null)
        	return new ResponseEntity<String>(headers, HttpStatus.BAD_REQUEST);
        else
        	slotService.saveOPSlot(opSlotEntity);
        
    	//Output header with content
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }	
	
	/**
	 * this method delets a slot
	 * 
	 * @param OPSlot 
	 * 				JSON entity of type OPSlot
	 * @return Returns 
	 * 				JSON Header and Content of the Result
	 */
	@RequestMapping(value = "/deleteSlotJSON/", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> deleteSlotJSON(@RequestParam("opslot") String opSlot, Model model) {
    	logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . deleteSlotJSON()");

        HttpHeaders headers = new HttpHeaders();
        OPSlot opSlotEntity = new JSONDeserializer<OPSlot>().deserialize( opSlot );
        
        headers.add("Content-Type", "application/json; charset=utf-8");

        if(opSlotEntity == null)
        	return new ResponseEntity<String>(headers, HttpStatus.BAD_REQUEST);
        else
        	slotService.deleteOPSlot(opSlotEntity);
        
    	//Output header with content
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }	
}
