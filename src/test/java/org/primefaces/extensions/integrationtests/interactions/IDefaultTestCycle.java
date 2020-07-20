package org.primefaces.extensions.integrationtests.interactions;


import org.junit.jupiter.api.*;

import java.util.logging.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface IDefaultTestCycle {

	Logger LOG = Logger.getLogger(IDefaultTestCycle.class.getName());

	@BeforeAll
	default void beforeAllTests() {
		LOG.info(" Before all tests ");
	}

	@AfterAll
	default void afterAllTests() {
		LOG.info(" After all tests ");
	}

	@BeforeEach
	default void beforeEachTest(TestInfo testInfo) {
		LOG.info("Starting : " + testInfo );
	}

	@AfterEach
	default void afterEachTest(TestInfo testInfo) {
	}
}
