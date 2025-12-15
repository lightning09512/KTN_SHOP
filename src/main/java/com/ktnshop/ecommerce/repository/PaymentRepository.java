package com.ktnshop.ecommerce.repository;

import com.ktnshop.ecommerce.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTransactionId(String transactionId);
    List<Payment> findByOrder_Id(Long orderId);
    List<Payment> findByStatus(Payment.PaymentStatus status);
}
