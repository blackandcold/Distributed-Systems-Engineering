// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.domain;

import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import java.util.Date;
import java.util.Set;

privileged aspect Patient_Roo_JavaBean {
    
    public Set<OPSlot> Patient.getOpSlots() {
        return this.opSlots;
    }
    
    public void Patient.setOpSlots(Set<OPSlot> opSlots) {
        this.opSlots = opSlots;
    }
    
    public String Patient.getFirstName() {
        return this.firstName;
    }
    
    public void Patient.setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String Patient.getLastName() {
        return this.lastName;
    }
    
    public void Patient.setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Date Patient.getDateOfBirth() {
        return this.dateOfBirth;
    }
    
    public void Patient.setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public Double Patient.getLatitude() {
        return this.latitude;
    }
    
    public void Patient.setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Double Patient.getLongitude() {
        return this.longitude;
    }
    
    public void Patient.setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
}
