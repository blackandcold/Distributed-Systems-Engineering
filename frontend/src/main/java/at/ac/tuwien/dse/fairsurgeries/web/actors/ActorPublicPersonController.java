package at.ac.tuwien.dse.fairsurgeries.web.actors;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlotStatus;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import flexjson.JSONSerializer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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
	
	/*
	 * curl -i -X GET -H "Content-Type: application/json" -H "Accept: application/json" http://dse_frontend.cloudfoundry.com/actors/public/listSlotsJSON/dateFrom/01-01-1980/dateTo/16-06-2012 
	 * 
	 */
	
	/**
	 * @param dateFrom
	 * @param dateTo
	 * @return Returns JSON Header and Content of the Result
	 */
	@RequestMapping(value = "/listSlotsJSON/dateFrom/{dateFrom}/dateTo/{dateTo}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listSlotsJSON(@PathVariable String dateFrom, @PathVariable String dateTo, Model model) {
    	logEntryService.log(Constants.Component.Frontend.toString(), "Starting ActorPublicPersonController . listSlotsJSON()");

    	List<OPSlot> opSlots;
    	/*
    	 * Parsing Request
    	 */
    	
    	//JSONDeserializer<OPSlot> deserializer = new JSONDeserializer<OPSlot>();
    	

    	OPSlot exampleSlot = new OPSlot();
    	if(dateFrom != "")
    	{
    		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    		Date parsedDateFrom = null;
    		try
    		{
    			parsedDateFrom = (Date)formatter.parse(dateFrom);
    		}catch(ParseException e)
    		{
    			
    		}
    		//exampleSlot.setDateFrom(parsedDateFrom);
    		logEntryService.log(Constants.Component.Frontend.toString(), "Parse request dateFrom . listSlotsJSON(): "+dateFrom+" ");
    	}
    	if(dateTo != "")
    	{
    		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    		Date parsedDateTo = null;
    		try
    		{
    			parsedDateTo = (Date)formatter.parse(dateFrom);
			}catch(ParseException e)
			{
				
			}
    		//exampleSlot.setDateTo(parsedDateTo);
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
    	
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<String>(outputJSON, headers, HttpStatus.OK);
      
        // May be for error handling or something 
        //return new ResponseEntity<string>(headers, HttpStatus.NOT_FOUND);
    }
}
