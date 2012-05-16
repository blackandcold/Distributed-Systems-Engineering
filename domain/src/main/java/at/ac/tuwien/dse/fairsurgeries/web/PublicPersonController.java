package at.ac.tuwien.dse.fairsurgeries.web;

import at.ac.tuwien.dse.fairsurgeries.domain.PublicPerson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/publicpeople")
@Controller
@RooWebScaffold(path = "publicpeople", formBackingObject = PublicPerson.class)
public class PublicPersonController {
}
