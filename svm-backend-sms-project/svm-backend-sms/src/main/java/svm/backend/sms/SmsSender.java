package svm.backend.sms;

/**
 * 
 * @author Sartakov
 * @param <ID> - message id type
 * @param <M> - message type
 */
public interface SmsSender<ID, M> {
    ID send(SmsMessage message);
    M getMessageStatus(ID id);
}
