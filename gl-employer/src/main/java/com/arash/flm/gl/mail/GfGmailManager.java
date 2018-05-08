package com.arash.flm.gl.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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


    }
}
