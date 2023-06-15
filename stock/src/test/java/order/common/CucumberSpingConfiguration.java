package order.common;

import io.cucumber.spring.CucumberContextConfiguration;
import order.StockApplication;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = { StockApplication.class })
public class CucumberSpingConfiguration {}
