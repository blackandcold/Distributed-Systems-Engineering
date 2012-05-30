package at.ac.tuwien.dse.fairsurgeries.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/my")
@Controller
public class MyController {
	
	/*@Autowired AmqpTemplate amqpTemplate;
	
	@RequestMapping(value = "/")
    public String home(Model model) {
        return "message_test";
    }
	
	@RequestMapping(value = "/sendMessage", method=RequestMethod.GET)
    public String publish(Model model) {
        // Send a message to the "messages" queue
        amqpTemplate.convertAndSend("MatcherInQueue", "This is a test message");
        model.addAttribute("published", true);
        return home(model);
    }
	
	@RequestMapping(value = "/receiveMessage", method=RequestMethod.GET)
    public String get(Model model) {
        // Receive a message from the "messages" queue
        String message = (String)amqpTemplate.receiveAndConvert("MatcherInQueue");
        if (message != null)
            model.addAttribute("got", message);
        else
            model.addAttribute("got_queue_empty", true);

        return home(model);
    }*/
}
