package at.ac.tuwien.dse.fairsurgeries.web;

import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.dto.DoctorDTO;
import at.ac.tuwien.dse.fairsurgeries.dto.PatientDTO;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationDTO;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;

@RooWebJson(jsonObject = Hospital.class)
@RequestMapping("/hospitals")
@Controller
@RooWebScaffold(path = "hospitals", formBackingObject = Hospital.class)
public class HospitalController {

	@Autowired 
	AmqpTemplate amqpTemplate;
	
	@Autowired
	DoctorService doctorService;
	@Autowired
	PatientService patientService;
	@Autowired
	OPSlotService slotService;
	

	@RequestMapping(value = "/matthias", produces = "application/json")
	public void matthias(HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();

		out.println("Matthias as a Service");
	}

	@RequestMapping(value = "/gerhard/{message}", produces = "text/html")
	public String gerhard(@PathVariable String message, Model uiModel) {
		uiModel.addAttribute("myFreakinMessage", new String(message));
		uiModel.addAttribute("myFreakinTitle", new String("I can output whatever i want"));
		return "hospitals/gerhard";
	}

	@RequestMapping(value = "/sendMessage", produces="text/html")
	public String publish(Model model) {
		// TODO: temporary because addSlot website doesn't work
		if (slotService.findAllOPSlots().isEmpty()) {
			Hospital hospital = hospitalService.findAllHospitals().get(0);
			OPSlot slot = new OPSlot();
			
			slot.setSurgeryType(SurgeryType.Cardiology);
			slot.setDateFrom(Date.valueOf("2012-04-12"));
			slot.setDateTo(Date.valueOf("2012-04-13"));
			slot.setHospital(hospital);
		
			slotService.saveOPSlot(slot);
		}
		
		// Sample code to post a reservation
		DoctorDTO doctorDTO = new DoctorDTO(doctorService.findAllDoctors().get(0).getId());
		PatientDTO patientDTO = new PatientDTO(patientService.findAllPatients().get(0).getId());
		ReservationDTO reservationDTO = new ReservationDTO(doctorDTO, patientDTO, SurgeryType.Cardiology, 50., Date.valueOf("2012-01-01"), Date.valueOf("2012-12-31"));
		
		// send persistent message
		MessageController.sendMessage(amqpTemplate, Constants.Queue.MatcherIn, reservationDTO);
		
        return "hospitals/message_test";
    }
	

	@RequestMapping(value = "/receiveMessage", produces="text/html")
	public String get(Model model) {
		// Receive a message from the "messages" queue
		String message = (String)amqpTemplate.receiveAndConvert("MatcherInQueue");
		if (message != null)
			model.addAttribute("message", message);
		else
			model.addAttribute("got_queue_empty", true);

		return "hospitals/message_test";
	}

	/**
	 * This method is invoked when a RabbitMQ Message is received.
	 */
	public void handleMessage(String message) {
		Hospital hospital = new Hospital();
		hospital.setName(message);
		hospitalService.saveHospital(hospital);
	}

}

