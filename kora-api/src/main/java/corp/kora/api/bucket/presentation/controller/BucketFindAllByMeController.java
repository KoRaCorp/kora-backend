package corp.kora.api.bucket.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import corp.kora.api.bucket.application.BucketFindAllByMeManager;
import corp.kora.api.bucket.presentation.response.BucketFindAllByMeResponse;
import corp.kora.global.annotation.LoginMemberId;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BucketFindAllByMeController {
	private final BucketFindAllByMeManager bucketFindAllByMeManager;

	@GetMapping("/api/buckets")
	@ResponseStatus(HttpStatus.OK)
	public List<BucketFindAllByMeResponse> findAllByMe(
		@LoginMemberId Long loginMemberId
	) {
		return bucketFindAllByMeManager.read(new BucketFindAllByMeManager.Query(loginMemberId));
	}
}
