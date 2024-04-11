package corp.kora.api.bucket.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import corp.kora.api.bucket.presentation.response.BucketCreateResponse;
import corp.kora.bucket.domain.exception.DuplicateBucketException;
import corp.kora.bucket.domain.model.Bucket;
import corp.kora.bucket.domain.repository.BucketRepository;
import corp.kora.member.domain.exception.NotFoundMemberException;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BucketCreateProcessor {
	private final MemberQueryRepository memberQueryRepository;
	private final BucketRepository bucketRepository;

	@Transactional
	public BucketCreateResponse execute(BucketCreateProcessor.Command command) {
		Member loginMember = memberQueryRepository.findById(command.loginMemberId)
			.orElseThrow(() -> new NotFoundMemberException("존재하지 않은 회원입니다."));

		if (isDuplicatedBucket(command, loginMember)) {
			throw new DuplicateBucketException("이미 존재하는 버킷 이름입니다.");
		}

		Bucket bucketToCreate = Bucket.
			from(command.bucketName, loginMember.getId());
		return BucketCreateResponse.from(bucketRepository.save(bucketToCreate).getId());
	}

	private boolean isDuplicatedBucket(Command command, Member loginMember) {
		return bucketRepository.findByMemberIdAndBucketName(loginMember.getId(), command.bucketName).isPresent();
	}

	public record Command(
		Long loginMemberId,
		String bucketName
	) {

	}
}
