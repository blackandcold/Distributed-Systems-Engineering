package at.ac.tuwien.dse.fairsurgeries.web;

import java.io.PrintWriter;
import java.io.StringWriter;

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

	public void handleNotification(Object object) {
		logEntryService.log(Constants.Component.Notifier.toString(), "Starting handleNotification()");
		logEntryService.log(Constants.Component.Notifier.toString(), "Notification is of type " + object.getClass().getSimpleName());
		if (object instanceof ReservationSuccessfulDTO) {
			try {
				ReservationSuccessfulDTO notificationDTO = (ReservationSuccessfulDTO) object;
				
				if(notificationDTO == null)
					logEntryService.log(Constants.Component.Notifier.toString(), "notificationDTO ist null");
				if(notificationDTO.getSlot() == null)
					logEntryService.log(Constants.Component.Notifier.toString(), "notificationDTO.getSlot() ist null");
				OPSlot slot = slotService.findOPSlot(notificationDTO.getSlot().getId());
	
				if (slot != null) {
					Notification notification = new Notification();
	
					notification.setReason(NotificationReason.ReservationSuccessful);
					notification.setDescription("Slot was reserved sucessfully");
					notification.setOpSlot(slot);
					
					notificationService.saveNotification(notification);
				}
			} catch(Exception e) {
				logEntryService.log(Constants.Component.Notifier.toString(), "UIJE!!! " + e.getMessage());
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String stacktrace = sw.toString();
				logEntryService.log(Constants.Component.Matcher.toString(), stacktrace);
			}
		}
		else if (object instanceof ReservationFailedDTO) {
			ReservationFailedDTO notificationDTO = (ReservationFailedDTO) object;

			Notification notification = new Notification();

			notification.setReason(NotificationReason.ReservationFailed);
			notification.setDescription("Do hot irgendwo wos ned hinghaut");
			
			notificationService.saveNotification(notification);
		}
		logEntryService.log(Constants.Component.Notifier.toString(), "Finished handleNotification()");
	}
}
