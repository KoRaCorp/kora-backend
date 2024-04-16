package corp.kora.member.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import corp.kora.member.domain.generater.NicknameGenerator;
import corp.kora.member.domain.repository.MemberReader;
import corp.kora.member.domain.repository.MemberRepository;
import corp.kora.member.infrastructure.generator.NicknameGeneratorV1;
import corp.kora.member.infrastructure.persistence.MemberReaderV1;
import corp.kora.member.infrastructure.persistence.MemberRepositoryV1;
import jakarta.persistence.EntityManager;

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
	public NicknameGenerator nicknameGenerator(MemberRepository memberRepository) {
		return new NicknameGeneratorV1(memberRepository);
	}

}
