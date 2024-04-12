package corp.kora.member.infrastructure.config;

import corp.kora.member.domain.provider.NicknameProvider;
import corp.kora.member.domain.repository.MemberReader;
import corp.kora.member.domain.repository.MemberRepository;
import corp.kora.member.infrastructure.persistence.MemberReaderV1;
import corp.kora.member.infrastructure.persistence.MemberRepositoryV1;
import corp.kora.member.infrastructure.provider.NicknameProviderV1;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberConfig {

    @Bean
    public MemberRepository memberRepository(EntityManager entityManager) {
        return new MemberRepositoryV1(entityManager);
    }

    @Bean
    public MemberReader memberReader(EntityManager entityManager) {
        return new MemberReaderV1(entityManager);
    }

    @Bean
    public NicknameProvider nicknameProvider() {
        return new NicknameProviderV1();
    }


}
