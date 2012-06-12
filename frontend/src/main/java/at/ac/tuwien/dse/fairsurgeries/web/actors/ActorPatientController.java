package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.io.PrintWriter;
import java.math.BigInteger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.NotificationService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;

@Controller
@RequestMapping("/actors/patient")
public class ActorPatientController {

	@Autowired
	private PatientService patientService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private LogEntryService logEntryService;

	@RequestMapping(value = "{patientId}/slots", method = RequestMethod.GET, produces = "text/html")
	public String listSlots(@PathVariable BigInteger patientId, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPatient.listSlots() for ID: " + patientId);

		Patient patient = patientService.findPatient(patientId);

		if (patient != null) {
			uiModel.addAttribute("heading", "Slots of Patient " + patient.getLastName());
			uiModel.addAttribute("slots", patient.getOpSlots());

			return "actors/public/slots";
		} else {
			return "redirect:/resourceNotFound";
		}
	}
	
	@RequestMapping(value = "{patientId}/test", method = RequestMethod.GET, produces = "text/html")
	public void test(@PathVariable BigInteger patientId, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();

		out.println("Patient id is " + patientId);
	}

	@RequestMapping(value = "/notifications", method = RequestMethod.GET, produces = "text/html")
	public String listNotifications(Model uiModel, BindingResult binding) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPatient . listNotifications()");
		uiModel.addAttribute("heading", "List all Notifications (private)");
		uiModel.addAttribute("notifications", notificationService.findAllNotifications());
		return "actors/public/notifications";
	}

}