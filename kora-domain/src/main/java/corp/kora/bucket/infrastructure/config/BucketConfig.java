package corp.kora.bucket.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import corp.kora.bucket.domain.repository.BucketCommandRepository;
import corp.kora.bucket.domain.repository.BucketQueryRepository;
import corp.kora.bucket.infrastructure.persistence.BucketCommandRepositoryV1;
import corp.kora.bucket.infrastructure.persistence.BucketQueryRepositoryV1;
import jakarta.persistence.EntityManager;

@Configuration
public class BucketConfig {
	@Bean
	public BucketCommandRepository bucketCommandRepository(EntityManager entityManager) {
		return new BucketCommandRepositoryV1(entityManager);
	}

	@Bean
	public BucketQueryRepository bucketQueryRepository(EntityManager entityManager) {
		return new BucketQueryRepositoryV1(entityManager);
	}
}
