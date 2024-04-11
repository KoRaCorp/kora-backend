package corp.kora.support;

import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.bytebuddy.utility.RandomString;

import corp.kora.bucket.domain.repository.BucketRepository;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberCommandRepository;

@SpringBootTest
public abstract class IntegrationTestSupport {
	@Autowired
	protected MemberCommandRepository memberCommandRepository;

	@Autowired
	protected BucketRepository bucketRepository;

	@BeforeEach
	void setUp() {
		setUpTimeZone();
		setUpRepository();
	}

	private void setUpRepository() {
		memberCommandRepository.deleteAllInBatch();
		bucketRepository.deleteAllInBatch();
	}

	private void setUpTimeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	protected Member generateMember() {
		return memberCommandRepository.save(
			Member.builder()
				.authKey(RandomString.make())
				.email(RandomString.make() + "@gmail.com")
				.nickname(RandomString.make())
				.build()
		);
	}
}

