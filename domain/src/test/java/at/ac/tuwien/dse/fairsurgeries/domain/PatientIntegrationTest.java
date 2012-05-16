package at.ac.tuwien.dse.fairsurgeries.domain;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = Patient.class, transactional = false)
public class PatientIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }
}
