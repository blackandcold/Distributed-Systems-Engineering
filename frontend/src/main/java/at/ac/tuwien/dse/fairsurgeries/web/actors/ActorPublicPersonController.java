package at.ac.tuwien.dse.fairsurgeries.web.actors;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlotStatus;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import flexjson.JSONSerializer;

/**
 * The controller managing all requests for the actor role "Public Person"
 */
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

	/**
	 * This method updates the ui model to show a list of all slots.
	 * @param uiModel the ui model
	 * @return identifier of the next page we want to visit
	 */
	@RequestMapping(value = "/slots", method = RequestMethod.GET, produces = "text/html")
	public String listSlots(Model uiModel) {
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPublicPersonController . listSlots()");

		this.setupModel(uiModel, new OPSlot(), null);

		return "actors/public/slots";
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
		logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPublicPersonController . listFilteredSlots() example=" + opSlot);
		String status = request.getParameter("status");
		
		if (status == null || status.isEmpty()) {
			this.setupModel(uiModel, opSlot, null);
		} else {
			this.setupModel(uiModel, opSlot, OPSlotStatus.valueOf(status));
		}
		
		return "actors/public/slots";
	}
	
	/**
	 * Private method to update the ui model with the given filter criteria
	 * @param uiModel the ui model
	 * @param slotFilter the example slot, used for findByExample
	 * @param status the status of the slot
	 */
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
	
	/* REST */
	
	/*
	 * curl -i -X GET -H "Content-Type: application/json" -H "Accept: application/json" http://dse_frontend.cloudfoundry.com/actors/public/listSlotsJSON/dateFrom/01-01-1980/dateTo/16-06-2012 
	 * 
	 */
	
	@SuppressWarnings("unused")
	/**
	 * @param dateFrom starting date in format dd-mm-yyyy
	 * @param dateTo end date in format dd-mm-yyyy
	 * @return Returns JSON Header and Content of the Result
	 */
	@RequestMapping(value = "/listSlotsJSON/dateFrom/{dateFrom}/dateTo/{dateTo}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listSlotsJSON(@PathVariable String dateFrom, @PathVariable String dateTo, Model model) {
    	logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPublicPersonController . listSlotsJSON()");

    	List<OPSlot> opSlots;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        
    	/*
    	 * Parsing Request
    	 */
    	
    	//JSONDeserializer<OPSlot> deserializer = new JSONDeserializer<OPSlot>();
    	

    	OPSlot exampleSlot = new OPSlot();
    	if(dateFrom != null && dateFrom != "")
    	{
    		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    		Date parsedDateFrom = null;
    		try
    		{
    			parsedDateFrom = (Date)formatter.parse(dateFrom);
    		}catch(ParseException e)
    		{
    			logEntryService.log(Constants.Component.Frontend.toString(), "Parse request dateFrom . listSlotsJSON() exception: "+e.getMessage()+" ");
    		}
    		//exampleSlot.setDateFrom(parsedDateFrom);
    		logEntryService.log(Constants.Component.Frontend.toString(), "Parse request dateFrom . listSlotsJSON(): "+dateFrom+" ");
    	}
    	if(dateTo != null && dateTo != "")
    	{
    		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    		Date parsedDateTo = null;
    		try
    		{
    			parsedDateTo = (Date)formatter.parse(dateFrom);
			}catch(ParseException e)
			{
				logEntryService.log(Constants.Component.Frontend.toString(), "Parse request dateTo . listSlotsJSON() exception: "+e.getMessage()+" ");
			}
    		//exampleSlot.setDateTo(parsedDateTo); //Does not work!
    		logEntryService.log(Constants.Component.Frontend.toString(), "Parse request dateTo . listSlotsJSON(): "+dateTo+" ");
    	}
    	
    	// set properties per arguments (url parts or get parameters)
    	
		logEntryService.log(Constants.Component.Frontend.toString(), "Example generated . listSlotsJSON(): " + exampleSlot);
		opSlots = opSlotService.findByExample(exampleSlot);

    	
    	/*
    	 * Answere
    	 */
    	
    	JSONSerializer serializer = new JSONSerializer();
    	//JSONSerializer arraySerializer = new JSONSerializer().transform(new ArrayTransformer(),ArrayList.class);
 /*   	 
    	ArrayList<String> jsonStringList = new ArrayList<String>();
    	
    	for(OPSlot slot : opSlots)
    	{
    		//jsonStringList.add(new JSONSerializer().deepSerialize(slot));
    		jsonStringList.add(new JSONSerializer().serialize(slot));
    	}
    	
    	*/
        String outputJSON = serializer.serialize(opSlots);
    	
        return new ResponseEntity<String>(outputJSON, headers, HttpStatus.OK);
      
        // May be for error handling or something 
        //return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
    }
	
}
