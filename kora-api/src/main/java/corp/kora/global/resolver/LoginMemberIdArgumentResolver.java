package corp.kora.global.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import corp.kora.auth.domain.provider.TokenProvider;
import corp.kora.global.annotation.LoginMemberId;
import corp.kora.global.exception.NoAccessAuthorizationException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LoginMemberIdArgumentResolver implements HandlerMethodArgumentResolver {
	private static final String AUTHORIZATION = "Authorization";
	private final TokenProvider tokenProvider;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginMemberId.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		String authorization = webRequest.getHeader(AUTHORIZATION);

		if (authorization == null) {
			throw new NoAccessAuthorizationException("접근 권한이 없습니다.");
		}

		return Long.parseLong(tokenProvider.extractPayload(authorization));
	}
}
