package com.mbm.base;

import org.testng.annotations.AfterMethod;

import com.mbm.filelogging.FileLogManager;

public class BaseTest {

  


    @AfterMethod(alwaysRun = true)
    public void afterMethod() {

        FileLogManager.close();
    }
}
