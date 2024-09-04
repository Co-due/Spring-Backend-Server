package soma.haeya.edupi_user.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import soma.haeya.edupi_user.domain.Member;
import soma.haeya.edupi_user.dto.request.MemberLoginRequest;
import soma.haeya.edupi_user.dto.request.SignUpRequest;
import soma.haeya.edupi_user.dto.response.SignUpResponse;

@Component
@HttpExchange("/v1/member")
public interface MemberApiClient {

<<<<<<< HEAD
    @PostExchange("/findByEmailAndPassword")
    Member findMemberByEmailAndPassword(@RequestBody MemberLoginRequest memberLoginRequest);

    @PostExchange("/signup")
    ResponseEntity<Response> saveMember(@RequestBody SignupRequest signupRequest);
=======
    @PostExchange("/login")
    Member findMemberByEmailAndPassword(@RequestBody MemberLoginRequest memberLoginRequest);

    @PostExchange("/signup")
    ResponseEntity<SignUpResponse> saveMember(@RequestBody SignUpRequest signupRequest);
>>>>>>> 1e758fc ([#26]feat: 자동 들여쓰기 변경)
}
