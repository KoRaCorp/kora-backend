package corp.kora.support;

import corp.kora.member.domain.repository.MemberRepository;
import corp.kora.member.infrastructure.persistence.FakeMemberRepository;
import org.junit.jupiter.api.AfterEach;

public abstract class ServiceTestSupport {

    protected MemberRepository memberRepository;

    public ServiceTestSupport() {
        // Member
        memberRepository = new FakeMemberRepository();

    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }
}
