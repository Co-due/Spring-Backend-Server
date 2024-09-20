package soma.edupi.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soma.edupi.user.dto.request.EmailRequest;
import soma.edupi.user.dto.request.MemberLoginRequest;
import soma.edupi.user.dto.request.SignupRequest;
import soma.edupi.user.dto.response.SignupResponse;
import soma.edupi.user.dto.response.TokenInfo;
import soma.edupi.user.service.EmailService;
import soma.edupi.user.service.MemberService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/member")
public class MemberController implements MemberSpecification {

    private final MemberService memberService;
    private final EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody MemberLoginRequest memberLoginRequest,
        HttpServletResponse response) {
        String token = memberService.login(memberLoginRequest);

        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/info")
    public ResponseEntity<TokenInfo> loginInfo(@CookieValue("token") String token) {
        TokenInfo tokenInfo = memberService.findMemberInfo(token);

        return ResponseEntity.ok(tokenInfo);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> createAccount(@Valid @RequestBody SignupRequest signupRequest)
        throws JsonProcessingException, MessagingException {
        memberService.signUp(signupRequest);
        emailService.sendEmail(signupRequest.getEmail());

        return ResponseEntity.ok().build();
    }

    // 이메일 인증
    @PostMapping("/activate")
    public ResponseEntity<Void> activateAccount(@RequestBody EmailRequest emailRequest) {
        emailService.activateAccount(emailRequest);
        log.info("Member: 이메일 인증 완료 {}", emailRequest.getEmail());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

}
