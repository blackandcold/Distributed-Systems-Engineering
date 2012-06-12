// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.domain;

import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.domain.PatientDataOnDemand;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect PatientDataOnDemand_Roo_DataOnDemand {
    
    declare @type: PatientDataOnDemand: @Component;
    
    private Random PatientDataOnDemand.rnd = new SecureRandom();
    
    private List<Patient> PatientDataOnDemand.data;
    
    @Autowired
    PatientService PatientDataOnDemand.patientService;
    
    public Patient PatientDataOnDemand.getNewTransientPatient(int index) {
        Patient obj = new Patient();
        setDateOfBirth(obj, index);
        setFirstName(obj, index);
        setLastName(obj, index);
        setPosition(obj, index);
        return obj;
    }
    
    public void PatientDataOnDemand.setDateOfBirth(Patient obj, int index) {
        Date dateOfBirth = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setDateOfBirth(dateOfBirth);
    }
    
    public void PatientDataOnDemand.setFirstName(Patient obj, int index) {
        String firstName = "firstName_" + index;
        obj.setFirstName(firstName);
    }
    
    public void PatientDataOnDemand.setLastName(Patient obj, int index) {
        String lastName = "lastName_" + index;
        obj.setLastName(lastName);
    }
    
    public void PatientDataOnDemand.setPosition(Patient obj, int index) {
        double[] position = { new Integer(index).doubleValue(), new Integer(index).doubleValue() };
        obj.setPosition(position);
    }
    
    public Patient PatientDataOnDemand.getSpecificPatient(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Patient obj = data.get(index);
        BigInteger id = obj.getId();
        return patientService.findPatient(id);
    }
    
    public Patient PatientDataOnDemand.getRandomPatient() {
        init();
        Patient obj = data.get(rnd.nextInt(data.size()));
        BigInteger id = obj.getId();
        return patientService.findPatient(id);
    }
    
    public boolean PatientDataOnDemand.modifyPatient(Patient obj) {
        return false;
    }
    
    public void PatientDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = patientService.findPatientEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Patient' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Patient>();
        for (int i = 0; i < 10; i++) {
            Patient obj = getNewTransientPatient(i);
            try {
                patientService.savePatient(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            data.add(obj);
        }
    }
    
}