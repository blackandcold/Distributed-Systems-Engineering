package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.ServletRequest;

import org.apache.commons.fileupload.RequestContext;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlotStatus;

@Controller
@RequestMapping("/actors/public")
public class ActorPublicPersonController {

	@Autowired
	private OPSlotService opSlotService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private LogEntryService logEntryService;

	@RequestMapping(value = "/slots", method = RequestMethod.GET, produces = "text/html")
	public String listSlots(Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPublicPersonController . listSlots()");

		this.setupModel(uiModel, new OPSlot(), null);

		return "actors/public/slots";
	}

	@RequestMapping(value = "/slots", method = RequestMethod.POST, produces = "text/html")
	public String listFilteredSlots(@ModelAttribute OPSlot opSlot, Model uiModel, ServletRequest request) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPublicPersonController . listFilteredSlots() example=" + opSlot);
		String status = request.getParameter("status");
		if(status == null || status.isEmpty())
			this.setupModel(uiModel, opSlot, null);
		else
			this.setupModel(uiModel, opSlot, OPSlotStatus.valueOf(status));
		return "actors/public/slots";
	}
	
	private void setupModel(Model uiModel, OPSlot slotFilter, OPSlotStatus status) {
		uiModel.addAttribute("opSlots", opSlotService.findByExample(slotFilter, status));
		uiModel.addAttribute("surgeryTypes", Arrays.asList(SurgeryType.values()));
		uiModel.addAttribute("statusList", Arrays.asList(OPSlotStatus.values()));
		uiModel.addAttribute("hospitals", hospitalService.findAllHospitals());
		uiModel.addAttribute("doctors", doctorService.findAllDoctors());
		uiModel.addAttribute("dateFormat", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("slotFilter", slotFilter);
		uiModel.addAttribute("status", status);
	}
}
