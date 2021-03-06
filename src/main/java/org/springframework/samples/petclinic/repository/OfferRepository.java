package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer>{
	
	Collection<Offer> findByValid(boolean valid) throws DataAccessException;
	
	Collection<Offer> findByExpirationAfter(Date d);

	
}
