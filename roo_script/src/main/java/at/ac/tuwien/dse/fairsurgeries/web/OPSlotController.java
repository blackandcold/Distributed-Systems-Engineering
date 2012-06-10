package at.ac.tuwien.dse.fairsurgeries.web;

import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/opslots")
@Controller
@RooWebScaffold(path = "opslots", formBackingObject = OPSlot.class)
public class OPSlotController {
}
