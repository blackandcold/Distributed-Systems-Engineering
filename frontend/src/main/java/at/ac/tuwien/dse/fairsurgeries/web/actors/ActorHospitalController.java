package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;

@Controller
@RequestMapping("/actors/hospital")
public class ActorHospitalController {
	
	@Autowired
	private LogEntryService logEntryService;
	
	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private OPSlotService opSlotService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private PatientService patientService;

	
	@RequestMapping(value = "{hospitalId}", method = RequestMethod.GET)
	public String showMenu(@PathVariable BigInteger hospitalId, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . () for ID: " + hospitalId);

		Hospital hospital = hospitalService.findHospital(hospitalId);

		if (hospital != null) {
			
			uiModel.addAttribute("hospital", hospital);

			return "actors/hospital/showmenu";
		} else {
			return "redirect:/resourceNotFound";
		}
	}
	
	@RequestMapping(value = "/viewslots", method = RequestMethod.POST)
	public String viewSlots(@ModelAttribute Hospital hospital, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . viewSlots() for hospital: " + hospital);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		return "actors/hospital/viewslots";
	}
	
	@RequestMapping(value = "/viewnotifications", method = RequestMethod.POST)
	public String viewNotifications(@ModelAttribute Hospital hospital, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . viewNotifications() for hospital: " + hospital);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		return "actors/hospital/viewnotifications";
	}
	
	@RequestMapping(value = "/manageslots", method = RequestMethod.POST)
	public String manageSlots(@ModelAttribute Hospital hospital, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . manageSlots() for hospital: " + hospital);
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		OPSlot opSlot = new OPSlot();
		opSlot.setHospital(hospital);
		uiModel.addAttribute("opSlot", opSlot);
		//uiModel.addAttribute("opSlots", opSlotService.findByHospital(hospital));
		
		uiModel.addAttribute("OPSlot_datefrom_date_format", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("OPSlot_dateto_date_format", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));

        //uiModel.addAttribute("OPSlot_datefrom_date_format", "dd.MM.yyyy");
        //uiModel.addAttribute("OPSlot_dateto_date_format", "dd.MM.yyyy");
        
        /*uiModel.addAttribute("doctors", doctorService.findAllDoctors());
        uiModel.addAttribute("hospitals", hospitalService.findAllHospitals());
        uiModel.addAttribute("patients", patientService.findAllPatients());
        uiModel.addAttribute("surgerytypes", Arrays.asList(SurgeryType.values()));*/
		
		return "actors/hospital/manageslots";
	}
	
	@RequestMapping(value = "/createslot", method = RequestMethod.POST)
	public void createSlot(@ModelAttribute Hospital hospital, @ModelAttribute OPSlot opSlot, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . createSlot() for hospital: " + hospital);
		logEntryService.log(Constants.Component.Frontend.toString(), "opSlot: " + opSlot);
		if(opSlot != null)
			logEntryService.log(Constants.Component.Frontend.toString(), "opSlot.hospital: " + opSlot.getHospital());
		else
			logEntryService.log(Constants.Component.Frontend.toString(), "opSlot is freakin null");
		logEntryService.log(Constants.Component.Frontend.toString(), "uiModel: " + uiModel);
		//opSlotService.saveOPSlot(OPSlot_)
		
		//opSlot.setHospital(hospital);
		opSlotService.saveOPSlot(opSlot);
		
		//return "redirect:/actors/hospital/viewslots";
		viewSlots(hospital, uiModel);
	}
}
