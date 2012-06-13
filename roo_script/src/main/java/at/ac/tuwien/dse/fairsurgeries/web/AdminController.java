package at.ac.tuwien.dse.fairsurgeries.web;

import at.ac.tuwien.dse.fairsurgeries.domain.Admin;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = Admin.class)
@Controller
@RequestMapping("/admins")
@RooWebScaffold(path = "admins", formBackingObject = Admin.class)
public class AdminController {
}
