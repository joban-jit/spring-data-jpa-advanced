package com.sdjpa.repositories;

import com.sdjpa.domain.OrderApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderApprovalRepository extends JpaRepository<OrderApproval, Long> {
}