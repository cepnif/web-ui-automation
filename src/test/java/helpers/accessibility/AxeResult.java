package helpers.accessibility;

import java.util.List;

public record AxeResult(
        List<AxeViolation> violations
) {}
