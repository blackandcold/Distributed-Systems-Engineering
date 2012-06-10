// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.web;

import at.ac.tuwien.dse.fairsurgeries.domain.Admin;
import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.LogEntry;
import at.ac.tuwien.dse.fairsurgeries.domain.Notification;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.domain.PublicPerson;
import at.ac.tuwien.dse.fairsurgeries.service.AdminService;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.NotificationService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;
import at.ac.tuwien.dse.fairsurgeries.service.PublicPersonService;
import at.ac.tuwien.dse.fairsurgeries.web.ApplicationConversionServiceFactoryBean;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    @Autowired
    AdminService ApplicationConversionServiceFactoryBean.adminService;
    
    @Autowired
    DoctorService ApplicationConversionServiceFactoryBean.doctorService;
    
    @Autowired
    HospitalService ApplicationConversionServiceFactoryBean.hospitalService;
    
    @Autowired
    LogEntryService ApplicationConversionServiceFactoryBean.logEntryService;
    
    @Autowired
    NotificationService ApplicationConversionServiceFactoryBean.notificationService;
    
    @Autowired
    OPSlotService ApplicationConversionServiceFactoryBean.oPSlotService;
    
    @Autowired
    PatientService ApplicationConversionServiceFactoryBean.patientService;
    
    @Autowired
    PublicPersonService ApplicationConversionServiceFactoryBean.publicPersonService;
    
    public Converter<Admin, String> ApplicationConversionServiceFactoryBean.getAdminToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<at.ac.tuwien.dse.fairsurgeries.domain.Admin, java.lang.String>() {
            public String convert(Admin admin) {
                return new StringBuilder().append(admin.getFirstName()).append(" ").append(admin.getLastName()).append(" ").append(admin.getDateOfBirth()).toString();
            }
        };
    }
    
    public Converter<BigInteger, Admin> ApplicationConversionServiceFactoryBean.getIdToAdminConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, at.ac.tuwien.dse.fairsurgeries.domain.Admin>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.Admin convert(java.math.BigInteger id) {
                return adminService.findAdmin(id);
            }
        };
    }
    
    public Converter<String, Admin> ApplicationConversionServiceFactoryBean.getStringToAdminConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, at.ac.tuwien.dse.fairsurgeries.domain.Admin>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.Admin convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Admin.class);
            }
        };
    }
    
    public Converter<Doctor, String> ApplicationConversionServiceFactoryBean.getDoctorToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<at.ac.tuwien.dse.fairsurgeries.domain.Doctor, java.lang.String>() {
            public String convert(Doctor doctor) {
                return new StringBuilder().append(doctor.getFirstName()).append(" ").append(doctor.getLastName()).append(" ").append(doctor.getDateOfBirth()).toString();
            }
        };
    }
    
    public Converter<BigInteger, Doctor> ApplicationConversionServiceFactoryBean.getIdToDoctorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, at.ac.tuwien.dse.fairsurgeries.domain.Doctor>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.Doctor convert(java.math.BigInteger id) {
                return doctorService.findDoctor(id);
            }
        };
    }
    
    public Converter<String, Doctor> ApplicationConversionServiceFactoryBean.getStringToDoctorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, at.ac.tuwien.dse.fairsurgeries.domain.Doctor>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.Doctor convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Doctor.class);
            }
        };
    }
    
    public Converter<Hospital, String> ApplicationConversionServiceFactoryBean.getHospitalToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<at.ac.tuwien.dse.fairsurgeries.domain.Hospital, java.lang.String>() {
            public String convert(Hospital hospital) {
                return new StringBuilder().append(hospital.getName()).append(" ").append(hospital.getLatitude()).append(" ").append(hospital.getLongitude()).toString();
            }
        };
    }
    
    public Converter<BigInteger, Hospital> ApplicationConversionServiceFactoryBean.getIdToHospitalConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, at.ac.tuwien.dse.fairsurgeries.domain.Hospital>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.Hospital convert(java.math.BigInteger id) {
                return hospitalService.findHospital(id);
            }
        };
    }
    
    public Converter<String, Hospital> ApplicationConversionServiceFactoryBean.getStringToHospitalConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, at.ac.tuwien.dse.fairsurgeries.domain.Hospital>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.Hospital convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Hospital.class);
            }
        };
    }
    
    public Converter<LogEntry, String> ApplicationConversionServiceFactoryBean.getLogEntryToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<at.ac.tuwien.dse.fairsurgeries.domain.LogEntry, java.lang.String>() {
            public String convert(LogEntry logEntry) {
                return new StringBuilder().append(logEntry.getComponent()).append(" ").append(logEntry.getMessage()).append(" ").append(logEntry.getExecutionTimestamp()).toString();
            }
        };
    }
    
    public Converter<BigInteger, LogEntry> ApplicationConversionServiceFactoryBean.getIdToLogEntryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, at.ac.tuwien.dse.fairsurgeries.domain.LogEntry>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.LogEntry convert(java.math.BigInteger id) {
                return logEntryService.findLogEntry(id);
            }
        };
    }
    
    public Converter<String, LogEntry> ApplicationConversionServiceFactoryBean.getStringToLogEntryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, at.ac.tuwien.dse.fairsurgeries.domain.LogEntry>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.LogEntry convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), LogEntry.class);
            }
        };
    }
    
    public Converter<Notification, String> ApplicationConversionServiceFactoryBean.getNotificationToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<at.ac.tuwien.dse.fairsurgeries.domain.Notification, java.lang.String>() {
            public String convert(Notification notification) {
                return new StringBuilder().append(notification.getDescription()).toString();
            }
        };
    }
    
    public Converter<BigInteger, Notification> ApplicationConversionServiceFactoryBean.getIdToNotificationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, at.ac.tuwien.dse.fairsurgeries.domain.Notification>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.Notification convert(java.math.BigInteger id) {
                return notificationService.findNotification(id);
            }
        };
    }
    
    public Converter<String, Notification> ApplicationConversionServiceFactoryBean.getStringToNotificationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, at.ac.tuwien.dse.fairsurgeries.domain.Notification>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.Notification convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Notification.class);
            }
        };
    }
    
    public Converter<OPSlot, String> ApplicationConversionServiceFactoryBean.getOPSlotToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<at.ac.tuwien.dse.fairsurgeries.domain.OPSlot, java.lang.String>() {
            public String convert(OPSlot oPSlot) {
                return new StringBuilder().append(oPSlot.getDateFrom()).append(" ").append(oPSlot.getDateTo()).toString();
            }
        };
    }
    
    public Converter<BigInteger, OPSlot> ApplicationConversionServiceFactoryBean.getIdToOPSlotConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, at.ac.tuwien.dse.fairsurgeries.domain.OPSlot>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.OPSlot convert(java.math.BigInteger id) {
                return oPSlotService.findOPSlot(id);
            }
        };
    }
    
    public Converter<String, OPSlot> ApplicationConversionServiceFactoryBean.getStringToOPSlotConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, at.ac.tuwien.dse.fairsurgeries.domain.OPSlot>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.OPSlot convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), OPSlot.class);
            }
        };
    }
    
    public Converter<Patient, String> ApplicationConversionServiceFactoryBean.getPatientToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<at.ac.tuwien.dse.fairsurgeries.domain.Patient, java.lang.String>() {
            public String convert(Patient patient) {
                return new StringBuilder().append(patient.getFirstName()).append(" ").append(patient.getLastName()).append(" ").append(patient.getDateOfBirth()).append(" ").append(patient.getLatitude()).toString();
            }
        };
    }
    
    public Converter<BigInteger, Patient> ApplicationConversionServiceFactoryBean.getIdToPatientConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, at.ac.tuwien.dse.fairsurgeries.domain.Patient>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.Patient convert(java.math.BigInteger id) {
                return patientService.findPatient(id);
            }
        };
    }
    
    public Converter<String, Patient> ApplicationConversionServiceFactoryBean.getStringToPatientConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, at.ac.tuwien.dse.fairsurgeries.domain.Patient>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.Patient convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Patient.class);
            }
        };
    }
    
    public Converter<PublicPerson, String> ApplicationConversionServiceFactoryBean.getPublicPersonToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<at.ac.tuwien.dse.fairsurgeries.domain.PublicPerson, java.lang.String>() {
            public String convert(PublicPerson publicPerson) {
                return new StringBuilder().toString();
            }
        };
    }
    
    public Converter<BigInteger, PublicPerson> ApplicationConversionServiceFactoryBean.getIdToPublicPersonConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, at.ac.tuwien.dse.fairsurgeries.domain.PublicPerson>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.PublicPerson convert(java.math.BigInteger id) {
                return publicPersonService.findPublicPerson(id);
            }
        };
    }
    
    public Converter<String, PublicPerson> ApplicationConversionServiceFactoryBean.getStringToPublicPersonConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, at.ac.tuwien.dse.fairsurgeries.domain.PublicPerson>() {
            public at.ac.tuwien.dse.fairsurgeries.domain.PublicPerson convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), PublicPerson.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getAdminToStringConverter());
        registry.addConverter(getIdToAdminConverter());
        registry.addConverter(getStringToAdminConverter());
        registry.addConverter(getDoctorToStringConverter());
        registry.addConverter(getIdToDoctorConverter());
        registry.addConverter(getStringToDoctorConverter());
        registry.addConverter(getHospitalToStringConverter());
        registry.addConverter(getIdToHospitalConverter());
        registry.addConverter(getStringToHospitalConverter());
        registry.addConverter(getLogEntryToStringConverter());
        registry.addConverter(getIdToLogEntryConverter());
        registry.addConverter(getStringToLogEntryConverter());
        registry.addConverter(getNotificationToStringConverter());
        registry.addConverter(getIdToNotificationConverter());
        registry.addConverter(getStringToNotificationConverter());
        registry.addConverter(getOPSlotToStringConverter());
        registry.addConverter(getIdToOPSlotConverter());
        registry.addConverter(getStringToOPSlotConverter());
        registry.addConverter(getPatientToStringConverter());
        registry.addConverter(getIdToPatientConverter());
        registry.addConverter(getStringToPatientConverter());
        registry.addConverter(getPublicPersonToStringConverter());
        registry.addConverter(getIdToPublicPersonConverter());
        registry.addConverter(getStringToPublicPersonConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
