package corp.kora.bucket.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import corp.kora.bucket.domain.repository.BucketReader;
import corp.kora.bucket.domain.repository.BucketRepository;
import corp.kora.bucket.infrastructure.persistence.BucketReaderV1;
import corp.kora.bucket.infrastructure.persistence.BucketRepositoryV1;
import jakarta.persistence.EntityManager;

@Configuration
public class BucketConfig {

	@Bean
	public BucketReader bucketReader(EntityManager entityManager) {
		return new BucketReaderV1(entityManager);
	}

	@Bean
	public BucketRepository bucketRepository(EntityManager entityManager) {
		return new BucketRepositoryV1(entityManager);
	}
}
