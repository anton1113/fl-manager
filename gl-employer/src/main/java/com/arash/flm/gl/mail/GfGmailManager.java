package com.arash.flm.gl.mail;

import com.arash.flm.gl.util.MessageDefiner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by anton on 30.04.18.
 *
 */
@Component
public class GfGmailManager {

    private static final long WEEK = 7 * 24 * 60 * 60 * 1000;

    @Value("${flm.gmail.login}") private String gmailLogin;
    @Value("${flm.gmail.password}") private String gmailPassword;

    public void deleteObsoleteMessages() {

        try {
            Session session = createMailSession();
            Folder inbox = getInboxFolder(session);
            inbox.open(Folder.READ_WRITE);
            List<Message> messages = Arrays.asList(inbox.getMessages());
            List<Message> forEvaluationMessages = messages.stream()
                    .filter(MessageDefiner::isGfOutOfListingMessage)
                    .collect(Collectors.toList());
            for (Message message : forEvaluationMessages) {
                message.setFlag(Flags.Flag.DELETED, true);
            }
            inbox.close(true);
            // todo log exceptions
        } catch (MessagingException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private boolean messageIsObsolete(Message message) {

        try {
            Date now = new Date();
            Date msgDate = message.getReceivedDate();
            return now.getTime() - msgDate.getTime() > WEEK;
        } catch (MessagingException e) {
            return false;
        }
    }

    public List<Message> getMessages() throws Exception {

        Session session = createMailSession();
        Folder inbox = getInboxFolder(session);
        inbox.open(Folder.READ_WRITE);
        return Arrays.asList(inbox.getMessages());
    }

    private Session createMailSession() {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth=true", "true");
        properties.put("mail.smtp.port", "465");
        return Session.getDefaultInstance(properties, null);
    }

    private Folder getInboxFolder(Session session) throws Exception {

        Store store = session.getStore("imaps");
        store.connect("smtp.gmail.com", gmailLogin, gmailPassword);
        return store.getFolder("inbox");
    }
}
