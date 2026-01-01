package helpers.accessibility;

import com.microsoft.playwright.Page;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class AxeScriptLoader {

    private AxeScriptLoader() {}

    public static void injectAxe(Page page) {
        try (InputStream is = AxeScriptLoader.class
                .getClassLoader()
                .getResourceAsStream("axe/axe.min.js")) {

            if (is == null) {
                throw new IllegalStateException("axe.min.js not found under src/test/resources/axe");
            }

            String script = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            page.addScriptTag(new Page.AddScriptTagOptions().setContent(script));

        } catch (Exception e) {
            throw new RuntimeException("Failed to inject axe-core", e);
        }
    }
}
