// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.domain;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import java.util.Set;

privileged aspect Hospital_Roo_JavaBean {
    
    public String Hospital.getName() {
        return this.name;
    }
    
    public void Hospital.setName(String name) {
        this.name = name;
    }
    
    public Set<OPSlot> Hospital.getOpSlots() {
        return this.opSlots;
    }
    
    public void Hospital.setOpSlots(Set<OPSlot> opSlots) {
        this.opSlots = opSlots;
    }
    
}
