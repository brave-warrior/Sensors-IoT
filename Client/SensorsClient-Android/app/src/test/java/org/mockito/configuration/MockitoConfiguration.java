package org.mockito.configuration;

/**
 * Required for disabling mockito cache (http://stackoverflow.com/a/35076338/927014)
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public class MockitoConfiguration extends DefaultMockitoConfiguration {

    @Override
    public boolean enableClassCache() {
        return false;
    }
}
