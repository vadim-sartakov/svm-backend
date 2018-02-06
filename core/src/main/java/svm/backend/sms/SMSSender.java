package svm.backend.sms;

public interface SMSSender {
    Object sendMessage(String phoneNumber, String message);
    Object getMessageStatus(Object id);
}
