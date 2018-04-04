package svm.backend.sms.mts;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ArrayOfDeliveryInfo")
public class ArrayOfDeliveryInfo {     
    @XmlElement(name = "DeliveryInfo")
    public List<DeliveryInfo> deliveryInfoList;        
}
