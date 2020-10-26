package br.com.digital.bank.api.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = {"src/test/resources/features"},
        glue = {"br.com.digital.bank.api.config","br.com.digital.bank.api.steps"})
public class CucumberRunnerTest {
}
