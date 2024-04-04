package corp.kora.member.infrastructure.persistence;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberCommandRepository;
import corp.kora.member.domain.repository.MemberQueryRepository;

public class FakeMemberRepository implements MemberCommandRepository, MemberQueryRepository {

	private final Map<Long, Member> store = new ConcurrentHashMap<>();

	private final AtomicLong idGenerator = new AtomicLong();

	@Override
	public Member save(Member member) {
		if (!isNew(member)) {
			System.out.println(" is not new ==");
			return member;
		}
		
		long memberId = idGenerator.incrementAndGet();
		setId(memberId, member);
		store.put(memberId, member);
		return member;
	}

	@Override
	public List<Member> saveAll(List<Member> members) {
		return null;
	}

	@Override
	public void deleteAllInBatch() {
		store.clear();

	}

	@Override
	public Optional<Member> find(Long memberId) {
		return Optional.empty();
	}

	@Override
	public Optional<Member> find(String authKey) {
		return store.values()
			.stream()
			.filter(member -> member.getAuthKey().equals(authKey))
			.findFirst();
	}

	private void setId(long memberId, Member member) {
		try {
			Method method = Class.forName("org.springframework.test.util.ReflectionTestUtils")
				.getMethod("setField", Object.class, String.class, Object.class);

			method.invoke(null, member, "id", memberId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isNew(Member member) {
		return member.getId() == null;
	}
}
