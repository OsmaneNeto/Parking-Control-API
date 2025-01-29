package com.api.parking_control.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_PARKING_SPOT")
public class ParkingSpotModel implements Serializable {
    //ele vai ter o id e numero da vaga
    //conversões de objetos java para bites para serem salvos em bancos de dados
    @Serial
    private static final long serialVersionUID= 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//gera os id's automaticamente
    private UUID id;
    @Column(nullable = false, unique = true, length = 10)
    private  String parkingSpotNumber; //número da vaga
    @Column(nullable = false, unique = true, length = 7)
    private String licensePlateCar; //placa do carro
    @Column(nullable = false, length = 70)
    private String brandCar; //Marca
    @Column(nullable = false, length = 70)
    private String modelCar; //modelo
    @Column(nullable = false, length = 70)
    private String colorCar;//cor do carro
    @Column(nullable = false)
    private LocalDateTime registrationDate; //Registro da data
    @Column(nullable = false, length = 130)
    private String responsableName; //Responsável
    @Column(nullable = false, length = 30)
    private String apartment; //Apartamento
    @Column(nullable = false, length = 30)
    private String block; //Bloco ou torres do apartamento

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public void setParkingSpotNumber(String parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
    }

    public String getLicensePlateCar() {
        return licensePlateCar;
    }

    public void setLicensePlateCar(String licensePlateCar) {
        this.licensePlateCar = licensePlateCar;
    }

    public String getBrandCar() {
        return brandCar;
    }

    public void setBrandCar(String brandCar) {
        this.brandCar = brandCar;
    }

    public String getModelCar() {
        return modelCar;
    }

    public void setModelCar(String modelCar) {
        this.modelCar = modelCar;
    }

    public String getColorCar() {
        return colorCar;
    }

    public void setColorCar(String colorCar) {
        this.colorCar = colorCar;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getResponsableName() {
        return responsableName;
    }

    public void setResponsableName(String responsableName) {
        this.responsableName = responsableName;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
}
