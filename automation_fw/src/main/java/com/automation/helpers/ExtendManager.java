package com.automation.helpers;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtendManager {

    public static ExtentReports extent;

    public static ExtentReports getInstance(String versrion){
        if (extent == null)
            createInstance("./test_report.html", versrion);
        return extent;
    }

    private static ExtentReports createInstance(String fileName, String versrion) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("App version:" + versrion);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        return extent;
    }
}
