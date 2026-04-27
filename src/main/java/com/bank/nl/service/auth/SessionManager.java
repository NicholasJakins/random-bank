package com.bank.nl.service.auth;

import java.util.UUID;

public interface SessionManager {
    UUID createNewSession();
    boolean isValidSession(UUID token);
}
