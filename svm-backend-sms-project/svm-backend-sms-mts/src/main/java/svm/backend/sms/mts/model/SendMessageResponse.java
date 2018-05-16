package svm.backend.sms.mts.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import lombok.Data;

@Data
@XmlRootElement(name = "long")
public class SendMessageResponse {
    @XmlValue
    private long id;
}
