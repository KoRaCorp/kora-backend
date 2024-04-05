package corp.kora.scrap.client;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class ScrapByUrlClient {

	public void scrapByUrl(String url) {
		try {
			Document document = Jsoup.connect(url).get();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
