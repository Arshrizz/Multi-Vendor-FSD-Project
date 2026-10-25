package com.example.demo.Repository;

import com.example.demo.Entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    List<CartItemEntity> findByCart_CartId(Long cartId);

    Optional<CartItemEntity> findByCart_CartIdAndProduct_ProductId(Long cartId, Long productId);

    void deleteByCart_CartId(Long cartId);
}
