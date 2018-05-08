package com.arash.flm.gl.repository;

import com.arash.flm.gl.model.mail.db.GfOffer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by anton on 08.05.18.
 *
 */
@Repository
public interface GfOfferRepository extends MongoRepository<GfOffer, String> {

}
