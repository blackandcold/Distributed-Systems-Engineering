package at.ac.tuwien.dse.fairsurgeries.web;

import at.ac.tuwien.dse.fairsurgeries.domain.Reservation;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = Reservation.class)
@Controller
@RequestMapping("/reservations")
@RooWebScaffold(path = "reservations", formBackingObject = Reservation.class)
public class ReservationController {
}
