package com.rappcha.serverjs.web;

import com.rappcha.serverjs.pool.JavascriptContainerPool;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/serverjs-context.xml" })
public class ServerJSConfigTest {

  @Autowired
  private JavascriptContainerPool containerPool;

  @Test
  public void testConfigOK() {
    assertNotNull(containerPool);
  }

}
