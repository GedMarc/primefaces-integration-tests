package org.primefaces.extensions.integrationtests.interactions;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Interface that allows default actions on web sites, for action classes that require events
 */
public interface IWebActionable {

	WebDriver getDriver();

	/**
	 * Clicks on a specific element
	 *
	 * @param jquerySelector
	 */
	default void clickOnItem(IWebElement jquerySelector)
	{
		clickOnItem(jquerySelector.getSelector());
	}
	/**
	 * Clicks on a specific element
	 *
	 * @param jquerySelector
	 */
	default void clickOnItem(String jquerySelector) {
		focusOnWindow();
		//wait2Seconds();
		focus(jquerySelector);
		String runnable = jquerySelector + ".click();";
		((JavascriptExecutor) getDriver()).executeScript(runnable);
	}

	default void clickOnTabWithName(String tabName)
	{
		focusOnWindow();
		String runnable = "$('li.ui-tabs-header a:contains(\"" + tabName + "\")')" + ".click();";
		((JavascriptExecutor) getDriver()).executeScript(runnable);
	}

	/**
	 * Checks a given check box
	 *
	 * @param jquerySelector
	 */
	default void checkBox(String jquerySelector) {}

	/**
	 * Focuses on the given selector item
	 * @param jquerySelector
	 */
	default void focus(String jquerySelector)
	{
		focusOnWindow();
		String focus = jquerySelector + ".focus();";
		((JavascriptExecutor) getDriver()).executeScript(focus);
	}

	/**
	 * Blurs on the given selector item
	 * @param jquerySelector
	 */
	default void blur(String jquerySelector)
	{
		focusOnWindow();
		String blur = jquerySelector + ".blur();";
		((JavascriptExecutor) getDriver()).executeScript(blur);
	}

	/**
	 * Fill with text
	 *
	 * @param jquerySelector
	 */
	default void setValue(IWebElement jquerySelector, String value) {
		setValue(jquerySelector.getSelector(), value);
	}
	/**
	 * Fill with text
	 *
	 * @param jquerySelector
	 */
	default void setValue(String jquerySelector, String value) {
		focusOnWindow();
		focus(jquerySelector);
		String runnable = jquerySelector + ".val(\"" + value + "\");";
		((JavascriptExecutor) getDriver()).executeScript(runnable);
	}

	default String getValue(String jquerySelector)
	{
		focusOnWindow();
		focus(jquerySelector);
		String runnable = "return " + jquerySelector + ".val();";
		String output = (String) ((JavascriptExecutor) getDriver()).executeScript(runnable);
		return output;
	}

	default boolean doesExist(String jquerySelector)
	{
		//Wait for jQuery to load
		JavascriptExecutor jsExec = (JavascriptExecutor) getDriver();
		WebDriverWait jsWait = new WebDriverWait(getDriver(), 10);
		ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) getDriver())
				.executeScript("try{return " + jquerySelector + ".length === 0 ? 1 : 0;}catch(e){return 1;}") == 0);
		return jQueryLoad.apply(getDriver());
	}

	/**
	 * Waits for a dialog to show
	 *
	 * @param cssSearchSelector

	 */
	default void waitForDialog(String cssSearchSelector) {
		waitForElement(cssSearchSelector);
	}

	/**
	 * Waits 10 seconds for an element to be available, or throws a failed exception
	 *
	 * @param cssSearchSelector
	 */
	default void waitForElement(String cssSearchSelector) {
		focusOnWindow();
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		try {
			System.out.println("Waiting for  : " + cssSearchSelector + "  : To be visible");
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSearchSelector)));
			System.out.println("Element found in waiting : " + cssSearchSelector + " - " + element);
		}catch(TimeoutException te)
		{
			fail("Timed Out waiting for element : " + cssSearchSelector);
		}
	}

	default void wait2Seconds()
	{
		WebDriverWait wait = new WebDriverWait(getDriver(), 0,100);
		try {
			wait.withMessage("ready").until(a -> false);
		}catch (Throwable T)
		{
			//intentional
		}
	}

	default void waitSeconds(int seconds, int millis)
	{
		WebDriverWait wait = new WebDriverWait(getDriver(), seconds,millis);
		try {
			wait.withMessage("ready").until(a -> false);
		}catch (Throwable T)
		{
			//intentional
		}
	}

	default void focusOnWindow()
	{
		((JavascriptExecutor)getDriver()).executeScript("window.focus(); intermediate = function () {window.requestAnimationFrame(fn)}\n"
				+ "    window.requestAnimationFrame(intermediate);");
		wait2Seconds();
	}
}
