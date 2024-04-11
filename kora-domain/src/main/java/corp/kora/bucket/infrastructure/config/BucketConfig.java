package corp.kora.bucket.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import corp.kora.bucket.domain.repository.BucketRepository;
import corp.kora.bucket.infrastructure.persistence.BucketRepositoryV1;
import jakarta.persistence.EntityManager;

@Configuration
public class BucketConfig {

	@Bean
	public BucketRepository bucketRepository(EntityManager entityManager) {
		return new BucketRepositoryV1(entityManager);
	}
}
