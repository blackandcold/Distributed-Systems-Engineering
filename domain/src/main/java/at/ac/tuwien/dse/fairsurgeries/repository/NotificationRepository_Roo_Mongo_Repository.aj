// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.repository;

import at.ac.tuwien.dse.fairsurgeries.domain.Notification;
import at.ac.tuwien.dse.fairsurgeries.repository.NotificationRepository;
import java.math.BigInteger;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

privileged aspect NotificationRepository_Roo_Mongo_Repository {
    
    declare parents: NotificationRepository extends PagingAndSortingRepository<Notification, BigInteger>;
    
    declare @type: NotificationRepository: @Repository;
    
}
