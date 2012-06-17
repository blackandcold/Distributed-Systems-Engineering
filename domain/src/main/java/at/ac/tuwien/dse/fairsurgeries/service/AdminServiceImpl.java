package at.ac.tuwien.dse.fairsurgeries.service;

import at.ac.tuwien.dse.fairsurgeries.domain.Admin;


public class AdminServiceImpl implements AdminService {

	@Override
	public void deleteAllAdmins() {
		for (Admin admin : this.findAllAdmins()) {
			this.deleteAdmin(admin);
		}
	}
}
