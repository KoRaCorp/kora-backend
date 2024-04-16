package corp.kora.support;

import org.junit.jupiter.api.AfterEach;

import net.bytebuddy.utility.RandomString;

import corp.kora.member.domain.generater.NicknameGenerator;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberRepository;
import corp.kora.member.infrastructure.generator.NicknameGeneratorV1;
import corp.kora.member.infrastructure.persistence.FakeMemberRepository;

public abstract class ServiceTestSupport {

	protected MemberRepository memberRepository;
	protected NicknameGenerator nicknameGenerator;

	public ServiceTestSupport() {
		// Member
		memberRepository = new FakeMemberRepository();
		nicknameGenerator = new NicknameGeneratorV1(memberRepository);

	}

	@AfterEach
	void tearDown() {
		memberRepository.deleteAllInBatch();
	}

	protected Member generateMember(String email) {
		return memberRepository
			.save(
				Member.builder()
					.email(email)
					.authKey(RandomString.make())
					.nickname(nicknameGenerator.generateNickname(email))
					.build()
			);
	}
}
