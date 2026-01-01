package helpers.accessibility;

import java.util.List;

public record AxeViolation(
        String id,
        String impact,
        String description,
        String help,
        List<AxeNode> nodes
) {}
