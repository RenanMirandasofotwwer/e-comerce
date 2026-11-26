package com.tads.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tads.ecommerce.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
