// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.domain;

import at.ac.tuwien.dse.fairsurgeries.domain.Notification;
import at.ac.tuwien.dse.fairsurgeries.domain.NotificationDataOnDemand;
import at.ac.tuwien.dse.fairsurgeries.domain.NotificationReason;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlotDataOnDemand;
import at.ac.tuwien.dse.fairsurgeries.service.NotificationService;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect NotificationDataOnDemand_Roo_DataOnDemand {
    
    declare @type: NotificationDataOnDemand: @Component;
    
    private Random NotificationDataOnDemand.rnd = new SecureRandom();
    
    private List<Notification> NotificationDataOnDemand.data;
    
    @Autowired
    private OPSlotDataOnDemand NotificationDataOnDemand.oPSlotDataOnDemand;
    
    @Autowired
    NotificationService NotificationDataOnDemand.notificationService;
    
    public Notification NotificationDataOnDemand.getNewTransientNotification(int index) {
        Notification obj = new Notification();
        setDescription(obj, index);
        setOpSlot(obj, index);
        setReason(obj, index);
        return obj;
    }
    
    public void NotificationDataOnDemand.setDescription(Notification obj, int index) {
        String description = "description_" + index;
        obj.setDescription(description);
    }
    
    public void NotificationDataOnDemand.setOpSlot(Notification obj, int index) {
        OPSlot opSlot = oPSlotDataOnDemand.getRandomOPSlot();
        obj.setOpSlot(opSlot);
    }
    
    public void NotificationDataOnDemand.setReason(Notification obj, int index) {
        NotificationReason reason = NotificationReason.class.getEnumConstants()[0];
        obj.setReason(reason);
    }
    
    public Notification NotificationDataOnDemand.getSpecificNotification(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Notification obj = data.get(index);
        BigInteger id = obj.getId();
        return notificationService.findNotification(id);
    }
    
    public Notification NotificationDataOnDemand.getRandomNotification() {
        init();
        Notification obj = data.get(rnd.nextInt(data.size()));
        BigInteger id = obj.getId();
        return notificationService.findNotification(id);
    }
    
    public boolean NotificationDataOnDemand.modifyNotification(Notification obj) {
        return false;
    }
    
    public void NotificationDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = notificationService.findNotificationEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Notification' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Notification>();
        for (int i = 0; i < 10; i++) {
            Notification obj = getNewTransientNotification(i);
            try {
                notificationService.saveNotification(obj);
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
