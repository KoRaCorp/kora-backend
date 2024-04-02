package corp.kora.member.infrastructure.config;

import corp.kora.member.domain.repository.MemberCommandRepository;
import corp.kora.member.domain.repository.MemberQueryRepository;
import corp.kora.member.domain.service.MemberService;
import corp.kora.member.infrastructure.persistence.MemberCommandRepositoryV1;
import corp.kora.member.infrastructure.persistence.MemberQueryRepositoryV1;
import corp.kora.member.infrastructure.service.MemberServiceV1;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberConfig {
    @Bean
    public MemberCommandRepository memberCommandRepository(EntityManager entityManager) {
        return new MemberCommandRepositoryV1(entityManager);
    }

    @Bean
    public MemberQueryRepository memberQueryRepository(EntityManager entityManager) {
        return new MemberQueryRepositoryV1(entityManager);
    }

    @Bean
    public MemberService memberService(MemberQueryRepository memberQueryRepository, MemberCommandRepository memberCommandRepository) {
        return new MemberServiceV1(memberQueryRepository, memberCommandRepository);
    }
}
