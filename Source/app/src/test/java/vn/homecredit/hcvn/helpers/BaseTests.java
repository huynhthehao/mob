/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 2:50 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.helpers;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class BaseTests {
    private static StringBuilder _logger;
    private static String _className = null;

    private static final String TEST_CLASS_KEY = "#TESTCLASS";

    public BaseTests(){
        if(_className == null)
            _className = this.getClass().getSimpleName();
    }

    @BeforeClass
    public static void initAllTests() {
        _logger = new StringBuilder();
        _logger.append(TEST_CLASS_KEY + " Results:");
        _logger.append("\n------------------------------------------------------------");
    }

    @AfterClass
    public static void summing(){
        _logger.append("\n------------------------------------------------------------\n");
        String logString = _logger.toString().replace(TEST_CLASS_KEY, _className);
        System.out.println(logString);
    }

    @Rule
    public TestRule watcher = new TestWatcher() {
        long startedTick = 0;
        String status = "";
        protected void starting(Description description) {
            startedTick = System.nanoTime();
        }

        protected void succeeded(Description description){
            status = "SUCCEEDED";
        }

        protected void failed(Throwable e, Description description){
            status = "FAILED";
        }

        protected void finished(Description description){
            long endedTick = System.nanoTime();
            int milliseconds = (int)(endedTick - startedTick)/1000000;

            _logger.append("\n(" + status + ")\t"
                    + description.getMethodName()
                    + " \tin "
                    + String.valueOf(milliseconds)
                    + " ms");
        }
    };
}
