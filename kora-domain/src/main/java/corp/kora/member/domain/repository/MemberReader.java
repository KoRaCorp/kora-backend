package corp.kora.member.domain.repository;

import corp.kora.member.domain.model.MemberReadModel;

import java.util.Optional;

public interface MemberReader {
    Optional<MemberReadModel> readById(Long id);
}
