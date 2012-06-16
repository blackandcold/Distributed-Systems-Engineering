package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.util.Arrays;

import javax.servlet.ServletRequest;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlotStatus;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;

/**
 * The controller managing all requests for the actor role "Admin"
 */
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

	/**
	 * This method updates the ui model to show a list of all slots.
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/slots", method = RequestMethod.GET, produces = "text/html")
	public String listSlots(Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorAdminController . listSlots()");

		this.setupModel(uiModel, new OPSlot(), null);

		return "actors/admin/slots";
	}

	/**
	 * This method updates the ui model to show a lis of all slots matching the given criteria
	 * @param opSlot the example slot (= filter criteria)
	 * @param uiModel the ui model
	 * @param request the request object
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/slots", method = RequestMethod.POST, produces = "text/html")
	public String listFilteredSlots(@ModelAttribute OPSlot opSlot, Model uiModel, ServletRequest request) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorAdminController . listFilteredSlots() example=" + opSlot);

		String status = request.getParameter("status");
		if (status == null || status.isEmpty()) {
			this.setupModel(uiModel, opSlot, null);
		} else {
			this.setupModel(uiModel, opSlot, OPSlotStatus.valueOf(status));
		}

		return "actors/admin/slots";
	}
	
	/**
	 * @param uiModel
	 * @return returns just a http OK status
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.GET, produces = "text/json")
	public ResponseEntity<String> resetDatabase(Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorAdminController . resetDatabase() resetting all data to default dummy content");

		/* TODO: Datenbank leeren */
		
		/* TODO: Testdaten anlegen */
		
		
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<String>("", headers, HttpStatus.OK);

	}	
	
	/**
	 * Private method to update the ui model with the given filter criteria
	 * @param uiModel the ui model
	 * @param slotFilter the example slot, used for findByExample
	 * @param status the status of the slot
	 */
	private void setupModel(Model uiModel, OPSlot slotFilter, OPSlotStatus status) {
		uiModel.addAttribute("opSlots", opSlotService.findByExample(slotFilter));
		uiModel.addAttribute("surgeryTypes", Arrays.asList(SurgeryType.values()));
		uiModel.addAttribute("statusList", Arrays.asList(OPSlotStatus.values()));
		uiModel.addAttribute("hospitals", hospitalService.findAllHospitals());
		uiModel.addAttribute("doctors", doctorService.findAllDoctors());
		uiModel.addAttribute("patients", patientService.findAllPatients());
		uiModel.addAttribute("dateFormat", DateTimeFormat.patternForStyle("MS", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("slotFilter", slotFilter);
		uiModel.addAttribute("status", status);
	}
}
