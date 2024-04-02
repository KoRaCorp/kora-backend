package corp.kora.api.member.application;

import corp.kora.api.member.presentation.response.MemberFindByIdResponse;
import corp.kora.member.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberFindByIdQueryManager {
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public MemberFindByIdResponse read(Query query) {
        return MemberFindByIdResponse.from(memberService.find(query.memberId));
    }

    public record Query(
        Long memberId
    ) {

    }
}
