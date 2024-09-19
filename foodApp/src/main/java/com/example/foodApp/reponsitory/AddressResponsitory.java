package com.example.foodApp.reponsitory;

import com.example.foodApp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressResponsitory extends JpaRepository<Address,Long> {
    Address findAddressById(Long addressId);
    List<Address> findAllAddressByUserId(Long userId);

    @Query("SELECT a FROM Address a WHERE LOWER(a.city) = LOWER(:city) AND LOWER(a.streetAddress) = LOWER(:streetAddress) AND LOWER(a.state) = LOWER(:state) AND LOWER(a.postalCode) = LOWER(:postalCode)")
    Optional<Address> findAddressByAll(@Param("streetAddress") String streetAddress,
                                       @Param("city") String city,
                                       @Param("state") String state,
                                       @Param("postalCode") String postalCode);

}
