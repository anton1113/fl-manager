package com.arash.flm.gl.model.mail.db;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by anton on 08.05.18.
 *
 */
@Data
@Document(collection = "gf_offer")
public class GfOffer {

    @Id
    private String id;
    private String title;
    private String level;
    private String deadline;
    private String task;
    private String detailedExplanations;
    private String specificRequirements;
    private String offerLink;
    private List<Byte[]> attachments;
}
