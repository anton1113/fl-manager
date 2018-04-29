package com.arash.flm.gl.parcer;

import com.arash.flm.gl.model.mail.GlForEvaluationMessage;
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
public class GfForEvaluationParser extends GfMailParser<GlForEvaluationMessage> {

    private static final String TITLE_LABEL = "Title: ";
    private static final String LEVEL_LABEL = "Level: ";
    private static final String DEADLINE_LABEL = "Deadline: ";
    private static final String TASK_LABEL = "Task: ";
    private static final String DETAILED_EXPLANATIONS_LABEL = "Detailed explanations: ";
    private static final String SPECIFIC_REQUIREMENTS_LABEL = "Specific requirements: ";

    @Override
    public GlForEvaluationMessage parse(Message message) throws Exception {

        GlForEvaluationMessage result = new GlForEvaluationMessage();

        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
        int bodyPartsCount = mimeMultipart.getCount();
        for (int i = 0; i < bodyPartsCount; i++) {
            BodyPart currentBodyPart = mimeMultipart.getBodyPart(i);
            if (isHtmlBody(currentBodyPart)) {
                parseHtml(currentBodyPart, result);
            } else {
                parseAttachment(currentBodyPart, result);
            }
        }

        return result;
    }

    private void parseHtml(BodyPart bodyPart, GlForEvaluationMessage message) throws Exception {

        String html = (String) bodyPart.getContent();
        Document document = Jsoup.parse(html);
        Element element = document.select("td.content-block").get(0);
        element.childNodes().parallelStream().forEach(node -> {
            if (node instanceof TextNode) parseTextNode((TextNode) node, message);
            if (node instanceof Element) parseElementNode((Element) node, message);
        });
    }

    private void parseTextNode(TextNode textNode, GlForEvaluationMessage message) {

        String text = textNode.getWholeText();
        if (text == null || text.isEmpty() || text.equals(" ")) return;

        if (text.startsWith(TITLE_LABEL)) {
            String title = removeLabel(text, TITLE_LABEL);
            message.setTitle(title);
        }

        if (text.startsWith(LEVEL_LABEL)) {
            String level = removeLabel(text, LEVEL_LABEL);
            message.setLevel(level);
        }

        if (text.startsWith(DEADLINE_LABEL)) {
            String deadline = removeLabel(text, DEADLINE_LABEL);
            message.setDeadline(deadline);
        }

        if (text.startsWith(TASK_LABEL)) {
            String task = removeLabel(text, TASK_LABEL);
            message.setTask(task);
        }

        if (text.startsWith(DETAILED_EXPLANATIONS_LABEL)) {
            String detailedExplanations = removeLabel(text, DETAILED_EXPLANATIONS_LABEL);
            message.setDetailedExplanations(detailedExplanations);
        }

        if (text.startsWith(SPECIFIC_REQUIREMENTS_LABEL)) {
            String specificRequirements = removeLabel(text, SPECIFIC_REQUIREMENTS_LABEL);
            message.setSpecificRequirements(specificRequirements);
        }
    }

    private void parseElementNode(Element elementNode, GlForEvaluationMessage message) {

        if (!"a".equals(elementNode.tag().getName())) return;
        String offerLink = elementNode.attributes().get("href");
        message.setOfferLink(offerLink);
    }

    private void parseAttachment(BodyPart bodyPart, GlForEvaluationMessage message) {

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
