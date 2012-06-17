package at.ac.tuwien.dse.fairsurgeries.web.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.ac.tuwien.dse.fairsurgeries.domain.Admin;
import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.AdminService;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;

/**
 * The controller managing the login procedure
 */
@Controller
@RequestMapping("/actors")
public class LoginController {
	@Autowired
	private LogEntryService logEntryService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private AdminService adminService;
	
	/**
	 * This method updates the ui model to display a list of all possible actors we can login as.
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value="/login", method = RequestMethod.GET, produces = "text/html")
	public String showLoginView(Model uiModel) {
		logEntryService.clearLog();
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting LoginController . showLoginView()");
		
		uiModel.addAttribute("patient", new Patient());
		uiModel.addAttribute("patients", patientService.findAllPatients());
		uiModel.addAttribute("doctor", new Doctor());
		uiModel.addAttribute("doctors", doctorService.findAllDoctors());
		uiModel.addAttribute("hospital", new Hospital());
		uiModel.addAttribute("hospitals", hospitalService.findAllHospitals());
		uiModel.addAttribute("admin", new Admin());
		uiModel.addAttribute("admins", adminService.findAllAdmins());
		
		return "actors/login";
	}
	
	/**
	 * This method forwards to the public slots view
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value="/loginAsPublicPerson", method = RequestMethod.POST)
	public String loginAsPublicPerson(Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting LoginController . login() as public person");
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		
		return "redirect:/actors/public/slots";
	}
	
	/**
	 * This method forwards to the menu view of the given patient
	 * @param patient the patient we want to login as
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value="/loginAsPatient", method = RequestMethod.POST)
	public String loginAsPatient(@ModelAttribute Patient patient, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting LoginController . login() as patient: " + patient);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		
		return "redirect:/actors/patient/" + patient.getId();
	}
	
	/**
	 * This method forwards to the menu view of the given doctor
	 * @param doctor the doctor we want to login as
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value="/loginAsDoctor", method = RequestMethod.POST)
	public String loginAsDoctor(@ModelAttribute Doctor doctor, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting LoginController . login() as doctor: " + doctor);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		
		return "redirect:/actors/doctor/" + doctor.getId();
	}
	
	/**
	 * This method forwards to the menu view of the given hospital
	 * @param hospital the hospital we want to login as
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value="/loginAsHospital", method = RequestMethod.POST)
	public String loginAsHospital(@ModelAttribute Hospital hospital, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting LoginController . login() as hospital: " + hospital);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		
		return "redirect:/actors/hospital/" + hospital.getId();
	}
	
	/**
	 * This method forwards to the slots view of an admin
	 * @param admin the admin we want to login as
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value="/loginAsAdmin", method = RequestMethod.POST)
	public String loginAsAdmin(@ModelAttribute Admin admin, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting LoginController . login() as admin: " + admin);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		
		return "redirect:/actors/admin/slots";
	}
}
