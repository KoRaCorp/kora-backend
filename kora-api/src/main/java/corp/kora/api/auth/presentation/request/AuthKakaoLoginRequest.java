package corp.kora.api.auth.presentation.request;

import jakarta.validation.constraints.NotBlank;

// code=imLQu4c8KfwkLPh_JiQg7-KuK_GaTf3MQA_vH6hU7LzgLdYdplMmGi0kDFcKPXWcAAABjq60EXGYFzyUYZmfhQ
public record AuthKakaoLoginRequest(
	@NotBlank(message = "code는 필수 값입니다.")
	String code
) {
}
