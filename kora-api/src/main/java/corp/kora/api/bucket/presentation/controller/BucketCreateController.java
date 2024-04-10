package corp.kora.api.bucket.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import corp.kora.api.bucket.application.BucketCreateProcessor;
import corp.kora.api.bucket.presentation.request.BucketCreateRequest;
import corp.kora.api.bucket.presentation.response.BucketCreateResponse;
import corp.kora.global.annotation.LoginMemberId;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BucketCreateController {
	private final BucketCreateProcessor bucketCreateProcessor;

	@PostMapping("/api/buckets/create")
	@ResponseStatus(HttpStatus.CREATED)
	public BucketCreateResponse create(
		@LoginMemberId Long loginMemberId,
		@RequestBody BucketCreateRequest request
	) {
		return bucketCreateProcessor.execute(request.toCommand(loginMemberId));
	}
}
