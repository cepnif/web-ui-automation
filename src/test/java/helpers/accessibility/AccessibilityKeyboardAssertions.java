package helpers.accessibility;

import com.microsoft.playwright.Page;
import driver.DriverManager;

import static org.assertj.core.api.Assertions.assertThat;

public final class AccessibilityKeyboardAssertions {

    private AccessibilityKeyboardAssertions() {}

    private static Page page() {
        return DriverManager.page();
    }

    public static void assertFocusableElementsPresent() {
        int count = ((Number) page().evaluate("""
            () => document.querySelectorAll(
              'a[href], button, input, select, textarea'
            ).length
        """)).intValue();

        assertThat(count)
                .as("Page must contain focusable elements")
                .isGreaterThan(0);
    }

    public static void assertFocusVisible() {
        Boolean visible = (Boolean) page().evaluate("""
            () => {
                const el = document.activeElement;
                if (!el) return false;
                const style = window.getComputedStyle(el);
                return style.outlineStyle !== 'none' ||
                       style.boxShadow !== 'none';
            }
        """);

        assertThat(visible)
                .as("Focus indicator must be visible")
                .isTrue();
    }
}
