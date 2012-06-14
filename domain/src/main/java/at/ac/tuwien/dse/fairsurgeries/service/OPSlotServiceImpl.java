package at.ac.tuwien.dse.fairsurgeries.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlotStatus;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;

public class OPSlotServiceImpl implements OPSlotService {

	@Autowired
	LogEntryService logentryService;

	public List<OPSlot> findByExample(OPSlot slot) {
		return findByExample(slot, null);
	}
	
	public List<OPSlot> findByExample(OPSlot slot, OPSlotStatus status) {
		List<OPSlot> slots = oPSlotRepository.findAll();
		List<OPSlot> matchingSlots = new ArrayList<OPSlot>(slots);

		logentryService.log("slot", "All slots: " + slots);
		logentryService.log("slot", "Example: " + slot);

		if (slots != null && slot != null) {
			Hospital hospital = slot.getHospital();
			Patient patient = slot.getPatient();
			Doctor doctor = slot.getDoctor();
			Date from = slot.getDateFrom();
			Date to = slot.getDateTo();
			SurgeryType type = slot.getSurgeryType();

			for (OPSlot s : slots) {
				if (hospital != null && (s.getHospital() == null || !s.getHospital().getId().equals(hospital.getId()))) {
					matchingSlots.remove(s);
					continue;
				}

				if (patient != null && (s.getPatient() == null || !s.getPatient().getId().equals(patient.getId()))) {
					matchingSlots.remove(s);
					continue;
				}

				if (doctor != null && (s.getDoctor() == null || !s.getDoctor().getId().equals(doctor.getId()))) {
					matchingSlots.remove(s);
					continue;
				}

				if (from != null && !this.isDateEqualToDate(from, s.getDateFrom())) {
					matchingSlots.remove(s);
					continue;
				}

				if (to != null && !this.isDateEqualToDate(to, s.getDateTo())) {
					matchingSlots.remove(s);
					continue;
				}

				if (type != null && (s.getSurgeryType() == null || !s.getSurgeryType().equals(type))) {
					matchingSlots.remove(s);
					continue;
				}
				
				if (status != null && !s.getStatus().equals(status)) {
					matchingSlots.remove(s);
					continue;
				}
			}
		}

		logentryService.log("slot", "Matching Slots: " + matchingSlots);

		return matchingSlots;
	}

	public List<OPSlot> findByPatient(Patient patient) {
		OPSlot example = new OPSlot();

		example.setPatient(patient);
		return this.findByExample(example);
	}

	public List<OPSlot> findByDoctor(Doctor doctor) {
		OPSlot example = new OPSlot();

		example.setDoctor(doctor);
		return this.findByExample(example);
	}

	public List<OPSlot> findByHospital(Hospital hospital) {
		OPSlot example = new OPSlot();

		example.setHospital(hospital);
		return this.findByExample(example);
	}

	public List<OPSlot> findAllFreeSlotsByHospital(Hospital hospital) {
		List<OPSlot> slotsOfHospital = this.findByHospital(hospital);
		List<OPSlot> matchingSlots = new ArrayList<OPSlot>(slotsOfHospital);

		for (OPSlot slot : slotsOfHospital) {
			if (slot.getStatus().equals(OPSlotStatus.RESERVED))
				matchingSlots.remove(slot);
		}
		
		return matchingSlots;
	}
	
	
	private boolean isDateEqualToDate(Date d1, Date d2) {
		if (d1 == null && d2 == null) {
			return true;
		}
		
		if (d1 == null || d2 == null) {
			return false;
		}
		
		Calendar fromCalendar = Calendar.getInstance();
		Calendar sFromCalendar = Calendar.getInstance();
		
		fromCalendar.setTime(d1);
		sFromCalendar.setTime(d2);
		
		if (fromCalendar.get(Calendar.YEAR) != sFromCalendar.get(Calendar.YEAR) ||
			fromCalendar.get(Calendar.DAY_OF_YEAR) != sFromCalendar.get(Calendar.DAY_OF_YEAR) ||
			fromCalendar.get(Calendar.HOUR_OF_DAY) != sFromCalendar.get(Calendar.HOUR_OF_DAY) ||
			fromCalendar.get(Calendar.MINUTE) != sFromCalendar.get(Calendar.MINUTE)) {
			return false;
		} else {
			return true;
		}
	}
}
