package at.ac.tuwien.dse.fairsurgeries.web;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.dse.fairsurgeries.domain.Notification;
import at.ac.tuwien.dse.fairsurgeries.domain.NotificationReason;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationFailedDTO;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationSuccessfulDTO;
import at.ac.tuwien.dse.fairsurgeries.service.NotificationService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;

@Controller
public class NotificationController {
	@Autowired
	AmqpTemplate template;

	@Autowired
	private NotificationService notificationService;
	@Autowired
	private OPSlotService slotService;

	public void handleNotification(Object object) {
		if (object instanceof ReservationSuccessfulDTO) {
			ReservationSuccessfulDTO notificationDTO = (ReservationSuccessfulDTO) object;
			OPSlot slot = slotService.findOPSlot(notificationDTO.getSlot().getId());

			if (slot != null) {
				Notification notification = new Notification();

				notification.setReason(NotificationReason.ReservationSuccessful);
				notification.setDescription("Slot was reserved sucessfully");
				notification.setOpSlot(slot);
				
				notificationService.saveNotification(notification);
			}
		}

		else if (object instanceof ReservationFailedDTO) {
			ReservationFailedDTO notificationDTO = (ReservationFailedDTO) object;

			// TODO: What to do now?
		}
	}
}
