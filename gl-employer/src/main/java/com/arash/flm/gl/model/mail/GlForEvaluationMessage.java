package com.arash.flm.gl.model.mail;

import lombok.Data;

import java.util.List;

/**
 * Created by anton on 29.04.18.
 *
 */
@Data
public class GlForEvaluationMessage extends GlMessage {

    private String title;
    private String level;
    private String deadline;
    private String task;
    private String detailedExplanations;
    private String specificRequirements;
    private String offerLink;
    private List<String> attachments;
}
