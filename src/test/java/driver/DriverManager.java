package driver;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * Holds Playwright objects per thread using {@link ThreadLocal} to enable
 * scenario-based isolation and parallel execution.
 *
 * <p>Each scenario should have its own {@link Page} and {@link BrowserContext}.</p>
 */
public final class DriverManager {

    private static final ThreadLocal<Playwright> TL_PLAYWRIGHT = new ThreadLocal<>();
    private static final ThreadLocal<Browser> TL_BROWSER = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> TL_CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<Page> TL_PAGE = new ThreadLocal<>();

    private DriverManager() {
        // Utility class
    }

    /**
     * Stores Playwright objects for the current thread.
     *
     * @param playwright {@link Playwright} the Playwright instance
     * @param browser {@link Browser} the browser instance
     * @param context {@link BrowserContext} the browser context instance
     * @param page {@link Page} the page instance
     */
    public static void set(Playwright playwright, Browser browser, BrowserContext context, Page page) {
        TL_PLAYWRIGHT.set(playwright);
        TL_BROWSER.set(browser);
        TL_CONTEXT.set(context);
        TL_PAGE.set(page);
    }

    /**
     * Gets the current thread's Playwright page.
     *
     * @return {@link Page} the current page, or {@code null} if not initialised
     */
    public static Page page() {
        return TL_PAGE.get();
    }

    /**
     * Gets the current thread's browser context.
     *
     * @return {@link BrowserContext} the current context, or {@code null} if not initialised
     */
    public static BrowserContext context() {
        return TL_CONTEXT.get();
    }

    /**
     * Gets the current thread's browser.
     *
     * @return {@link Browser} the current browser, or {@code null} if not initialised
     */
    public static Browser browser() {
        return TL_BROWSER.get();
    }

    /**
     * Gets the current thread's Playwright instance.
     *
     * @return {@link Playwright} the current Playwright instance, or {@code null} if not initialised
     */
    public static Playwright playwright() {
        return TL_PLAYWRIGHT.get();
    }

    /**
     * Closes all Playwright resources for the current thread and clears {@link ThreadLocal} state.
     *
     * <p>Resources are closed in reverse order: Page -> Context -> Browser -> Playwright.</p>
     */
    public static void cleanup() {
        safeClose(TL_PAGE.get());
        safeClose(TL_CONTEXT.get());
        safeClose(TL_BROWSER.get());
        safeClose(TL_PLAYWRIGHT.get());

        TL_PAGE.remove();
        TL_CONTEXT.remove();
        TL_BROWSER.remove();
        TL_PLAYWRIGHT.remove();
    }

    /**
     * Safely closes a Playwright resource, ignoring errors during shutdown.
     *
     * @param closeable the object to close (Page/Context/Browser/Playwright)
     */
    private static void safeClose(Object closeable) {
        try {
            if (closeable instanceof Page p) p.close();
            else if (closeable instanceof BrowserContext c) c.close();
            else if (closeable instanceof Browser b) b.close();
            else if (closeable instanceof Playwright pw) pw.close();
        } catch (Exception ignored) {
            // No action required
        }
    }
}
