package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.math.BigInteger;
import java.util.ArrayList;
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
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;

@Controller
@RequestMapping("/actors/hospital")
public class ActorHospitalController {
	
	@Autowired
	private LogEntryService logEntryService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private OPSlotService opSlotService;

	
	@RequestMapping(value = "{hospitalId}", method = RequestMethod.GET)
	public String showMenu(@PathVariable BigInteger hospitalId, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . () for ID: " + hospitalId);
		return prepareMenu(hospitalId, uiModel);
	}
	
	private String prepareMenu(BigInteger hospitalId, Model uiModel) {
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
		
		List<Hospital> hospitalList = new ArrayList<Hospital>();
		hospitalList.add(hospital);
		uiModel.addAttribute("hospitals", hospitalList);
		
		uiModel.addAttribute("OPSlot_datefrom_date_format", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("OPSlot_dateto_date_format", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
        
        uiModel.addAttribute("slotsDeleteList", opSlotService.findAllFreeSlotsByHospital(hospital));
		
		return "actors/hospital/manageslots";
	}
	
	@RequestMapping(value = "/createslot", method = RequestMethod.POST)
	public String createSlot(@ModelAttribute OPSlot opSlot, @ModelAttribute Hospital hospital, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . createSlot() for opSlot: " + opSlot);

		opSlotService.saveOPSlot(opSlot);
		
		String redirectUrl = "/actors/hospital/" + opSlot.getHospital().getId();
		logEntryService.log(Constants.Component.Frontend.toString(), "Redirecting to: " + redirectUrl);
		
		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = "/deleteslot", method = RequestMethod.POST)
	public String deleteSlot(@ModelAttribute OPSlot opSlot, @ModelAttribute Hospital hospital, Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorHospital . deleteSlot() for opSlot: " + opSlot);
		
		/*if(id == null)
			logEntryService.log("DEL STR", "STR is null");
		else
			logEntryService.log("DEL STR", "STR ID=" + id);
		
		if(opSlot.getId() == null)
			logEntryService.log("DEL ID", "opSlot is null");
		else
			logEntryService.log("DEL ID", "OPSLOT ID=" + opSlot.getId());
		
		if(opSlot.getHospital() == null)
			logEntryService.log("DEL HO", "opSlot is null");
		else
			logEntryService.log("DEL HO", "Hospital = " + hospital);*/

		//opSlotService.deleteOpSlot(opSlot);
		opSlotService.deleteOPSlot(opSlot);
		
		String redirectUrl = "/actors/hospital/" + opSlot.getHospital().getId();
		logEntryService.log(Constants.Component.Frontend.toString(), "Redirecting to: " + redirectUrl);
		
		return "redirect:" + redirectUrl;
		//return "redirect:/";
	}
}
