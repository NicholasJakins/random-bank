package com.bank.nl.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SessionRepository extends CrudRepository<SessionEntity, UUID> {
}
