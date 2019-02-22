package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.repository.OfferRepository;
import org.springframework.stereotype.Service;

@Service
public class OfferService {
	
	@Autowired
	private OfferRepository offerRepo;
	
	public Offer findOfferById(int id) {
		return offerRepo.findOne(id);
	}

	public Collection<Offer> findAllOffers() {
		return offerRepo.findAll();
	}
	
	public Collection<Offer> findAllOffersByValid() {
		//Collection<Offer> offers = offerRepo.findByValid(true);
		Collection<Offer> offers = offerRepo.findByExpirationAfter(new Date());
		for (Offer offer : offers) {
			// Comprueba a nivel de fecha que de verdad la oferta es válida
			// y lo setea si es necesario
			offer.setValidation();
		}	
		return offers;
	}
	
	public void addOffer(Offer offer) {
		offer.setValidation();
		offerRepo.save(offer);
	}
	
	public Offer updateOffer(Offer offer, int id) {
		if (offer.getId() == id) {
			offer.setValidation();
			offerRepo.save(offer);
			return offer;
		} else {
			return null;
		}
	}
	
	public Offer deleteOffer(int id) {
		Offer offer = offerRepo.findOne(id);
		if (offer != null) {
			offerRepo.delete(offer);
		}
		return offer;
	}
}
