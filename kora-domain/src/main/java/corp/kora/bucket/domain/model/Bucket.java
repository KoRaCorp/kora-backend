package corp.kora.bucket.domain.model;

import corp.kora.global.entity.BaseModel;
import corp.kora.global.exception.NoAccessAuthorizationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bucket extends BaseModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bucket_id")
	private Long id;

	private String bucketName;

	private Long memberId;

	private Bucket(String bucketName, Long memberId) {
		this.bucketName = bucketName;
		this.memberId = memberId;
	}

	public static Bucket from(String bucketName, Long memberId) {
		return new Bucket(bucketName, memberId);
	}

	public void changeBucketName(String bucketNameToChange, Long loginMemberId) {
		validateIsOwner(loginMemberId);
		this.bucketName = bucketNameToChange;
	}

	public boolean isNew() {
		return id == null;
	}

	private boolean isOwner(Long memberId) {
		return this.memberId.equals(memberId);
	}

	public void validateIsOwner(Long memberId) {
		if (!isOwner(memberId)) {
			throw new NoAccessAuthorizationException("버킷 소유자만 접근할 수 있습니다.");
		}
	}
}
