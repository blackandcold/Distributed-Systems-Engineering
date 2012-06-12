// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.domain;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.DoctorDataOnDemand;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
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

privileged aspect DoctorDataOnDemand_Roo_DataOnDemand {
    
    declare @type: DoctorDataOnDemand: @Component;
    
    private Random DoctorDataOnDemand.rnd = new SecureRandom();
    
    private List<Doctor> DoctorDataOnDemand.data;
    
    @Autowired
    DoctorService DoctorDataOnDemand.doctorService;
    
    public Doctor DoctorDataOnDemand.getNewTransientDoctor(int index) {
        Doctor obj = new Doctor();
        setDateOfBirth(obj, index);
        setFirstName(obj, index);
        setLastName(obj, index);
        return obj;
    }
    
    public void DoctorDataOnDemand.setDateOfBirth(Doctor obj, int index) {
        Date dateOfBirth = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setDateOfBirth(dateOfBirth);
    }
    
    public void DoctorDataOnDemand.setFirstName(Doctor obj, int index) {
        String firstName = "firstName_" + index;
        obj.setFirstName(firstName);
    }
    
    public void DoctorDataOnDemand.setLastName(Doctor obj, int index) {
        String lastName = "lastName_" + index;
        obj.setLastName(lastName);
    }
    
    public Doctor DoctorDataOnDemand.getSpecificDoctor(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Doctor obj = data.get(index);
        BigInteger id = obj.getId();
        return doctorService.findDoctor(id);
    }
    
    public Doctor DoctorDataOnDemand.getRandomDoctor() {
        init();
        Doctor obj = data.get(rnd.nextInt(data.size()));
        BigInteger id = obj.getId();
        return doctorService.findDoctor(id);
    }
    
    public boolean DoctorDataOnDemand.modifyDoctor(Doctor obj) {
        return false;
    }
    
    public void DoctorDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = doctorService.findDoctorEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Doctor' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Doctor>();
        for (int i = 0; i < 10; i++) {
            Doctor obj = getNewTransientDoctor(i);
            try {
                doctorService.saveDoctor(obj);
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
