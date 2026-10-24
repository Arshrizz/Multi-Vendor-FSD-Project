// package com.example.demo.Repository;

// import com.example.demo.Entity.DeliveryPartner;
// import org.springframework.data.jpa.repository.JpaRepository;
// import java.util.UUID;

// // <<<<<<< HEAD
// public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, UUID> {

// }
// =======
// public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, Integer> {
// }
// >>>>>>> d8b5ce97b1df123aa5d185f1e0b426de0fdbe884


package com.example.demo.Repository;

import com.example.demo.Entity.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, UUID> {
}
