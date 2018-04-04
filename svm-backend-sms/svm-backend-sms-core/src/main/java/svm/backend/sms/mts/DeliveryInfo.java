package svm.backend.sms.mts;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DeliveryInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeliveryInfo {
    
    @XmlElement(name = "Msid")
    public String msid;
    @XmlElement(name = "DeliveryStatus")
    public Status deliveryStatus;
    @XmlElement(name = "DeliveryDate")
    public Date deliveryDate;
    @XmlElement(name = "UserDeliveryDate")
    public Date userDeliveryDate;
    @XmlElement(name = "PartCount")
    public int partCount;

    @Override
    public String toString() {
        return "DeliveryInfo{" + "msid=" + msid + ", deliveryStatus=" + deliveryStatus + ", deliveryDate=" + deliveryDate + ", userDeliveryDate=" + userDeliveryDate + ", partCount=" + partCount + '}';
    }
    
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
