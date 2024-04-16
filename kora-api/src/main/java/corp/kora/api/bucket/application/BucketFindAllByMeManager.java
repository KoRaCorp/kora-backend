package corp.kora.api.bucket.application;

import corp.kora.api.bucket.presentation.response.BucketFindAllByMeResponse;
import corp.kora.bucket.domain.repository.BucketRepository;
import corp.kora.member.domain.exception.NotFoundMemberException;
import corp.kora.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BucketFindAllByMeManager {
    private final BucketRepository bucketRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<BucketFindAllByMeResponse> read(Query query) {
        memberRepository.findById(query.loginMemberId)
                .orElseThrow(() -> new NotFoundMemberException("존재하지 않은 회원입니다."));

        return bucketRepository.findAllByMemberId(query.loginMemberId)
                .stream()
                .map(BucketFindAllByMeResponse::from)
                .toList();
    }

    public record Query(
            Long loginMemberId
    ) {
    }
}
