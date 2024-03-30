package corp.kora.api.scraping.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import corp.kora.scrap.client.ScrapByUrlClient;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ScrapingTestController {
	private final ScrapByUrlClient scrapByUrlClient;

	@GetMapping("/api/scrapings/test")
	@ResponseStatus(HttpStatus.OK)
	public String scrapingTest(
		@PathParam("scrapingUrl") String scrapingUrl
	) {
		scrapByUrlClient.scrapByUrl(scrapingUrl);
		return "Hello World!";
	}
}
