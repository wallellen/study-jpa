package alvin.configs;

import com.google.inject.Guice;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.junit.Before;

public class TestSupport {

    @Before
    public void setUp() {
        Guice.createInjector(new TestModule(), new JpaPersistModule("default")).injectMembers(this);
    }
}
