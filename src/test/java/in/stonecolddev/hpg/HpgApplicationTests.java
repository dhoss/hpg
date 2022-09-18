package in.stonecolddev.hpg;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("it-test")
@Tag("it-test")
class HpgApplicationTests {

	@Test
	void contextLoads() {
	}
}