package v5.gidd.entities.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import v5.gidd.entities.message.Status;
import v5.gidd.throwable.GiddException;

import static org.junit.jupiter.api.Assertions.*;

class TokenKeyTest {
    private StrictToken strictToken;

    @BeforeEach
    void setup() {
        // Valid key
        strictToken = new StrictToken("Ymg9I0j0m1zP3eEK5XZ4hb4wM6s8XGOwOCrP");
    }

    @Test
    void testExceptionOnBadTokenKey() {
        strictToken.setKey("badkey");

        GiddException thrown = assertThrows(GiddException.class, strictToken::inspect, "Expected inspect() to throw exception");
        assertEquals(Status.MALFORMED_VERIFICATION_TOKEN, thrown.getStatus());
    }

    @Test
    void testValidKeySuccess() {
        assertDoesNotThrow(strictToken::inspect);
    }
}