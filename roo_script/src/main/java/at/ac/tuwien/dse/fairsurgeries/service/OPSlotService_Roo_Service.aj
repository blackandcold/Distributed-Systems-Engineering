// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.service;

import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import java.math.BigInteger;
import java.util.List;

privileged aspect OPSlotService_Roo_Service {
    
    public abstract long OPSlotService.countAllOPSlots();    
    public abstract void OPSlotService.deleteOPSlot(OPSlot OPSlot_);    
    public abstract OPSlot OPSlotService.findOPSlot(BigInteger id);    
    public abstract List<OPSlot> OPSlotService.findAllOPSlots();    
    public abstract List<OPSlot> OPSlotService.findOPSlotEntries(int firstResult, int maxResults);    
    public abstract void OPSlotService.saveOPSlot(OPSlot OPSlot_);    
    public abstract OPSlot OPSlotService.updateOPSlot(OPSlot OPSlot_);    
}
