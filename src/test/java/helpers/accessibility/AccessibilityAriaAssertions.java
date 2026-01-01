package helpers.accessibility;

import com.microsoft.playwright.Page;
import driver.DriverManager;

import static org.assertj.core.api.Assertions.assertThat;

public final class AccessibilityAriaAssertions {

    private AccessibilityAriaAssertions() {}

    private static Page page() {
        return DriverManager.page();
    }

    /** Interactive elements must have accessible names */
    public static void assertAllInteractiveElementsHaveAccessibleNames() {
        int invalid = ((Number) page().evaluate("""
            () => {
                const elements = Array.from(
                  document.querySelectorAll('a, button, input, select, textarea')
                );

                return elements.filter(el => {
                  const name =
                    el.getAttribute('aria-label') ||
                    el.getAttribute('aria-labelledby') ||
                    el.textContent?.trim();

                  return !name;
                }).length;
            }
        """)).intValue();

        assertThat(invalid)
                .as("All interactive elements must have accessible names")
                .isZero();
    }

    /** Content sections should be announced correctly */
    public static void assertSectionsAreAnnounced() {
        int invalid = ((Number) page().evaluate("""
            () => {
                const sections = Array.from(
                  document.querySelectorAll('main section')
                );
                return sections.filter(s =>
                  !s.hasAttribute('aria-labelledby') &&
                  !s.querySelector('h2, h3')
                ).length;
            }
        """)).intValue();

        assertThat(invalid)
                .as("Content sections must be announced correctly")
                .isZero();
    }

    /** Decorative images hidden */
    public static void assertDecorativeImagesAreHidden() {
        int invalid = ((Number) page().evaluate("""
            () => {
                return Array.from(document.querySelectorAll('img'))
                  .filter(img =>
                    img.getAttribute('alt') === null
                  ).length;
            }
        """)).intValue();

        assertThat(invalid)
                .as("Decorative images must be hidden from screen readers")
                .isZero();
    }

    public static void assertAllFormFieldsHaveLabels() {
        int unlabeled = ((Number) page().evaluate("""
        () => {
            const fields = Array.from(
                document.querySelectorAll('input, select, textarea')
            );

            return fields.filter(field => {
                const hasLabel =
                    field.labels && field.labels.length > 0;

                const hasAriaLabel =
                    field.hasAttribute('aria-label') ||
                    field.hasAttribute('aria-labelledby');

                return !hasLabel && !hasAriaLabel;
            }).length;
        }
    """)).intValue();

        assertThat(unlabeled)
                .as("All form fields must have accessible labels")
                .isZero();
    }

    /**
     * Ensures the email input field has an accessible label.
     *
     * WCAG 1.3.1, 3.3.2
     */
    public static void assertEmailFieldHasAccessibleLabel() {
        boolean hasLabel = (Boolean) page().evaluate("""
        () => {
            const email = document.querySelector('input[type="email"], #email');
            if (!email) return false;

            const hasLabel =
                email.labels && email.labels.length > 0;

            const hasAria =
                email.hasAttribute('aria-label') ||
                email.hasAttribute('aria-labelledby');

            return hasLabel || hasAria;
        }
    """);

        assertThat(hasLabel)
                .as("Email input field must have an accessible label")
                .isTrue();
    }

}
