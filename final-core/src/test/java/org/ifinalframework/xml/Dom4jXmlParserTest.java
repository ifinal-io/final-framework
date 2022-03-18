package org.ifinalframework.xml;

import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.ifinalframework.json.Json;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ilikly
 * @version 1.2.4
 **/
class Dom4jXmlParserTest {

    @Test
    @SneakyThrows
    void parse() {
        SAXReader reader = new SAXReader();
        Document document = reader.read(getClass().getResourceAsStream("/parse.xml"));
        Element element = new Dom4jXmlParser().parse(document);
        assertNotNull(element);
    }

    @Test
    void test(){
        Object object = Json.toObject("{\n" +
                "  \"direction\": 0,\n" +
                "  \"screen_block\": [\n" +
                "    {\n" +
                "      \"start_x\": 0,\n" +
                "      \"start_y\": 2,\n" +
                "      \"end_x\": 110,\n" +
                "      \"end_y\": 2,\n" +
                "      \"content_type\": \"CONTENT_TYPE_LINE\",\n" +
                "      \"content_color\": \"BLACK\",\n" +
                "      \"content_reverse\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"start_x\": 5,\n" +
                "      \"start_y\": 5,\n" +
                "      \"end_x\": 80,\n" +
                "      \"end_y\": 30,\n" +
                "      \"font_type\": \"Zfull-GB Bold 16\",\n" +
                "      \"font_size\": 12,\n" +
                "      \"content_type\": \"CONTENT_TYPE_TEXT\",\n" +
                "      \"content_title\": \"name\",\n" +
                "      \"content_value\": \" 苹 果 \",\n" +
                "      \"content_color\": \"BLACK\",\n" +
                "      \"content_alignment\": \"LEFT\",\n" +
                "      \"number_script\": \"NONE\",\n" +
                "      \"number_gap\": \"CONSECUTIVE\",\n" +
                "      \"font_size_script\": 12,\n" +
                "      \"content_reverse\": false,\n" +
                "      \"origin_font_type\": 0\n" +
                "    }\n" +
                "  ]\n" +
                "}");
        System.out.println(Json.toJson(Arrays.asList(Json.toJson(object))));
    }

}