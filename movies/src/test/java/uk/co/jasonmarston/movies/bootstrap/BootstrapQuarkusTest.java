package uk.co.jasonmarston.movies.bootstrap;

import io.quarkus.runtime.annotations.QuarkusMain;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BootstrapQuarkusTest {

    @Test
    void shouldBeAnnotatedAsQuarkusMain() {
        assertTrue(BootstrapQuarkus.class.isAnnotationPresent(QuarkusMain.class));
    }

    @Test
    void constructorShouldBePrivate() throws Exception {
        Constructor<BootstrapQuarkus> constructor = BootstrapQuarkus.class.getDeclaredConstructor();

        assertTrue(Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);
        assertNotNull(constructor.newInstance());
    }

    @Test
    void shouldExposePublicStaticMainMethod() throws Exception {
        Method mainMethod = BootstrapQuarkus.class.getDeclaredMethod("main", String[].class);

        assertTrue(Modifier.isPublic(mainMethod.getModifiers()));
        assertTrue(Modifier.isStatic(mainMethod.getModifiers()));
    }
}

