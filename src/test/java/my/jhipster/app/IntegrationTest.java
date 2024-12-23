package my.jhipster.app;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import my.jhipster.app.config.AsyncSyncConfiguration;
import my.jhipster.app.config.EmbeddedMongo;
import my.jhipster.app.config.JacksonConfiguration;
import my.jhipster.app.config.TestSecurityConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { MyjhappApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class })
@EmbeddedMongo
public @interface IntegrationTest {
}
