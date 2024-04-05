package corp.kora.member.infrastructure.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberCommandRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberCommandRepositoryV1 implements MemberCommandRepository {
	private final EntityManager entityManager;

	@Override
	@Transactional
	public Member save(Member member) {
		if (isNew(member)) {
			entityManager.persist(member);
			return member;
		}
		return entityManager.merge(member);

	}

	@Override
	@Transactional
	public List<Member> saveAll(List<Member> members) {
		if (members.isEmpty()) {
			return List.of();
		}

		List<Member> result = new ArrayList<>(members.size());
		for (Member member : members) {
			result.add(save(member));
		}

		return result;
	}

	@Override
	@Transactional
	public void deleteAllInBatch() {
		entityManager.createQuery("delete from Member").executeUpdate();
	}

	private boolean isNew(Member member) {
		return member.getId() == null;
	}
}
