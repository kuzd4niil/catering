package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.demo.security.ApplicationUserRole.GUEST;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void checkEnum() {
		System.out.println("В виде числа будет выглядеть так" );
	}

}
