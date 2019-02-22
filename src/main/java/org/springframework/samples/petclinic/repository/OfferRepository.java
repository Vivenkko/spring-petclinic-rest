package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Offer;

public interface OfferRepository {
	
	Collection<Offer> findAll() throws DataAccessException;
	
	Collection<Offer> findAllByValid() throws DataAccessException;
	
	void save(Offer offer) throws DataAccessException;

	void delete(Offer offer) throws DataAccessException;

	Offer findById(int id) throws DataAccessException;
}
