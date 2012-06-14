package at.ac.tuwien.dse.fairsurgeries.service;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.Notification;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;

@RooService(domainTypes = { at.ac.tuwien.dse.fairsurgeries.domain.Notification.class })
public interface NotificationService {
	
	public List<Notification> findByHospital(Hospital hospital);
	public List<Notification> findByDoctor(Doctor doctor);
	public List<Notification> findByPatient(Patient patient);
}
