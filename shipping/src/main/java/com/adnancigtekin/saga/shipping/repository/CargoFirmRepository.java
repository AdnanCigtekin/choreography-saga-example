package com.adnancigtekin.saga.shipping.repository;

import com.adnancigtekin.saga.shipping.model.CargoFirm;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoFirmRepository extends MongoRepository<CargoFirm, String> {
}
