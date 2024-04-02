package corp.kora.member.domain.exception;

import corp.kora.global.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
