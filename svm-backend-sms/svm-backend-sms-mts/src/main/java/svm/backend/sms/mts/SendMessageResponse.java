package svm.backend.sms.mts;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "long")
public class SendMessageResponse {
    @XmlValue
    public long id;
}
