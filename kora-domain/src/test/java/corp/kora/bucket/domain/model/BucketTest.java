package corp.kora.bucket.domain.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import corp.kora.global.exception.NoAccessAuthorizationException;
import corp.kora.member.domain.model.Member;
import corp.kora.support.ServiceTestSupport;

class BucketTest extends ServiceTestSupport {

	@DisplayName("Bucket에 대한 소유권을 가진 사용자가 버킷 이름을 변경한다.")
	@Test
	void changeBucketName() {
		// given
		Member owner = generateMember("uncle.ra@gmail.com");
		Bucket savedBucket = bucketRepository.save(Bucket.from("의류", owner.getId()));

		// when
		savedBucket.changeBucketName("가전", owner.getId());

		// then
		assertThat(savedBucket.getBucketName()).isEqualTo("가전");
		assertThat(savedBucket.getMemberId()).isEqualTo(owner.getId());
	}

	@DisplayName("Bucket에 대한 소유권을 가진 사용자가 아니라면  버킷 이름을 변경할 수 없다.")
	@Test
	void changeBucketName2() {
		// given
		Member notOwner = generateMember("fake@gmail.com");
		Member owner = generateMember("uncle.ra@gmail.com");
		Bucket savedBucket = bucketRepository.save(Bucket.from("의류", owner.getId()));

		// when & then
		assertThatThrownBy(() -> savedBucket.changeBucketName("가전", notOwner.getId()))
			.isInstanceOf(NoAccessAuthorizationException.class)
			.hasMessage("버킷 소유자만 접근할 수 있습니다.");

	}

}
