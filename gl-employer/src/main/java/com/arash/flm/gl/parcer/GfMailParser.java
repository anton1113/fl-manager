package com.arash.flm.gl.parcer;

import com.arash.flm.gl.model.mail.GlMessage;

import javax.mail.Message;

/**
 * Created by anton on 29.04.18.
 *
 */
public abstract class GfMailParser<T extends Object> {

    public abstract T parse(Message message) throws Exception;
}
