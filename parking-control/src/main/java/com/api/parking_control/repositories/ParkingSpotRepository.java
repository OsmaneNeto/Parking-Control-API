package com.api.parking_control.repositories;

import com.api.parking_control.models.ParkingSpotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
//JpaRepository<ParkingSpotModel(Pacote), UUID(Identificador)>
@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID>{
}
