package com.example.picpay.repository;

import com.example.picpay.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TranferRepository extends JpaRepository<Transfer, UUID> {
}
