package svm.backend.sms;

/**
 * Template to communicate with SMS service API provider.
 * @author Вадим
 */
public interface SmsSender {
    
    /**
     * Sends specified message.
     * @param message
     * @return message id
     */
    String send(SmsMessage message);
    
    /**
     * Retrieves message status.
     * @param id of message
     * @return message status
     */
    SmsStatus getMessageStatus(String id);
}
