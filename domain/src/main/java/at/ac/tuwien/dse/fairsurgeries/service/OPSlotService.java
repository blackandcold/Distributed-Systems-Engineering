package at.ac.tuwien.dse.fairsurgeries.service;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;

@RooService(domainTypes = { at.ac.tuwien.dse.fairsurgeries.domain.OPSlot.class })
public interface OPSlotService {
	
	public List<OPSlot> findByExample(OPSlot slot);
	
}
