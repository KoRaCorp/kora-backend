package corp.kora.api.bucket.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import corp.kora.api.bucket.application.BucketDeleteProcessor;
import corp.kora.api.bucket.presentation.response.BucketDeleteResponse;
import corp.kora.global.annotation.LoginMemberId;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BucketDeleteController {
	private final BucketDeleteProcessor bucketDeleteProcessor;

	@PostMapping("/api/buckets/{bucketId}/delete")
	@ResponseStatus(HttpStatus.OK)
	public BucketDeleteResponse delete(
		@LoginMemberId Long loginMemberId,
		@PathVariable Long bucketId
	) {
		return bucketDeleteProcessor.execute(new BucketDeleteProcessor.Command(loginMemberId, bucketId));
	}
}
