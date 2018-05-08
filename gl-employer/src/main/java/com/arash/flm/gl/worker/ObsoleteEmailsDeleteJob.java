package com.arash.flm.gl.worker;

import com.arash.flm.gl.mail.GfGmailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by anton on 30.04.18.
 *
 */
@Component
public class ObsoleteEmailsDeleteJob implements Runnable {

    @Autowired private GfGmailManager gmailManager;

    @Override
    public void run() {

        gmailManager.deleteObsoleteMessages();
    }
}
