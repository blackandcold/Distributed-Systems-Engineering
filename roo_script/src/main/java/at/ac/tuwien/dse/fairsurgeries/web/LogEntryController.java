package at.ac.tuwien.dse.fairsurgeries.web;

import at.ac.tuwien.dse.fairsurgeries.domain.LogEntry;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/logentrys")
@Controller
@RooWebScaffold(path = "logentrys", formBackingObject = LogEntry.class)
public class LogEntryController {
}
