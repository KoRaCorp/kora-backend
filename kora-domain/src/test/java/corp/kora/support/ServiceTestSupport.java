package corp.kora.support;

import org.junit.jupiter.api.AfterEach;

import corp.kora.member.domain.repository.MemberCommandRepository;
import corp.kora.member.domain.repository.MemberQueryRepository;
import corp.kora.member.infrastructure.persistence.FakeMemberRepository;

public abstract class ServiceTestSupport {

	protected MemberCommandRepository memberCommandRepository;
	protected MemberQueryRepository memberQueryRepository;

	public ServiceTestSupport() {
		// Member
		FakeMemberRepository fakeMemberRepository = new FakeMemberRepository();
		memberCommandRepository = fakeMemberRepository;
		memberQueryRepository = fakeMemberRepository;

	}

	@AfterEach
	void tearDown() {
		memberCommandRepository.deleteAllInBatch();
	}
}
