package at.ac.tuwien.dse.fairsurgeries.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;

@RequestMapping("/hospitals")
@Controller
@RooWebScaffold(path = "hospitals", formBackingObject = Hospital.class)
public class HospitalController {
	
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
}

