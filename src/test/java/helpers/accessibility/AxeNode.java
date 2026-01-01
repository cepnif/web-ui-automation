package helpers.accessibility;

import java.util.List;

public record AxeNode(
        List<String> target,
        String html
) {}
