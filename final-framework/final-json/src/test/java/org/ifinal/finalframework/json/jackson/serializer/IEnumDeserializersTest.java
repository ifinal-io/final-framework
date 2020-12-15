package org.ifinal.finalframework.json.jackson.serializer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.ifinal.finalframework.annotation.data.YN;
import org.ifinal.finalframework.json.jackson.deserializer.EnumDeserializer;
import org.junit.jupiter.api.Test;

/**
 * IEnumDeserializersTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class IEnumDeserializersTest {

    final IEnumDeserializers deserializers = new IEnumDeserializers();

    @Test
    void findEnumDeserializer() throws JsonMappingException {
        JsonDeserializer<?> enumDeserializer = deserializers.findEnumDeserializer(YN.class, null, null);
        assertTrue(enumDeserializer instanceof EnumDeserializer);
    }

}
