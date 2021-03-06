package pl.altkom.asc.lab.micronaut.poc.policy;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpStatus;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.altkom.asc.lab.micronaut.poc.policy.service.api.v1.Health;

import static org.junit.Assert.assertEquals;

public class HelloControllerTest {

    private static EmbeddedServer server;
    private static HelloTestClient client;

    @BeforeClass
    public static void setup() {
        server = ApplicationContext.run(EmbeddedServer.class);
        client = server.getApplicationContext().createBean(HelloTestClient.class, server.getURL());
    }

    @AfterClass
    public static void cleanup() {
        if (server != null) {
            server.stop();
        }
    }

    @Test
    public void testIndex() {
        assertEquals(HttpStatus.OK, client.index());
    }

    @Test
    public void testVersion() {
        Health actualInfo = client.version();
        Health expectedInfo = new Health("1.0", "OK");

        assertEquals(expectedInfo.toString(), actualInfo.toString());
        assertEquals(expectedInfo.getStatus(), actualInfo.getStatus());
        assertEquals(expectedInfo.getVer(), actualInfo.getVer());
    }
}
