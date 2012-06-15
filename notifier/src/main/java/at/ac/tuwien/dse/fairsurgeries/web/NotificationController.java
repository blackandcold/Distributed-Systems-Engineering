package at.ac.tuwien.dse.fairsurgeries.web;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.dse.fairsurgeries.domain.Notification;
import at.ac.tuwien.dse.fairsurgeries.domain.NotificationReason;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationFailedDTO;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationSuccessfulDTO;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
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
	@Autowired
	private LogEntryService logEntryService;

	/**
	 * This method listens to messages posted to the NotifierIn-Queue. 
	 * It expects the message to be an instance of ReservationSuccessfulDTO or ReservationFailedDTO
	 * and then persists a Notification object with the given information
	 * @param object the message posted into the queue
	 */
	public void handleNotification(Object object) {
		logEntryService.log(Constants.Component.Notifier.toString(), "Starting handleNotification()");
		logEntryService.log(Constants.Component.Notifier.toString(), "Notification is of type " + object.getClass().getSimpleName());

		// To be sure that we don't get an endless loop, catch all Exceptions here 
		try {
			if (object instanceof ReservationSuccessfulDTO) {
				ReservationSuccessfulDTO notificationDTO = (ReservationSuccessfulDTO) object;
				OPSlot slot = slotService.findOPSlot(notificationDTO.getSlot().getId());

				// If we found the given slot, persist a notification
				if (slot != null) {
					Notification notification = new Notification();

					notification.setReason(NotificationReason.ReservationSuccessful);
					notification.setDescription("Slot was reserved sucessfully");
					notification.setOpSlot(slot);

					notificationService.saveNotification(notification);
				}
			} else if (object instanceof ReservationFailedDTO) {
				ReservationFailedDTO notificationDTO = (ReservationFailedDTO) object;
				Notification notification = new Notification();

				notification.setReason(NotificationReason.ReservationFailed);
				notification.setDescription(notificationDTO.getReason());

				notificationService.saveNotification(notification);
			}

			logEntryService.log(Constants.Component.Notifier.toString(), "Finished handleNotification()");
		} catch (Exception ex) {
			logEntryService.log(Constants.Component.Notifier.toString(), ex.getMessage());
		}
	}
}
