package com.api.parking_control.controllers;

import com.api.parking_control.dtos.ParkingSpotDto;
import com.api.parking_control.models.ParkingSpotModel;
import com.api.parking_control.services.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        if (parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLICT: License Plate Car is already in use!");
        }
        if (parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLICT: Parking Spot is already in use!");
        }
        if (parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLICT: Parking Spot is registered for this apartment/block!");
        }


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
    @GetMapping
    public ResponseEntity<Page<ParkingSpotModel>> getAllParkingsSpot(@PageableDefault(page=0, size=10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
    }

    @GetMapping("/{id}")
   public ResponseEntity<Object> getOneParkingsSpot(@PathVariable(value="id")UUID id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
   };
    @DeleteMapping("/{id}")
   public ResponseEntity<Object> deleteParkingsSpot(@PathVariable(value="id")UUID id) {
       Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
       if (!parkingSpotModelOptional.isPresent()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
   }
       parkingSpotService.delete(parkingSpotModelOptional.get());
       return ResponseEntity.status(HttpStatus.OK).body("Parking Spot delete successfull!");
   }

   @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingsSpot(@PathVariable(value="id")UUID id, @RequestBody @Valid ParkingSpotDto parkingSpotDto) {
       Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
       if (!parkingSpotModelOptional.isPresent()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
       }

//       var parkingSpotModel = parkingSpotModelOptional.get();
//       parkingSpotModel.setParkingSpotNumber(parkingSpotDto.getParkingSpotNumber());
//       parkingSpotModel.setLicensePlateCar(parkingSpotDto.getLicensePlateCar());
//       parkingSpotModel.setModelCar(parkingSpotModel.getModelCar());
//       parkingSpotModel.setBrandCar(parkingSpotModel.getBrandCar());
//       parkingSpotModel.setColorCar(parkingSpotModel.getColorCar());
//       parkingSpotModel.setResponsableName(parkingSpotModel.getResponsableName());
//       parkingSpotModel.setApartment(parkingSpotModel.getApartment());
//       parkingSpotModel.setBlock(parkingSpotModel.getBlock());

        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());

       return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
   }
}