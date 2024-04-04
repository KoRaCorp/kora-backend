package corp.kora.member.domain.model;

import corp.kora.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String authKey;

	private String email;

	private String nickname;

	private String profileMessage;

	private String profileImageFilePath;

	private String refreshToken;

	public String profileImageUrl() {
		return null;
	}

	@Builder
	private Member(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
		this.profileMessage = NONE;
		this.refreshToken = NONE;
		this.profileImageFilePath = NONE;
		this.authKey = NONE;
	}
}
