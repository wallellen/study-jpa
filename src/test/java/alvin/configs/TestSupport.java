package alvin.configs;

import com.google.inject.Guice;
import org.junit.Before;

public class TestSupport {

    @Before
    public void setUp() {
        Guice.createInjector(new TestModule()).injectMembers(this);
    }
}
