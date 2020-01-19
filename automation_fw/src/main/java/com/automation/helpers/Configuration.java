package com.automation.helpers;

import com.automation.TestBase;

public class Configuration extends TestBase {

    public static String TEST_URL = "https://www.airbnb.com/";

    public class TimeOuts{
        public static final int FIVE_SEC_WAIT = 5;
        public static final int TEN_SEC_WAIT = 10;
        public static final int THIRTY_SEC_WAIT = 30;
        public static final int SIXTY_SEC_WAIT = 60;
    }

    public class LogType{
        public static final String  PASS = "Pass";
        public static final String  FAIL = "Fail";
        public static final String  WARNING = "Warning";
        public static final String  INFO = "Info";
        public static final String  ERROR = "Error";
    }
}
