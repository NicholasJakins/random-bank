package com.bank.nl.service.auth;

import com.bank.nl.exception.BusinessException;
import com.bank.nl.model.ResponseCode;
import com.bank.nl.persistence.SessionEntity;
import com.bank.nl.persistence.SessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class SessionManagerImpl implements SessionManager {
    private static final int SESSION_EXPIRY_SECONDS = 3600;

    private final SessionRepository sessionRepository;

    @Override
    public UUID createNewSession() {
        var expiry = getUtcTimeNow().plusSeconds(SESSION_EXPIRY_SECONDS);
        var sessionToken = UUID.randomUUID();
        var session = SessionEntity.builder()
                .expiry(expiry)
                .id(sessionToken)
                .build();

        sessionRepository.save(session);
        return sessionToken;
    }

    @Override
    public boolean isValidSession(UUID token) {
        Optional<SessionEntity> session = sessionRepository.findById(token);

        if (session.isPresent()) {
            var timeNow = getUtcTimeNow();
            if (timeNow.isAfter(session.get().getExpiry())) {
                throw new BusinessException(ResponseCode.EXPIRED_TOKEN, "Login session has expired");
            }
            return true;
        }

        return false;
    }

    private OffsetDateTime getUtcTimeNow() {
        return Instant.now().atZone(ZoneId.of("UTC")).toOffsetDateTime().plusSeconds(SESSION_EXPIRY_SECONDS);
    }
}
