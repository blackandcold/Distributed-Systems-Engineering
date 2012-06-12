package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;

@Controller
@RequestMapping("/actors")
public class LoginController {
	@Autowired
	private LogEntryService logEntryService;
	@Autowired
	private PatientService patientService;
	
	@RequestMapping(value="/login", method = RequestMethod.GET, produces = "text/html")
	public String showLoginView(Model uiModel) {
		logEntryService.clearLog();
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting LoginController . showLoginView()");
		uiModel.addAttribute("patient", new Patient());
		uiModel.addAttribute("patients", patientService.findAllPatients());
		return "actors/login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(@ModelAttribute Patient patient, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting LoginController . login()");
		logEntryService.log(Constants.Component.Frontend.toString(), "Patient: " + patient);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		return "redirect:/actors/patient/" + patient.getId() + "/slots";
	}
}
