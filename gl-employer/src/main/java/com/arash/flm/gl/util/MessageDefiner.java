package com.arash.flm.gl.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.Date;

/**
 * Created by anton on 30.04.18.
 *
 */
public class MessageDefiner {

    private static final String SERVICE_GF_ADDRESS = "service@globalfreelance.ua";
    private static final String NOREPLY_GF_ADDRESS = "noreply@globalfreelance.ua";

    private static final String FOR_EVALUATION = "for evaluation";
    private static final String OUT_OF_LISTING = "out of listing";

    public static boolean isGfForEvaluationMessage(Message message) {

        return addressAndSubjectCriteria(message, SERVICE_GF_ADDRESS, FOR_EVALUATION);
    }

    public static boolean isGfOutOfListingMessage(Message message) {

        return addressAndSubjectCriteria(message, NOREPLY_GF_ADDRESS, OUT_OF_LISTING);
    }

    private static boolean addressAndSubjectCriteria(Message message, String queryAddress, String querySubject) {

        try {
            String address = message.getFrom()[0].toString();
            String subject = message.getSubject();
            return address.contains(queryAddress) && subject.contains(querySubject);
        } catch (MessagingException e) {
            return false;
        }
    }
}
