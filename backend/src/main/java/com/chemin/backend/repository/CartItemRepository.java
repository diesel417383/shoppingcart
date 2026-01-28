package com.chemin.backend.repository;

import com.chemin.backend.entity.CartItem;
import com.chemin.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface
CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<List<CartItem>> findByUserId(Long userId);

    Optional<CartItem> findByUserAndProductId(User user, Long productId);
}
