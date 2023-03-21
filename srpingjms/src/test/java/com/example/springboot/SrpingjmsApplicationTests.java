package com.example.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SrpingjmsApplicationTests {

	@Autowired
	MessageSender sender;

	@Test
	public void testSendAndReceive() {
		sender.send("Hello spring JMS!!!");
	}
}
