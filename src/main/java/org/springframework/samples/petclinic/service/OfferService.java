package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.samples.petclinic.repository.OfferRepository;
import org.springframework.samples.petclinic.model.Offer;

@Service
public class OfferService {
	
	@Autowired
	private OfferRepository offerRepo;
	
	public Offer findOfferById(int id) {
		return offerRepo.findById(id);
	}

	public Collection<Offer> findAllOffers() {
		return offerRepo.findAll();
	}
	
	public Collection<Offer> findAllOffersByValid() {
		Collection<Offer> offers = offerRepo.findAllByValid();
		for (Offer offer : offers) {
			// Comprueba a nivel de fecha que de verdad la oferta es válida
			// y lo setea si es necesario
			offer.setValidation();
		}	
		return offers;
	}
	
	public void addOffer(Offer offer) {
		offerRepo.save(offer);
	}
	
	public void updateOffer(Offer offer, int id) {
		if (offer.getId() == id) {
			offerRepo.save(offer);
		}
	}
	
	public void deleteOffer(int id) {
		Offer offer = offerRepo.findById(id);
		offerRepo.delete(offer);
	}
}
