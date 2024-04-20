package corp.kora.api.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import corp.kora.api.member.presentation.response.MemberReadMeResponse;
import corp.kora.member.domain.exception.NotFoundMemberException;
import corp.kora.member.domain.model.MemberReadModel;
import corp.kora.member.domain.repository.MemberReader;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberReadMeManager {
	private final MemberReader memberReader;

	@Transactional(readOnly = true)
	public MemberReadMeResponse read(Query query) {
		MemberReadModel memberReadModel = memberReader.readById(query.memberId)
			.orElseThrow(() -> new NotFoundMemberException("Not Found Member"));

		return MemberReadMeResponse.from(memberReadModel);
	}

	public record Query(
		Long memberId
	) {

	}
}
