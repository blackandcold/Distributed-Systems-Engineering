package at.ac.tuwien.dse.fairsurgeries.web;

import at.ac.tuwien.dse.fairsurgeries.domain.PublicPerson;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = PublicPerson.class)
@Controller
@RequestMapping("/publicpeople")
@RooWebScaffold(path = "publicpeople", formBackingObject = PublicPerson.class)
public class PublicPersonController {
}
