package com.github.xxbeanxx.mymerit.cucumber.stepdefs;

import com.github.xxbeanxx.mymerit.MymeritApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = MymeritApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
