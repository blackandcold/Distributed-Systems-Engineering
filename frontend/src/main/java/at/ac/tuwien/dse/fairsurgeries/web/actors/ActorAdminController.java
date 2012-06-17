package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONSerializer;

import at.ac.tuwien.dse.fairsurgeries.domain.Admin;
import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlotStatus;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.AdminService;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.NotificationService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;

/*
import com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;
*/

/**
 * The controller managing all requests for the actor role "Admin"
 */
@Controller
@RequestMapping("/actors/admin")
public class ActorAdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private LogEntryService logEntryService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private OPSlotService opSlotService;
	@Autowired
	private PatientService patientService;
	

	/**
	 * This method updates the ui model to show a list of all slots.
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/slots", method = RequestMethod.GET, produces = "text/html")
	public String listSlots(Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorAdminController . listSlots()");

		this.setupModel(uiModel, new OPSlot(), null);

		return "actors/admin/slots";
	}

	/**
	 * This method updates the ui model to show a lis of all slots matching the given criteria
	 * @param opSlot the example slot (= filter criteria)
	 * @param uiModel the ui model
	 * @param request the request object
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/slots", method = RequestMethod.POST, produces = "text/html")
	public String listFilteredSlots(@ModelAttribute OPSlot opSlot, Model uiModel, ServletRequest request) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorAdminController . listFilteredSlots() example=" + opSlot);

		String status = request.getParameter("status");
		if (status == null || status.isEmpty()) {
			this.setupModel(uiModel, opSlot, null);
		} else {
			this.setupModel(uiModel, opSlot, OPSlotStatus.valueOf(status));
		}

		return "actors/admin/slots";
	}
	
	/**
	 * Private method to update the ui model with the given filter criteria
	 * @param uiModel the ui model
	 * @param slotFilter the example slot, used for findByExample
	 * @param status the status of the slot
	 */
	private void setupModel(Model uiModel, OPSlot slotFilter, OPSlotStatus status) {
		uiModel.addAttribute("opSlots", opSlotService.findByExample(slotFilter));
		uiModel.addAttribute("surgeryTypes", Arrays.asList(SurgeryType.values()));
		uiModel.addAttribute("statusList", Arrays.asList(OPSlotStatus.values()));
		uiModel.addAttribute("hospitals", hospitalService.findAllHospitals());
		uiModel.addAttribute("doctors", doctorService.findAllDoctors());
		uiModel.addAttribute("patients", patientService.findAllPatients());
		uiModel.addAttribute("dateFormat", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("slotFilter", slotFilter);
		uiModel.addAttribute("status", status);
	}
	
	
	/* REST */
	
	/**
	 * Lists all slots
	 *
	 * @return Returns 
	 * 				JSON header and content of the result
	 */
	@RequestMapping(value = "/listSlotsJSON/", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listSlotsJSON(Model model) {
    	logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorAdminController . listSlotsJSON()");

    	List<OPSlot> opSlots;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
    	
        OPSlot exampleSlot = new OPSlot();        
        
		opSlots = opSlotService.findByExample(exampleSlot);
		
    	//Generate JSON
    	JSONSerializer serializer = new JSONSerializer();
        String outputJSON = serializer.serialize(opSlots);
        
    	//Output header with content
        return new ResponseEntity<String>(outputJSON, headers, HttpStatus.OK);
    }	
	
	/**
	 * ru
	 *
	 * @return Returns 
	 * 				JSON header and content of the result
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> test(Model model) {
    	logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorAdminController . test()");
        
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
    	
    	
    	//String json = get("dse_frontend.cloudfoundry.com/actors/public/listSlotsJSON/dateFrom/01-01-1980/dateTo/16-06-2012").asString();
        
    	
    	
    	//Output header with content
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }	
	
	
	/**
	 * @param uiModel
	 * @return returns just a http OK status
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.GET, produces = "text/json")
	public ResponseEntity<String> resetDatabase(Model uiModel) {
		logEntryService.clearLog();
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorAdminController . resetDatabase() resetting all data to default dummy content");

		/* Datenbank leeren */
		notificationService.deleteAllNotifications();
		opSlotService.deleteAllOPSlots();
		adminService.deleteAllAdmins();
		doctorService.deleteAllDoctors();
		hospitalService.deleteAllHospitals();
		patientService.deleteAllPatients();
		
		/* Testdaten anlegen */
		patientService.savePatient(new Patient("Franz", "Meier"));
		patientService.savePatient(new Patient("Maria", "Müller"));
		patientService.savePatient(new Patient("Martin", "Moser"));
		patientService.savePatient(new Patient("Beate", "Bauer"));
		patientService.savePatient(new Patient("Ben", "Bäcker"));
		patientService.savePatient(new Patient("Gloria", "Glaser"));
		
		doctorService.saveDoctor(new Doctor("Dr.", "Hochweiß"));
		doctorService.saveDoctor(new Doctor("Dr.", "Gott"));
		doctorService.saveDoctor(new Doctor("Dr.", "Aufmesser"));
		doctorService.saveDoctor(new Doctor("Dr.", "Augenschein"));
		
		Hospital akh = new Hospital("AKH Wien");
		hospitalService.saveHospital(akh);
		hospitalService.saveHospital(new Hospital("Barmherziger Brüder"));
		hospitalService.saveHospital(new Hospital("LKH Klosterneuburg"));
		hospitalService.saveHospital(new Hospital("LKH Tulln"));
		
		adminService.saveAdmin(new Admin("Frank", "Nerd"));
		adminService.saveAdmin(new Admin("Steve", "Geek"));
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		try {
			opSlotService.saveOPSlot(new OPSlot(akh, formatter.parse("01.07.2012 12:00"), formatter.parse("01.07.2012 14:00")));
			opSlotService.saveOPSlot(new OPSlot(akh, formatter.parse("01.07.2012 15:00"), formatter.parse("01.07.2012 18:00")));
			opSlotService.saveOPSlot(new OPSlot(akh, formatter.parse("01.07.2012 19:00"), formatter.parse("01.07.2012 21:00")));
			opSlotService.saveOPSlot(new OPSlot(akh, formatter.parse("02.07.2012 07:00"), formatter.parse("02.07.2012 10:00")));
			opSlotService.saveOPSlot(new OPSlot(akh, formatter.parse("01.07.2012 11:00"), formatter.parse("01.07.2012 13:00")));
			opSlotService.saveOPSlot(new OPSlot(akh, formatter.parse("01.07.2012 14:00"), formatter.parse("01.07.2012 18:00")));
		} catch (ParseException e) {
			// dann werden eben keine OP-Slots angelegt
		}
		
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<String>("", headers, HttpStatus.OK);

	}	
	
}
