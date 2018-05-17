package svm.backend.sms.mts.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "ArrayOfDeliveryInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArrayOfDeliveryInfo {     
    @XmlElement(name = "DeliveryInfo")
    private List<DeliveryInfo> deliveryInfoList;        
}
