package com.arash.flm.gl.mail.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static javax.mail.Folder.READ_ONLY;
import static javax.mail.Folder.READ_WRITE;

/**
 * Created by anton on 08.05.18.
 *
 */
@Component
public class GmailConnector {

    private static final Logger LOGGER = LogManager.getLogger(GmailConnector.class);

    @Value("${flm.gmail.login}") private String gmailLogin;
    @Value("${flm.gmail.password}") private String gmailPassword;

    public List<Message> getLastMessages(int amount, boolean writable) {

        try {
            int accessLevel = writable ? READ_WRITE : READ_ONLY;
            Session session = createMailSession();
            Folder inbox = getInboxFolder(session);
            inbox.open(accessLevel);
            int start = inbox.getMessageCount() - amount + 1;
            int end = inbox.getMessageCount();
            return Arrays.asList(inbox.getMessages(start, end));
        } catch (Exception e) {
            LOGGER.error("Unable to get messages", e);
            return null;
        }
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
