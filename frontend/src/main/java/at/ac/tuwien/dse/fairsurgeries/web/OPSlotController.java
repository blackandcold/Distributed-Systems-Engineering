package at.ac.tuwien.dse.fairsurgeries.web;

import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = OPSlot.class)
@Controller
@RequestMapping("/opslots")
@RooWebScaffold(path = "opslots", formBackingObject = OPSlot.class)
public class OPSlotController {
}
