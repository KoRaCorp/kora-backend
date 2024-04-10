package corp.kora.bucket.domain.model;

import corp.kora.global.entity.BaseEntity;
import corp.kora.member.domain.model.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bucket extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bucket_id")
	private Long id;

	private String bucketName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private Bucket(String bucketName, Member member) {
		this.bucketName = bucketName;
		this.member = member;
	}

	public static Bucket from(String bucketName, Member member) {
		return new Bucket(bucketName, member);
	}

}
