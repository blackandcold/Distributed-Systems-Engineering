package at.ac.tuwien.dse.fairsurgeries.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;

@RooWebJson(jsonObject = Hospital.class)
@RequestMapping("/hospitals")
@Controller
@RooWebScaffold(path = "hospitals", formBackingObject = Hospital.class)
public class HospitalController {
}

