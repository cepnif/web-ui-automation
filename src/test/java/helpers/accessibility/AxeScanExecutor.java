package helpers.accessibility;

import com.microsoft.playwright.Page;

import java.util.Map;

public final class AxeScanExecutor {

    private AxeScanExecutor() {}

    @SuppressWarnings("unchecked")
    public static Map<String, Object> runFullScan(Page page) {
        AxeScriptLoader.injectAxe(page);

        return (Map<String, Object>) page.evaluate("""
            () => {
              return axe.run(document, {
                runOnly: {
                  type: 'tag',
                  values: ['wcag2a', 'wcag2aa', 'wcag21a', 'wcag21aa']
                }
              });
            }
        """);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> runScopedScan(Page page, String cssSelector) {
        AxeScriptLoader.injectAxe(page);

        return (Map<String, Object>) page.evaluate("""
            (selector) => {
              const context = document.querySelector(selector);
              return axe.run(context);
            }
        """, cssSelector);
    }
}
