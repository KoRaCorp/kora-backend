package corp.kora.api.member.application;

import corp.kora.api.member.presentation.response.MemberReadByIdResponse;
import corp.kora.member.domain.exception.NotFoundMemberException;
import corp.kora.member.domain.model.MemberReadModel;
import corp.kora.member.domain.repository.MemberReader;
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
public class MemberReadByIdManager {
    private final MemberReader memberReader;

    @Transactional(readOnly = true)
    public MemberReadByIdResponse read(Query query) {
        MemberReadModel memberReadModel = memberReader.readById(query.memberId)
                .orElseThrow(() -> new NotFoundMemberException("Not Found Member"));

        return MemberReadByIdResponse.from(memberReadModel);
    }

    public record Query(
            Long memberId
    ) {

    }
}
