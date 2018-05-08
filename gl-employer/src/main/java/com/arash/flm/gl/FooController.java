package com.arash.flm.gl;

import com.arash.flm.gl.mail.GfGmailOffersReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by anton on 08.05.18.
 *
 */
@Component
public class FooController {

    @Autowired private GfGmailOffersReader offersReader;

    @PostConstruct
    private void init() {

        offersReader.readOffers();
    }
}
