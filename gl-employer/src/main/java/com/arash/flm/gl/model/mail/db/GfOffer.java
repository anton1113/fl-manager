package com.arash.flm.gl.model.mail.db;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by anton on 08.05.18.
 *
 */
@Data
@Document(collection = "gf_offer")
public class GfOffer {

    private String title;
    private String level;
    private String deadline;
    private String task;
    private String detailedExplanations;
    private String specificRequirements;
    private List<Byte[]> attachments;
}
