package corp.kora.api.bucket.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import corp.kora.api.bucket.presentation.response.BucketDeleteResponse;
import corp.kora.bucket.domain.exception.NotFoundBucketException;
import corp.kora.bucket.domain.model.Bucket;
import corp.kora.bucket.domain.repository.BucketRepository;
import corp.kora.member.domain.exception.NotFoundMemberException;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BucketDeleteProcessor {
	private final BucketRepository bucketRepository;
	private final MemberQueryRepository memberQueryRepository;

	@Transactional
	public BucketDeleteResponse execute(Command command) {

		Member loginMember = memberQueryRepository.findById(command.loginMemberId())
			.orElseThrow(() -> new NotFoundMemberException("존재하지 않는 회원입니다."));

		Bucket bucket = bucketRepository.findById(command.bucketId())
			.orElseThrow(() -> new NotFoundBucketException("존재하지 않는 버킷입니다."));

		bucket.validateIsOwner(loginMember.getId());

		bucketRepository.delete(bucket);
		return BucketDeleteResponse.from(bucket.getId());
	}

	public record Command(
		Long loginMemberId,
		Long bucketId
	) {
	}
}
