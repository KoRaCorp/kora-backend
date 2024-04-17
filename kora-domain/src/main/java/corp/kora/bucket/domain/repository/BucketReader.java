package corp.kora.bucket.domain.repository;

import java.util.List;

import corp.kora.bucket.domain.model.BucketReadModel;

public interface BucketReader {
	List<BucketReadModel> readAllByMemberId(Long memberId);
}
