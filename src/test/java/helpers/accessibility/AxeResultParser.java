package helpers.accessibility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class AxeResultParser {

    private AxeResultParser() {}

    public static AxeResult parse(Map<String, Object> rawResult) {

        List<Map<String, Object>> rawViolations =
                (List<Map<String, Object>>) rawResult.get("violations");

        List<AxeViolation> violations = new ArrayList<>();

        for (Map<String, Object> v : rawViolations) {

            List<AxeNode> nodes = new ArrayList<>();
            List<Map<String, Object>> rawNodes =
                    (List<Map<String, Object>>) v.get("nodes");

            for (Map<String, Object> n : rawNodes) {
                nodes.add(new AxeNode(
                        (List<String>) n.get("target"),
                        (String) n.get("html")
                ));
            }

            violations.add(new AxeViolation(
                    (String) v.get("id"),
                    (String) v.get("impact"),
                    (String) v.get("description"),
                    (String) v.get("help"),
                    nodes
            ));
        }

        return new AxeResult(violations);
    }
}
