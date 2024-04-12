package corp.kora.api.bucket.application;

import corp.kora.api.bucket.presentation.response.BucketChangeBucketNameResponse;
import corp.kora.bucket.domain.exception.NotFoundBucketException;
import corp.kora.bucket.domain.model.Bucket;
import corp.kora.bucket.domain.repository.BucketRepository;
import corp.kora.member.domain.exception.NotFoundMemberException;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BucketChangeBucketNameProcessor {
    private final BucketRepository bucketRepository;
    private final MemberRepository memberQueryRepository;

    @Transactional
    public BucketChangeBucketNameResponse execute(Command command) {

        Member loginMember = memberQueryRepository.findById(command.loginMemberId())
                .orElseThrow(() -> new NotFoundMemberException("존재하지 않는 회원입니다."));

        Bucket bucket = bucketRepository.findById(command.bucketId)
                .orElseThrow(() -> new NotFoundBucketException("존재하지 않는 버킷입니다."));

        bucket.validateIsOwner(loginMember.getId());
        bucket.changeBucketName(command.bucketNameToChange);
        return BucketChangeBucketNameResponse.from(bucket.getId());
    }

    public record Command(
            Long loginMemberId,
            Long bucketId,
            String bucketNameToChange
    ) {
    }
}
