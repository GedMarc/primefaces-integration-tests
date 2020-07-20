package org.primefaces.extensions.integrationtests.interactions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Assists in finding web components
 */
public interface IWebElementFinder {

	WebDriver getDriver();
	
	default boolean findExistanceWithJQuerySelector(String jquerySelector) {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		String jsExecute = "return " + jquerySelector + ".length > 0;";
		Object returned = js.executeScript(jsExecute);
		boolean ret = (boolean) returned;
		return ret;
	}

	default void waitForElementToShowGuaranteed(IWebElement jquerySelector)
	{
		waitForElementToShowGuaranteed(jquerySelector.getSelector());
	}
	/**
	 * Only use when you know it is actually going to show up
	 * @param jquerySelector
	 */
	default void waitForElementToShowGuaranteed(String jquerySelector)
	{
		//Wait for jQuery to load
		JavascriptExecutor jsExec = (JavascriptExecutor) getDriver();
		WebDriverWait jsWait = new WebDriverWait(getDriver(), 10);
		ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) getDriver())
				.executeScript("try{return " + jquerySelector + ".length === 0 ? 1 : 0;}catch(e){return 1;}") == 0);

		//Get JQuery is Ready
		boolean jqueryReady = (Boolean) jsExec.executeScript("try {return " +jquerySelector + ".length === 0;}catch(e){return false;}");

		//Wait JQuery until it is Ready!
		if(!jqueryReady) {
			System.out.println("Element [" + jquerySelector + "] is NOT Ready!");
			//Wait for jQuery to load
			jsWait.until(jQueryLoad);
			System.out.println("Element [" + jquerySelector + "] is NOW Ready!");
		} else {
			System.out.println("Element [" + jquerySelector + "] is Ready!");
		}
	}


	//Wait for JQuery Load
	default void waitForJQueryLoad() {
		//Wait for jQuery to load
		JavascriptExecutor jsExec = (JavascriptExecutor) getDriver();
		WebDriverWait jsWait = new WebDriverWait(getDriver(), 10);
		ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) getDriver())
				.executeScript("try{return jQuery.active;}catch(e){return 1;}") == 0);

		//Get JQuery is Ready
		boolean jqueryReady = (Boolean) jsExec.executeScript("try {return jQuery.active==0;}catch(e){return false;}");

		//Wait JQuery until it is Ready!
		if(!jqueryReady) {
			System.out.println("JQuery is NOT Ready!");
			//Wait for jQuery to load
			jsWait.until(jQueryLoad);
			System.out.println("JQuery is NOW Ready!");
		} else {
			System.out.println("JQuery is Ready!");
		}
	}
}
