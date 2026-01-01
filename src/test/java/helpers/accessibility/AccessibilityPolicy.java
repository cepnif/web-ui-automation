package helpers.accessibility;

import config.ConfigReader;

import java.util.List;

public final class AccessibilityPolicy {

    private AccessibilityPolicy() {}

    public static void validate(AxeResult result) {

        List<String> failOn =
                List.of(ConfigReader.get("accessibility.failOn").split(","));

        result.violations().forEach(v -> {
            if (failOn.contains(v.impact())) {
                throw new AssertionError(buildMessage(v));
            }
        });
    }

    private static String buildMessage(AxeViolation v) {
        return """
        Accessibility violation detected
        Rule: %s
        Impact: %s
        Description: %s
        Affected nodes: %d
        """.formatted(
                v.id(),
                v.impact(),
                v.description(),
                v.nodes().size()
        );
    }
}
