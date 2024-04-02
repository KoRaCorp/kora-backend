package corp.kora.member.infrastructure.persistence;

import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberCommandRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class MemberCommandRepositoryV1 implements MemberCommandRepository {
    private final EntityManager entityManager;
    @Override
    @Transactional
    public Member save(Member member) {
        return null;
    }

    @Override
    @Transactional
    public List<Member> saveAll(List<Member> members) {
        return null;
    }

    @Override
    @Transactional
    public void deleteAllInBatch() {

    }
}
