package com.example.sb115.web;

import com.example.sb115.dto.ResponseDto;
import com.example.sb115.dto.oauth.KakaoOauthToken;
import com.example.sb115.dto.oauth.KakaoProfile;
import com.example.sb115.dto.user.UserReqDto;
import com.example.sb115.dto.user.UserRespDto;
import com.example.sb115.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/api/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserReqDto.JoinReqDto joinReqDto, BindingResult bindingResult) {
        UserRespDto.JoinRespDto joinRespDto = userService.join(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", joinRespDto), HttpStatus.CREATED);
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","");
        params.add("redirect_uri","http://localhost:8080/auth/kakao/callback");
        params.add("code",code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoOauthToken kakaoOauthToken = null;
        try{
            kakaoOauthToken = objectMapper.readValue((String) response.getBody(), KakaoOauthToken.class);
        }catch (JsonMappingException e){
            e.printStackTrace();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization","Bearer "+kakaoOauthToken.getAccess_token());
        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class);

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try{
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        }catch (JsonMappingException e){
            e.printStackTrace();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        return "회원가입완료";
    }
}