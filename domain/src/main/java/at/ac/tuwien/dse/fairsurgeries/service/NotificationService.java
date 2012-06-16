package at.ac.tuwien.dse.fairsurgeries.service;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.Notification;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;

@RooService(domainTypes = { at.ac.tuwien.dse.fairsurgeries.domain.Notification.class })
public interface NotificationService {
	
	/**
	 * Returns a list of all Notifications for the given hospital.
	 * @param hospital the hospital the notifications belong to
	 * @return list of notifications
	 */
	public List<Notification> findByHospital(Hospital hospital);
	
	/**
	 * Returns a list of all Notifications for the given doctor.
	 * @param doctor the doctor the notification belong to
	 * @return list of notifications
	 */
	public List<Notification> findByDoctor(Doctor doctor);
	
	/**
	 * Returns a list of all Notifications for the given patient.
	 * @param patient the patient the notification belong to
	 * @return list of notifications
	 */
	public List<Notification> findByPatient(Patient patient);
	
	/**
	 * Removes all notifications from the database
	 */
	public void deleteAllNotifications();
}
