package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;

@Controller
@RequestMapping("/actors/public")
public class ActorPublicPersonController {
	
	@Autowired
	private OPSlotService opSlotService;
	@Autowired
	private LogEntryService logEntryService;

	@RequestMapping(value="/slots", method = RequestMethod.GET, produces = "text/html")
	public String listSlots(Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPublicPersonController . listSlots()");
		uiModel.addAttribute("heading", "List all Slots (public)");
		uiModel.addAttribute("slots", opSlotService.findAllOPSlots());
		return "actors/public/slots";
	}
	
	/*@RequestMapping(value="/slots", method = RequestMethod.GET, produces = "text/html")
	public void listSlots(HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();

		out.println("feckin shit");
	}*/
}
