package at.ac.tuwien.dse.fairsurgeries.service;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.Notification;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;

public class NotificationServiceImpl implements NotificationService {
	
	public List<Notification> findByHospital(Hospital hospital) {
		List<Notification> allNotifications = notificationRepository.findAll();
		List<Notification> matchingNotifications = new ArrayList<Notification>(allNotifications);
		
		for(Notification notification:allNotifications) {
			if (notification.getOpSlot() == null) {
				matchingNotifications.remove(notification);
				continue;
			}
			if (notification.getOpSlot().getHospital() == null) {
				matchingNotifications.remove(notification);
				continue;
			}
			if (!notification.getOpSlot().getHospital().getId().equals(hospital.getId())) {
				matchingNotifications.remove(notification);
				continue;
			}
		}
		
		return matchingNotifications;
	}
	
	public List<Notification> findByDoctor(Doctor doctor) {
		List<Notification> allNotifications = notificationRepository.findAll();
		List<Notification> matchingNotifications = new ArrayList<Notification>(allNotifications);
		
		for(Notification notification:allNotifications) {
			if (notification.getOpSlot() == null) {
				matchingNotifications.remove(notification);
				continue;
			}
			if (notification.getOpSlot().getDoctor() == null) {
				matchingNotifications.remove(notification);
				continue;
			}
			if (!notification.getOpSlot().getDoctor().getId().equals(doctor.getId())) {
				matchingNotifications.remove(notification);
				continue;
			}
		}
		
		return matchingNotifications;
	}
	
	public List<Notification> findByPatient(Patient patient) {
		List<Notification> allNotifications = notificationRepository.findAll();
		List<Notification> matchingNotifications = new ArrayList<Notification>(allNotifications);
		
		for(Notification notification:allNotifications) {
			if (notification.getOpSlot() == null) {
				matchingNotifications.remove(notification);
				continue;
			}
			if (notification.getOpSlot().getPatient() == null) {
				matchingNotifications.remove(notification);
				continue;
			}
			if (!notification.getOpSlot().getPatient().getId().equals(patient.getId())) {
				matchingNotifications.remove(notification);
				continue;
			}
		}
		
		return matchingNotifications;
	}
}
