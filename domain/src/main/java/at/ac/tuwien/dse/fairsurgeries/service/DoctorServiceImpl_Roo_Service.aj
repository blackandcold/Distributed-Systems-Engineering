// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.service;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.repository.DoctorRepository;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorServiceImpl;
import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

privileged aspect DoctorServiceImpl_Roo_Service {
    
    declare @type: DoctorServiceImpl: @Service;
    
    declare @type: DoctorServiceImpl: @Transactional;
    
    @Autowired
    DoctorRepository DoctorServiceImpl.doctorRepository;
    
    public long DoctorServiceImpl.countAllDoctors() {
        return doctorRepository.count();
    }
    
    public void DoctorServiceImpl.deleteDoctor(Doctor doctor) {
        doctorRepository.delete(doctor);
    }
    
    public Doctor DoctorServiceImpl.findDoctor(BigInteger id) {
        return doctorRepository.findOne(id);
    }
    
    public List<Doctor> DoctorServiceImpl.findAllDoctors() {
        return doctorRepository.findAll();
    }
    
    public List<Doctor> DoctorServiceImpl.findDoctorEntries(int firstResult, int maxResults) {
        return doctorRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }
    
    public void DoctorServiceImpl.saveDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }
    
    public Doctor DoctorServiceImpl.updateDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
    
}
