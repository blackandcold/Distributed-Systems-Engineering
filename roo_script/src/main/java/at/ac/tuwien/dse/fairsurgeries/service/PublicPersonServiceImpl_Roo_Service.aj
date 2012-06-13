// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.service;

import at.ac.tuwien.dse.fairsurgeries.domain.PublicPerson;
import at.ac.tuwien.dse.fairsurgeries.repository.PublicPersonRepository;
import at.ac.tuwien.dse.fairsurgeries.service.PublicPersonServiceImpl;
import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

privileged aspect PublicPersonServiceImpl_Roo_Service {
    
    declare @type: PublicPersonServiceImpl: @Service;
    
    declare @type: PublicPersonServiceImpl: @Transactional;
    
    @Autowired
    PublicPersonRepository PublicPersonServiceImpl.publicPersonRepository;
    
    public long PublicPersonServiceImpl.countAllPublicpeople() {
        return publicPersonRepository.count();
    }
    
    public void PublicPersonServiceImpl.deletePublicPerson(PublicPerson publicPerson) {
        publicPersonRepository.delete(publicPerson);
    }
    
    public PublicPerson PublicPersonServiceImpl.findPublicPerson(BigInteger id) {
        return publicPersonRepository.findOne(id);
    }
    
    public List<PublicPerson> PublicPersonServiceImpl.findAllPublicpeople() {
        return publicPersonRepository.findAll();
    }
    
    public List<PublicPerson> PublicPersonServiceImpl.findPublicPersonEntries(int firstResult, int maxResults) {
        return publicPersonRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }
    
    public void PublicPersonServiceImpl.savePublicPerson(PublicPerson publicPerson) {
        publicPersonRepository.save(publicPerson);
    }
    
    public PublicPerson PublicPersonServiceImpl.updatePublicPerson(PublicPerson publicPerson) {
        return publicPersonRepository.save(publicPerson);
    }
    
}
