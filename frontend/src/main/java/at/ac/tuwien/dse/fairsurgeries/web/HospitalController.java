package at.ac.tuwien.dse.fairsurgeries.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalServiceImpl;

@RequestMapping("/hospitals")
@Controller
@RooWebScaffold(path = "hospitals", formBackingObject = Hospital.class)
public class HospitalController {
	
	@Autowired AmqpTemplate amqpTemplate;
	
	@RequestMapping(value = "/matthias", produces = "application/json")
	public void matthias(HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		
		out.println("Matthias as a Service");
	}
	
	@RequestMapping(value = "/gerhard/{message}", produces = "text/html")
    public String gerhard(@PathVariable String message, Model uiModel) {
		
		Hospital hospital = new Hospital();
		hospital.setName(message);
		hospitalService.saveHospital(hospital);
		
        uiModel.addAttribute("myFreakinMessage", new String(message));
        uiModel.addAttribute("myFreakinTitle", new String("I can output whatever i want"));
        return "hospitals/gerhard";
    }
	
	@RequestMapping(value = "/sendMessage", produces="text/html")
    public String publish(Model model) {
        // Send a message to the "messages" queue
		String message = "i published da freakin message";
        amqpTemplate.convertAndSend("MatcherInQueue", message);
        model.addAttribute("published", true);
        model.addAttribute("message", message);
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
	
}

