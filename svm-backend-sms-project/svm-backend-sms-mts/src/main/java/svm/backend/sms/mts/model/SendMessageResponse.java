package svm.backend.sms.mts.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import lombok.Data;

@Data
@XmlRootElement(name = "long")
@XmlAccessorType(XmlAccessType.FIELD)
public class SendMessageResponse {
    @XmlValue
    private long id;
}
