package com.mdd.ela.service.impl;

import com.mdd.ela.dto.model.Account;
import com.mdd.ela.dto.request.AuthenticationRequest;
import com.mdd.ela.dto.request.IntrospectRequest;
import com.mdd.ela.dto.response.APIResponse;
import com.mdd.ela.dto.response.auth.AuthenticationRes;
import com.mdd.ela.dto.response.auth.IntrospectRes;
import com.mdd.ela.exception.ElaRuntimeException;
import com.mdd.ela.repository.AccountRepository;
import com.mdd.ela.service.AuthenticateService;
import com.mdd.ela.util.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author dungmd
 * @created 1/3/2025 7:14 下午
 * @project e-learning-app
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AuthenticateServiceImpl implements AuthenticateService {
    AccountRepository repository;

    @NonFinal
    @Value("${jwt.signer-key}")
    protected String SIGNER_KEY;

    @Override
    public APIResponse authenticate(AuthenticationRequest req) throws JOSEException {
        try {
            var account = repository.findByEmail(req.getEmail());
            if (account == null) {
                throw new ElaRuntimeException(ErrorCode.EMAIL_NOT_FOUND);
            }
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            boolean authenticated = passwordEncoder.matches(req.getPassword(), account.getPassword());
            if (!authenticated)
                throw new ElaRuntimeException(ErrorCode.WRONG_PASSWORD);
            var token = generateToken(account);
            var authenticationRes = AuthenticationRes.builder()
                    .isAuthenticated(true)
                    .token(token)
                    .build();
            return APIResponse.success(authenticationRes);
        } catch (ElaRuntimeException e) {
            throw e;
        } catch (Exception e){
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public APIResponse authenticate(IntrospectRequest req) throws JOSEException, ParseException {
        var token = req.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        var introspectRes = IntrospectRes.builder().valid(verified && expTime.after(new Date())).build();
        return APIResponse.success(introspectRes);
    }

    @Override
    public String generateToken(Account account) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getEmail())
                .issuer("mdd")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("id", account.getId())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
        return jwsObject.serialize();
    }


}
