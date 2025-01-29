package com.api.parking_control.controllers;

import com.api.parking_control.dtos.ParkingSpotDto;
import com.api.parking_control.models.ParkingSpotModel;
import com.api.parking_control.services.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {
    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    //A Uri foi definida a nivel de classe

    //Quando o usuario fazer uma requisição POST ele será acionado para responder
    // a solicitação com status e corpo da resposta
    //RequestBody vai receber os dados como via json
    //Valid serve para realizar as validações, se não colocar não funcionará
//    @PostMapping
//    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){
//        var parkingSpotModel = new ParkingSpotModel();
//        BeanUtils.copyProperties(parkingSpotDto,parkingSpotModel);
//        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
//
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
//    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        try {
            var parkingSpotModel = new ParkingSpotModel();
            BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
            parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

            return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
        } catch (Exception e) {
            e.printStackTrace();  // Log do erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor: " + e.getMessage());
        }
    }

}
