// package com.example.demo.Repository;

// import com.example.demo.Entity.DeliveryTracking;
// import org.springframework.data.jpa.repository.JpaRepository;
// import java.util.UUID;


// public interface DeliveryTrackingRepository extends JpaRepository<DeliveryTracking, UUID> {

// }

// >>>>>>> d8b5ce97b1df123aa5d185f1e0b426de0fdbe884

package com.example.demo.Repository;

import com.example.demo.Entity.DeliveryTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryTrackingRepository extends JpaRepository<DeliveryTracking, UUID> {
    List<DeliveryTracking> findByDeliveryPartnerPartnerId(UUID partnerId);
}
