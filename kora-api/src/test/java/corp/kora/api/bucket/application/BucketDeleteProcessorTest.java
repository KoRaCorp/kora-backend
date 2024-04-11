package corp.kora.api.bucket.application;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;

import corp.kora.api.bucket.presentation.response.BucketDeleteResponse;
import corp.kora.bucket.domain.exception.NotFoundBucketException;
import corp.kora.bucket.domain.model.Bucket;
import corp.kora.bucket.domain.repository.BucketRepository;
import corp.kora.member.domain.model.Member;
import corp.kora.support.IntegrationTestSupport;

class BucketDeleteProcessorTest extends IntegrationTestSupport {

	@Autowired
	private BucketCreateProcessor bucketCreateProcessor;

	@Autowired
	private BucketDeleteProcessor bucketDeleteProcessor;

	@Autowired
	private BucketRepository bucketQueryRepository;

	@DisplayName("BucketDeleteProcessorIntegrationTest Scenario")
	@TestFactory
	Collection<DynamicTest> execute() {

		Member loginMember = generateMember();
		AtomicLong bucketId = new AtomicLong(0L);

		return List.of(
			DynamicTest.dynamicTest("bucket을 하나 생성한다.", () -> {
				// given
				BucketCreateProcessor.Command command = new BucketCreateProcessor.Command(loginMember.getId(),
					"의류");

				// when
				Bucket bucket = bucketQueryRepository.findById(
						bucketCreateProcessor.execute(command).bucketId())
					.orElseThrow();

				bucketId.set(bucket.getId());

				// then
				assertThat(bucket).isNotNull();
				assertThat(bucket.getBucketName()).isEqualTo("의류");
				assertThat(bucket.getMemberId()).isEqualTo(loginMember.getId());
			}),
			DynamicTest.dynamicTest("생성한 bucket을 삭제한다.", () -> {
				// given
				BucketDeleteProcessor.Command command = new BucketDeleteProcessor.Command(loginMember.getId(),
					bucketId.get());

				// when
				BucketDeleteResponse response = bucketDeleteProcessor.execute(command);
				assertThat(response.bucketId()).isEqualTo(bucketId.get());

				// then
				assertThat(bucketQueryRepository.findById(bucketId.get())).isEmpty();

			}),
			DynamicTest.dynamicTest("이미 삭제된 버킷을 삭제할 경우 예외를 발생시킨다.", () -> {
				// given
				BucketDeleteProcessor.Command command = new BucketDeleteProcessor.Command(loginMember.getId(),
					bucketId.get());

				// when & then
				assertThatThrownBy(() -> bucketDeleteProcessor.execute(command))
					.isInstanceOf(NotFoundBucketException.class);

			})

		);
	}
}
