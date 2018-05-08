package com.arash.flm.gl.mail;

import com.arash.flm.gl.mail.connector.GmailConnector;
import com.arash.flm.gl.model.mail.db.GfOffer;
import com.arash.flm.gl.parcer.GfForEvaluationParser;
import com.arash.flm.gl.repository.GfOfferRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 08.05.18.
 *
 */
@Component
public class GfGmailOffersReader {

    private static final Logger LOGGER = LogManager.getLogger(GfGmailOffersReader.class);
    private static final String FOR_EVALUATION = "for evaluation";

    @Autowired private GmailConnector gmailConnector;
    @Autowired private GfOfferRepository offerRepository;
    @Autowired private GfForEvaluationParser parser;

    public void readOffers() {

        List<Message> messages = new ArrayList<>(gmailConnector.getLastMessages(25, false));
        messages.removeIf(message -> !isGfOffer(message) || offerIsPresent(message));
        if (!messages.isEmpty()) {
            messages.parallelStream().forEach(message -> {
                GfOffer offer = fetchOfferFromMessage(message);
                if (offer != null) {
                    offerRepository.save(offer);
                }
            });
        }
    }

    private GfOffer fetchOfferFromMessage(Message message) {

        GfOffer offer = null;
        try {
            offer = parser.parse(message);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while parsing a message", e);
        }

        return offer;
    }

    private boolean isGfOffer(Message message) {

        boolean messageIsGfOffer = false;
        try {
            messageIsGfOffer = message.getSubject().endsWith(FOR_EVALUATION);
        } catch (MessagingException e) {
            LOGGER.error("Unable to fetch a message subject", e);
        }

        return messageIsGfOffer;
    }

    private boolean offerIsPresent(Message message) {

//        boolean offerIsPresent = false;
//        try {
//            String subject = message.getSubject();
//            String taskNumber = subject.substring(6, 12);
//            offerIsPresent = offerRepository.findById(taskNumber).isPresent();
//        } catch (MessagingException e) {
//            LOGGER.error("Unable to fetch a message subject", e);
//        }
//        return offerIsPresent;

        return false;
    }
}
