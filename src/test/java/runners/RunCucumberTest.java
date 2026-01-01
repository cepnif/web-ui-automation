package runners;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.*;

/**
 * Central Cucumber runner.
 *
 * <p>All execution behaviour (tags, parallelism, glue, plugins)
 * is controlled from here and can be overridden via system properties.</p>
 */
@Suite
@IncludeEngines("cucumber")
@SelectPackages("features")

@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "steps,hooks")

// ---------------------- TAG FILTERING ----------------------
@ConfigurationParameter(
        key = FILTER_TAGS_PROPERTY_NAME,
        value = ""
)

// ---------------------- PLUGINS ----------------------------
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value =
                "pretty," +
                        "summary," +
                        "html:target/cucumber-report.html," +
                        "json:target/cucumber-report.json"
)

// ---------------------- PARALLEL EXECUTION -----------------
@ConfigurationParameter(
        key = PARALLEL_EXECUTION_ENABLED_PROPERTY_NAME,
        value = "false"
)

@ConfigurationParameter(
        key = PARALLEL_CONFIG_STRATEGY_PROPERTY_NAME,
        value = "fixed"
)

@ConfigurationParameter(
        key = PARALLEL_CONFIG_FIXED_PARALLELISM_PROPERTY_NAME,
        value = "4"
)

public class RunCucumberTest {
}
