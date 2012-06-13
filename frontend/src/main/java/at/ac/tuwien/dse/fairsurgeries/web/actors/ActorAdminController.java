package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;


@Controller
@RequestMapping("/actors/admin")
public class ActorAdminController {
	
	@Autowired
	private OPSlotService opSlotService;
	
	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private LogEntryService logEntryService;

	@RequestMapping(value="/slots", method = RequestMethod.GET, produces = "text/html")
	public String listSlots(Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorAdminController . listSlots()");
		
		uiModel.addAttribute("opSlots", opSlotService.findAllOPSlots());
		uiModel.addAttribute("surgeryTypes", Arrays.asList(SurgeryType.values()));
		uiModel.addAttribute("hospitals", hospitalService.findAllHospitals());
		uiModel.addAttribute("doctors", doctorService.findAllDoctors());
		uiModel.addAttribute("patients", patientService.findAllPatients());
		
		uiModel.addAttribute("opSlotExample", new OPSlot());
		return "actors/admin/slots";
	}
	
	@RequestMapping(value="/slots", method = RequestMethod.POST, produces = "text/html")
	public String listFilteredSlots(@ModelAttribute OPSlot opSlot, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorAdminController . listFilteredSlots() example=" + opSlot);
		uiModel.addAttribute("opSlots", opSlotService.findByExample(opSlot));
		uiModel.addAttribute("surgeryTypes", Arrays.asList(SurgeryType.values()));
		uiModel.addAttribute("hospitals", hospitalService.findAllHospitals());
		uiModel.addAttribute("doctors", doctorService.findAllDoctors());
		uiModel.addAttribute("patients", patientService.findAllPatients());
		
		uiModel.addAttribute("opSlotExample", opSlot);
		return "actors/admin/slots";
	}
	
}
