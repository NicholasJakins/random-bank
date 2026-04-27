package com.bank.nl.service.auth;

import com.bank.nl.persistence.SessionEntity;
import com.bank.nl.persistence.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SessionManagerImpl implements SessionManager {
    private static final int SESSION_EXPIRY_SECONDS = 3600;

    private final SessionRepository sessionRepository;

    @Override
    public UUID createNewSession() {
        var expiry = OffsetDateTime.now().plusSeconds(SESSION_EXPIRY_SECONDS);
        var sessionToken = UUID.randomUUID();
        var session = SessionEntity.builder()
                .expiry(expiry)
                .id(sessionToken)
                .build();

        sessionRepository.save(session);
        return sessionToken;
    }
}
