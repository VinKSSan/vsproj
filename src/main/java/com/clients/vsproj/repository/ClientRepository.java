package com.clients.vsproj.repository;

import com.clients.vsproj.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
