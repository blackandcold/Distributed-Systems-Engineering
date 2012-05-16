// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.service;

import at.ac.tuwien.dse.fairsurgeries.domain.Notification;
import at.ac.tuwien.dse.fairsurgeries.service.NotificationService;
import java.math.BigInteger;
import java.util.List;

privileged aspect NotificationService_Roo_Service {
    
    public abstract long NotificationService.countAllNotifications();    
    public abstract void NotificationService.deleteNotification(Notification notification);    
    public abstract Notification NotificationService.findNotification(BigInteger id);    
    public abstract List<Notification> NotificationService.findAllNotifications();    
    public abstract List<Notification> NotificationService.findNotificationEntries(int firstResult, int maxResults);    
    public abstract void NotificationService.saveNotification(Notification notification);    
    public abstract Notification NotificationService.updateNotification(Notification notification);    
}
