package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.ac.tuwien.dse.fairsurgeries.dto.ReservationDTO;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;

@Controller
@RequestMapping("/actors/doctor")
public class ActorDoctorController {
	
	@Autowired
	private OPSlotService opSlotService;
	@Autowired
	private LogEntryService logEntryService;

	@RequestMapping(value="/reservations", method = RequestMethod.GET, produces = "text/html")
	public String manageReservation(Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorDoctorController . manageReservations()");
		//uiModel.addAttribute("heading", "List all feckin Slots");
		//uiModel.addAttribute("slots", opSlotService.findAllOPSlots());
		ReservationDTO reservation = new ReservationDTO(null, null, null, 27., null, null);
		uiModel.addAttribute("reservation", reservation);
		return "actors/doctor/reservations";
	}
	
	@RequestMapping(value="/doreservation", method = RequestMethod.POST, produces = "text/html")
	public String doReservation(@Valid ReservationDTO reservation, @Valid double radius, BindingResult bindingResult, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorDoctorController . doReservation()");
		//uiModel.addAttribute("heading", "List all feckin Slots");
		//uiModel.addAttribute("slots", opSlotService.findAllOPSlots());
		/*for(String key : uiModel.asMap().keySet()) {
			
		}*/
		logEntryService.log(Constants.Component.Frontend.toString(), "Popo: " + radius);
		
		if(bindingResult.hasErrors())
			logEntryService.log(Constants.Component.Frontend.toString(), "do hots wos");
		else
			logEntryService.log(Constants.Component.Frontend.toString(), "passt eh ois");
		
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel.asMap().toString());
		
		for(String key : uiModel.asMap().keySet()) {
			logEntryService.log(Constants.Component.Frontend.toString(), "key: " + key + "=" + uiModel.asMap().get(key));
		}
		
		//logEntryService.log(Constants.Component.Frontend.toString(), message)
		//ReservationDTO reservation = (ReservationDTO)uiModel.asMap().get("reservation");
		if(reservation != null)
			logEntryService.log(Constants.Component.Frontend.toString(), "Received POST: radius = " + reservation.getRadius());
		else
			logEntryService.log(Constants.Component.Frontend.toString(), "Reservation is null");
		return "actors/doctor/reservations";
	}
}
