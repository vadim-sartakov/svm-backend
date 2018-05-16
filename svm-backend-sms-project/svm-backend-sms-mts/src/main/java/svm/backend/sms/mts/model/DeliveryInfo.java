package svm.backend.sms.mts.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "DeliveryInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeliveryInfo {
    
    @XmlElement(name = "Msid")
    private String msid;
    @XmlElement(name = "DeliveryStatus")
    private Status deliveryStatus;
    @XmlElement(name = "DeliveryDate")
    private Date deliveryDate;
    @XmlElement(name = "UserDeliveryDate")
    private Date userDeliveryDate;
    @XmlElement(name = "PartCount")
    private int partCount;
    
    @XmlEnum
    public enum Status {
        @XmlEnumValue("Pending") PENDING,
        @XmlEnumValue("Sending") SENDING,
        @XmlEnumValue("Sent") SENT,
        @XmlEnumValue("NotSent") NOT_SENT,
        @XmlEnumValue("Delivered") DELIVERED,
        @XmlEnumValue("NotDelivered") NOT_DELIVERED,
        @XmlEnumValue("TimedOut") TIMED_OUT,
        @XmlEnumValue("Error") ERROR,
        @XmlEnumValue("Cancelled") CANCELLED                                  
    }
    
}
