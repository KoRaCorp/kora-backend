package corp.kora.api.bucket.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import corp.kora.api.bucket.application.BucketReadAllByMeManager;
import corp.kora.api.bucket.presentation.response.BucketReadAllByMeResponse;
import corp.kora.global.annotation.LoginMemberId;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BucketReadAllByMeController {
	private final BucketReadAllByMeManager bucketFindAllByMeManager;

	@GetMapping("/api/buckets")
	@ResponseStatus(HttpStatus.OK)
	public List<BucketReadAllByMeResponse> readAllByMe(
		@LoginMemberId Long loginMemberId
	) {
		return bucketFindAllByMeManager.read(new BucketReadAllByMeManager.Query(loginMemberId));
	}
}
