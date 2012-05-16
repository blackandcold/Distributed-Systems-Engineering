package at.ac.tuwien.dse.fairsurgeries.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/my/**")
@Controller
public class MyController {
	@RequestMapping
	public void get(HttpServletResponse response) throws IOException {
		response.getWriter().println("Hello World from MyController!");
	}
}
