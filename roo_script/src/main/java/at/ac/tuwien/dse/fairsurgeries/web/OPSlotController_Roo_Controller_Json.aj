// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.web;

import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.web.OPSlotController;
import java.math.BigInteger;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

privileged aspect OPSlotController_Roo_Controller_Json {
    
    @RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> OPSlotController.showJson(@PathVariable("id") BigInteger id) {
        OPSlot OPSlot_ = oPSlotService.findOPSlot(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (OPSlot_ == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(OPSlot_.toJson(), headers, HttpStatus.OK);
    }
    
    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> OPSlotController.listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<OPSlot> result = oPSlotService.findAllOPSlots();
        return new ResponseEntity<String>(OPSlot.toJsonArray(result), headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> OPSlotController.createFromJson(@RequestBody String json) {
        OPSlot OPSlot_ = OPSlot.fromJsonToOPSlot(json);
        oPSlotService.saveOPSlot(OPSlot_);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> OPSlotController.createFromJsonArray(@RequestBody String json) {
        for (OPSlot OPSlot_: OPSlot.fromJsonArrayToOPSlots(json)) {
            oPSlotService.saveOPSlot(OPSlot_);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> OPSlotController.updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        OPSlot OPSlot_ = OPSlot.fromJsonToOPSlot(json);
        if (oPSlotService.updateOPSlot(OPSlot_) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> OPSlotController.updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (OPSlot OPSlot_: OPSlot.fromJsonArrayToOPSlots(json)) {
            if (oPSlotService.updateOPSlot(OPSlot_) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> OPSlotController.deleteFromJson(@PathVariable("id") BigInteger id) {
        OPSlot OPSlot_ = oPSlotService.findOPSlot(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (OPSlot_ == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        oPSlotService.deleteOPSlot(OPSlot_);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
}