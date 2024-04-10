package corp.kora.api.bucket.application;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;

import corp.kora.bucket.domain.exception.DuplicateBucketException;
import corp.kora.bucket.domain.model.Bucket;
import corp.kora.bucket.domain.repository.BucketQueryRepository;
import corp.kora.member.domain.model.Member;
import corp.kora.support.IntegrationTestSupport;

class BucketCreateProcessorIntegrationTest extends IntegrationTestSupport {

	@Autowired
	private BucketCreateProcessor bucketCreateProcessor;

	@Autowired
	private BucketQueryRepository bucketQueryRepository;

	@DisplayName("BucketCreateProcessorIntegrationTest Scenario")
	@TestFactory
	Collection<DynamicTest> execute() {

		Member loginMember = generateMember();
		return List.of(
			DynamicTest.dynamicTest("bucket을 하나 생성한다.", () -> {
				// given
				BucketCreateProcessor.Command command = new BucketCreateProcessor.Command(loginMember.getId(),
					"의류");

				// when
				Bucket bucket = bucketQueryRepository.findWithMemberById(
						bucketCreateProcessor.execute(command).bucketId())
					.orElseThrow();

				// then
				assertThat(bucket).isNotNull();
				assertThat(bucket.getBucketName()).isEqualTo("의류");
				assertThat(bucket.getMember().getId()).isEqualTo(loginMember.getId());
			}),
			DynamicTest.dynamicTest("동일한 유저가 동일한 이름으로 Bucket을 생성할 경우 예외를 발생시킨다.", () -> {
				// given
				String duplicatedBucketName = "의류";

				BucketCreateProcessor.Command command = new BucketCreateProcessor.Command(loginMember.getId(),
					duplicatedBucketName);

				// when & then
				assertThatThrownBy(() -> bucketCreateProcessor.execute(command))
					.isInstanceOf(DuplicateBucketException.class);
			})

		);
	}

}
