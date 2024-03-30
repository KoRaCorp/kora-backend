package corp.kora.api.scraping.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScrapingTestController {

	@GetMapping("/api/scrapings/test")
	@ResponseStatus(HttpStatus.OK)
	public String scrapingTest() {
		return "Hello World!";
	}
}
