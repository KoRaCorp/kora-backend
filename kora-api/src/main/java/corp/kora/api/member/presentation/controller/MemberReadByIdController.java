package corp.kora.api.member.presentation.controller;

import corp.kora.api.member.application.MemberReadByIdManager;
import corp.kora.api.member.presentation.response.MemberReadByIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberReadByIdController {
    private final MemberReadByIdManager memberFindByIdQueryManager;

    @GetMapping("/api/members/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public MemberReadByIdResponse readById(
            @PathVariable Long memberId
    ) {
        return memberFindByIdQueryManager.read(new MemberReadByIdManager.Query(memberId));
    }
}
