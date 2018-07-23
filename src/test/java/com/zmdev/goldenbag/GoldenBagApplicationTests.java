package com.zmdev.goldenbag;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
// Enable JMX so we can test the MBeans (you can't do this in a properties file)
@TestPropertySource(properties = {"spring.jmx.enabled:true",
        "spring.datasource.jmx-enabled:true"})
@ActiveProfiles("test_db")
public class GoldenBagApplicationTests {

    @Test
    public void contextLoads() {
    }
}
