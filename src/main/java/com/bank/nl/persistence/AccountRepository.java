package com.bank.nl.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends CrudRepository<AccountEntity, UUID> {

    List<AccountEntity> findByUserName(String userName);
}
