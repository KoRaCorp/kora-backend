package corp.kora.api.member.application;

import corp.kora.api.member.presentation.response.MemberFindByIdResponse;
import corp.kora.member.domain.exception.NotFoundMemberException;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Todo
 * Query성 Manager는 Domain Model 객체를 그대로 반환하면 안된다.
 * MemberReader를 통해서 Member가 아닌 MemberReadModel을 반환하자.
 * MemberReadModel은 Member의 필요한 정보만 가지고 있는 객체이다.
 */
@Service
@RequiredArgsConstructor
public class MemberFindByIdManager {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberFindByIdResponse read(Query query) {
        Member member = memberRepository.findById(query.memberId).orElseThrow(() -> new NotFoundMemberException("Not Found Member"));
        return MemberFindByIdResponse.from(member);
    }

    public record Query(
            Long memberId
    ) {

    }
}
