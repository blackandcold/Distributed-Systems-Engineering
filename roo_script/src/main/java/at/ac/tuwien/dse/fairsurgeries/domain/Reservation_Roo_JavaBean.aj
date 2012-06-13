// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.domain;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.domain.Reservation;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import java.util.Date;

privileged aspect Reservation_Roo_JavaBean {
    
    public SurgeryType Reservation.getSurgeryType() {
        return this.surgeryType;
    }
    
    public void Reservation.setSurgeryType(SurgeryType surgeryType) {
        this.surgeryType = surgeryType;
    }
    
    public Doctor Reservation.getDoctor() {
        return this.doctor;
    }
    
    public void Reservation.setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    public Patient Reservation.getPatient() {
        return this.patient;
    }
    
    public void Reservation.setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public Double Reservation.getRadius() {
        return this.radius;
    }
    
    public void Reservation.setRadius(Double radius) {
        this.radius = radius;
    }
    
    public Date Reservation.getDateFrom() {
        return this.dateFrom;
    }
    
    public void Reservation.setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }
    
    public Date Reservation.getDateTo() {
        return this.dateTo;
    }
    
    public void Reservation.setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
    
}
