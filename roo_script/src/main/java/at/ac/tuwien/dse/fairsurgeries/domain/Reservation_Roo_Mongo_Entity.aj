// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.domain;

import at.ac.tuwien.dse.fairsurgeries.domain.Reservation;
import java.math.BigInteger;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;

privileged aspect Reservation_Roo_Mongo_Entity {
    
    declare @type: Reservation: @Persistent;
    
    @Id
    private BigInteger Reservation.id;
    
    public BigInteger Reservation.getId() {
        return this.id;
    }
    
    public void Reservation.setId(BigInteger id) {
        this.id = id;
    }
    
}
