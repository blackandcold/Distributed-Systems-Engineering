package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;

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
		return "actors/login";
	}
	
	@RequestMapping(value="/loginAsPublicPerson", method = RequestMethod.POST)
	public String loginAsPublicPerson(Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting LoginController . login() as public person");
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		return "redirect:/actors/public/slots";
	}
	
	@RequestMapping(value="/loginAsPatient", method = RequestMethod.POST)
	public String loginAsPatient(@ModelAttribute Patient patient, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting LoginController . login() as patient: " + patient);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		return "redirect:/actors/patient/" + patient.getId() + "/slots";
	}
	
	@RequestMapping(value="/loginAsDoctor", method = RequestMethod.POST)
	public String loginAsPatient(@ModelAttribute Doctor doctor, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting LoginController . login() as doctor: " + doctor);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		return "redirect:/actors/doctor/" + doctor.getId() + "/slots";
	}
	
	@RequestMapping(value="/loginAsHospital", method = RequestMethod.POST)
	public String loginAsHospital(@ModelAttribute Hospital hospital, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting LoginController . login() as hospital: " + hospital);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		return "redirect:/actors/hospital/" + hospital.getId() + "/slots";
	}
}
