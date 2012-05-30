package at.ac.tuwien.dse.fairsurgeries.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/fairsurgeries/public")
@Controller
public class MyPublicPersonController {
	
	@RequestMapping(value = "/show", produces = "text/html")
	public String show() throws IOException {
		return "fairsurgeries/public/show";
	}
}
