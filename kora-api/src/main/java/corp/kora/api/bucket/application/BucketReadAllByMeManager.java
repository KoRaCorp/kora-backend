package corp.kora.api.bucket.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import corp.kora.api.bucket.presentation.response.BucketReadAllByMeResponse;
import corp.kora.bucket.domain.repository.BucketReader;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BucketReadAllByMeManager {
	private final BucketReader bucketReader;

	@Transactional(readOnly = true)
	public List<BucketReadAllByMeResponse> read(Query query) {
		return bucketReader.readAllByMemberId(query.loginMemberId)
			.stream()
			.map(BucketReadAllByMeResponse::from)
			.toList();
	}

	public record Query(
		Long loginMemberId
	) {
	}
}
