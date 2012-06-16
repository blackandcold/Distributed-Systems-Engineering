package at.ac.tuwien.dse.fairsurgeries.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlotStatus;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;

public class OPSlotServiceImpl implements OPSlotService {

	public List<OPSlot> findByExample(OPSlot slot) {
		return findByExample(slot, null);
	}
	
	public List<OPSlot> findByExample(OPSlot slot, OPSlotStatus status) {
		// We sort by dateFrom, dateTo and surgeryType
		Sort sort = new Sort(Sort.Direction.ASC, "dateFrom", "dateTo", "surgeryType");
		Iterable<OPSlot> slots = oPSlotRepository.findAll(sort);
		List<OPSlot> matchingSlots = new ArrayList<OPSlot>();

		// check if everything is valid
		if (slots != null && slot != null) {
			Hospital hospital = slot.getHospital();
			Patient patient = slot.getPatient();
			Doctor doctor = slot.getDoctor();
			Date from = slot.getDateFrom();
			Date to = slot.getDateTo();
			SurgeryType type = slot.getSurgeryType();

			// we match each set criteria, and if one doesn't match we don't add the slot
			// to our list of matching slots
			for (OPSlot s : slots) {
				boolean didMatch = true;
				
				if (hospital != null && (s.getHospital() == null || !s.getHospital().getId().equals(hospital.getId()))) {
					didMatch = false;
				}

				else if (patient != null && (s.getPatient() == null || !s.getPatient().getId().equals(patient.getId()))) {
					didMatch = false;
				}

				else if (doctor != null && (s.getDoctor() == null || !s.getDoctor().getId().equals(doctor.getId()))) {
					didMatch = false;
				}

				else if (from != null && !this.isDateEqualToDate(from, s.getDateFrom())) {
					didMatch = false;
				}

				else if (to != null && !this.isDateEqualToDate(to, s.getDateTo())) {
					didMatch = false;
				}

				else if (type != null && (s.getSurgeryType() == null || !s.getSurgeryType().equals(type))) {
					didMatch = false;
				}
				
				else if (status != null && !s.getStatus().equals(status)) {
					didMatch = false;
				}
				
				if (didMatch) {
					matchingSlots.add(s);
				}
			}
		}

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
		OPSlot example = new OPSlot();

		example.setHospital(hospital);
		return this.findByExample(example, OPSlotStatus.FREE);
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
	
	@Override
	public void deleteAllOPSlots() {
		for (OPSlot opSlot : this.findAllOPSlots()) {
			this.deleteOPSlot(opSlot);
		}
	}
}
