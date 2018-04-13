package svm.backend.sms;

public interface SmsSender {
    Object send(SmsMessage message);
    Object getMessageStatus(Object id);
}
