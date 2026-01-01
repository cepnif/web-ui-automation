package runners;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("features")

@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "steps,hooks")

@ConfigurationParameter(
        key = FILTER_TAGS_PROPERTY_NAME,
        value = "@functional"
)

@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty,summary"
)

public class RunFunctionalTests {
}
