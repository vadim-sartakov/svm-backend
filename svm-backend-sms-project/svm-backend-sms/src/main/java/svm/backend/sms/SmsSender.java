package svm.backend.sms;

public interface SmsSender {
    SmsMessage send(SmsMessage message);
    SmsMessage updateMessageStatus(SmsMessage message);
}
