// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.domain;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect Doctor_Roo_Json {
    
    public String Doctor.toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }
    
    public static Doctor Doctor.fromJsonToDoctor(String json) {
        return new JSONDeserializer<Doctor>().use(null, Doctor.class).deserialize(json);
    }
    
    public static String Doctor.toJsonArray(Collection<Doctor> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }
    
    public static Collection<Doctor> Doctor.fromJsonArrayToDoctors(String json) {
        return new JSONDeserializer<List<Doctor>>().use(null, ArrayList.class).use("values", Doctor.class).deserialize(json);
    }
    
}
