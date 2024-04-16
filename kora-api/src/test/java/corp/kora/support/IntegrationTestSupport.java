package corp.kora.support;

import corp.kora.bucket.domain.repository.BucketRepository;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.TimeZone;

@SpringBootTest
public abstract class IntegrationTestSupport {
    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected BucketRepository bucketRepository;

    @BeforeEach
    void setUp() {
        setUpTimeZone();
        setUpRepository();
    }

    private void setUpRepository() {
        memberRepository.deleteAllInBatch();
        bucketRepository.deleteAllInBatch();
    }

    private void setUpTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    protected Member generateMember() {
        return memberRepository.save(
                Member.builder()
                        .authKey(RandomString.make())
                        .email(RandomString.make() + "@gmail.com")
                        .nickname(RandomString.make())
                        .build()
        );
    }
}

