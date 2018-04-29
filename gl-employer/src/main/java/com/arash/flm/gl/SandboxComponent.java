package com.arash.flm.gl;

import com.arash.flm.gl.parcer.GfForEvaluationParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.*;
import java.util.Properties;

/**
 * Created by anton on 29.04.18.
 *
 */
@Component
public class SandboxComponent {

    @Autowired private GfForEvaluationParser parser;

    @Value("${flm.gmail.login}") private String gmailLogin;
    @Value("${flm.gmail.password}") private String gmailPassword;

    private Session session;
    private Folder inbox;

    @PostConstruct
    private void init() throws Exception {
        session = createMailSession();
        inbox = getInboxFolder();
        getMessages();
    }

    private Message[] getMessages() throws Exception {

        inbox.open(Folder.READ_WRITE);
        Message[] messages = inbox.getMessages();
        Message message = messages[messages.length - 6];
        parser.parse(message);
        return messages;
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

    private Folder getInboxFolder() throws Exception {

        Store store = session.getStore("imaps");
        store.connect("smtp.gmail.com", gmailLogin, gmailPassword);
        return store.getFolder("inbox");
    }
}
