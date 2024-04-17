package corp.kora.api.bucket.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import corp.kora.api.bucket.application.BucketChangeBucketNameProcessor;
import corp.kora.api.bucket.presentation.request.BucketChangeBucketNameRequest;
import corp.kora.api.bucket.presentation.response.BucketChangeBucketNameResponse;
import corp.kora.global.annotation.LoginMemberId;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BucketChangeBucketNameController {
	private final BucketChangeBucketNameProcessor bucketChangeBucketNameProcessor;

	@PostMapping("/api/buckets/{bucketId}/change-bucket-name")
	@ResponseStatus(HttpStatus.OK)
	public BucketChangeBucketNameResponse changeBucketName(
		@LoginMemberId Long loginMemberId,
		@PathVariable Long bucketId,
		@RequestBody BucketChangeBucketNameRequest request
	) {
		return bucketChangeBucketNameProcessor.execute(
			new BucketChangeBucketNameProcessor.Command(loginMemberId, bucketId, request.bucketName()));
	}
}
