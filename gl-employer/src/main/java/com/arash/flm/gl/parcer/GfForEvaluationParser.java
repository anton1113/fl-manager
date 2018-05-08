package com.arash.flm.gl.parcer;

import com.arash.flm.gl.model.mail.db.GfOffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.springframework.stereotype.Component;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.internet.MimeMultipart;

/**
 * Created by anton on 29.04.18.
 *
 */
@Component
public class GfForEvaluationParser extends GfMailParser<GfOffer> {

    private static final String TITLE_LABEL = "Title: ";
    private static final String LEVEL_LABEL = "Level: ";
    private static final String DEADLINE_LABEL = "Deadline: ";
    private static final String TASK_LABEL = "Task: ";
    private static final String DETAILED_EXPLANATIONS_LABEL = "Detailed explanations: ";
    private static final String SPECIFIC_REQUIREMENTS_LABEL = "Specific requirements: ";

    @Override
    public GfOffer parse(Message message) throws Exception {

        GfOffer gfOffer = new GfOffer();
        gfOffer.setId(message.getSubject().substring(6, 12));

        if (message.getContent() instanceof String) {
            parseString((String) message.getContent(), gfOffer);
        } else if (message.getContent() instanceof MimeMultipart) {
            parseMimeMultipart((MimeMultipart) message.getContent(), gfOffer);
        }

        return gfOffer;
    }

    private void parseMimeMultipart(MimeMultipart mimeMultipart, GfOffer gfOffer) throws Exception {

        int bodyPartsCount = mimeMultipart.getCount();
        for (int i = 0; i < bodyPartsCount; i++) {
            BodyPart currentBodyPart = mimeMultipart.getBodyPart(i);
            if (isHtmlBody(currentBodyPart)) {
                parseHtml(currentBodyPart, gfOffer);
            } else {
                parseAttachment(currentBodyPart, gfOffer);
            }
        }
    }

    private void parseString(String content, GfOffer gfOffer) {

        Document document = Jsoup.parse(content);
        Element element = document.select("td.content-block").get(0);
        element.childNodes().parallelStream().forEach(node -> {
            if (node instanceof TextNode) parseTextNode((TextNode) node, gfOffer);
            if (node instanceof Element) parseElementNode((Element) node, gfOffer);
        });
    }

    private void parseHtml(BodyPart bodyPart, GfOffer gfOffer) throws Exception {

        String content = (String) bodyPart.getContent();
        parseString(content, gfOffer);
    }

    private void parseTextNode(TextNode textNode, GfOffer gfOffer) {

        String text = textNode.getWholeText();
        if (text == null || text.isEmpty() || text.equals(" ")) return;

        if (text.startsWith(TITLE_LABEL)) {
            String title = removeLabel(text, TITLE_LABEL);
            gfOffer.setTitle(title);
        }

        if (text.startsWith(LEVEL_LABEL)) {
            String level = removeLabel(text, LEVEL_LABEL);
            gfOffer.setLevel(level);
        }

        if (text.startsWith(DEADLINE_LABEL)) {
            String deadline = removeLabel(text, DEADLINE_LABEL);
            gfOffer.setDeadline(deadline);
        }

        if (text.startsWith(TASK_LABEL)) {
            String task = removeLabel(text, TASK_LABEL);
            gfOffer.setTask(task);
        }

        if (text.startsWith(DETAILED_EXPLANATIONS_LABEL)) {
            String detailedExplanations = removeLabel(text, DETAILED_EXPLANATIONS_LABEL);
            gfOffer.setDetailedExplanations(detailedExplanations);
        }

        if (text.startsWith(SPECIFIC_REQUIREMENTS_LABEL)) {
            String specificRequirements = removeLabel(text, SPECIFIC_REQUIREMENTS_LABEL);
            gfOffer.setSpecificRequirements(specificRequirements);
        }
    }

    private void parseElementNode(Element elementNode, GfOffer gfOffer) {

        if (!"a".equals(elementNode.tag().getName())) return;
        String offerLink = elementNode.attributes().get("href");
        gfOffer.setOfferLink(offerLink);
    }

    private void parseAttachment(BodyPart bodyPart, GfOffer gfOffer) {

    }

    private boolean isHtmlBody(BodyPart bodyPart) throws Exception {
        return bodyPart.isMimeType("text/html");
    }

    private String removeLabel(String origin, String label) {
        return origin.replace(label, "")
                .replace("\n", "")
                .replace("\r", "");
    }
}
