package helpers.accessibility;

import com.microsoft.playwright.Page;
import driver.DriverManager;

import java.util.Map;

public final class AccessibilityChecks {

    private AccessibilityChecks() {}

    public static AxeResult scanFullPage() {
        Page page = DriverManager.page();
        Map<String, Object> raw = AxeScanExecutor.runFullScan(page);
        return AxeResultParser.parse(raw);
    }

    public static AxeResult scanSection(String cssSelector) {
        Page page = DriverManager.page();
        Map<String, Object> raw = AxeScanExecutor.runScopedScan(page, cssSelector);
        return AxeResultParser.parse(raw);
    }
}
