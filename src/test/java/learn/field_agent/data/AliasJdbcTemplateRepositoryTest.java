package learn.field_agent.data;

import learn.field_agent.models.Alias;
import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author NAHID
 * Date  10,  June, 2021
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AliasJdbcTemplateRepositoryTest {

    @Autowired
    AliasJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldAddAlias() {
        Alias alias = new Alias();
        alias.setName("TEST");
        alias.setPersona("DEMO");
        Alias actual = repository.add(alias);
        assertNotNull(actual);
    }

    @Test
    void shouldFindAlias() {
        List<Alias> alias = repository.findAll();
        assertNotNull(alias);
        assertFalse(alias.size() > 0);
    }

    @Test
    void shouldUpdateAlias() {

        Alias alias = new Alias();
        alias.setAliasId(3);
        alias.setName("TEST");

        assertFalse(repository.update(alias));
    }

    @Test
    void shouldDeleteAlias() {
        assertFalse(repository.deleteById(2));
    }

}
