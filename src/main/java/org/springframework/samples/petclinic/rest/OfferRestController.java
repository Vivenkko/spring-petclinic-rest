/*
 * Copyright 2016-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.rest;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.service.OfferService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/offers")
public class OfferRestController {

	@Autowired
	private OfferService offerService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Offer>> getAllOffers(){
		Collection<Offer> offers = new ArrayList<Offer>();
		offers.addAll(this.offerService.findAllOffers());
		if (offers.isEmpty()){
			return new ResponseEntity<Collection<Offer>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Collection<Offer>>(offers, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/{OfferId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Offer> getOffer(@PathVariable("OfferId") int offerId){
		Offer offer = this.offerService.findOfferById(offerId);
		if(offer == null){
			return new ResponseEntity<Offer>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Offer>(offer, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Offer> addOffer(@RequestBody @Valid Offer offer, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (offer == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Offer>(headers, HttpStatus.BAD_REQUEST);
		} else {
			this.offerService.addOffer(offer);
			//headers.setLocation(ucBuilder.path("/api/Offers/{id}").buildAndExpand(offer.getId()).toUri());
			return new ResponseEntity<Offer>(offer, headers, HttpStatus.CREATED);
		}
	}

	@RequestMapping(value = "/{OfferId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Offer> updateOffer(@PathVariable("OfferId") int offerId, @RequestBody @Valid Offer offer, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (offer == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Offer>(headers, HttpStatus.BAD_REQUEST);
		} else {
			if(offerService.updateOffer(offer, offerId) == null) {
				return new ResponseEntity<Offer>(headers, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<Offer>(offer, headers, HttpStatus.OK);
			}
		}		
	}

	@RequestMapping(value = "/{OfferId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteOffer(@PathVariable("OfferId") int offerId){
		if(this.offerService.deleteOffer(offerId) == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/valid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Offer>> getAllValidOffers(){
		Collection<Offer> offers = new ArrayList<Offer>();
		offers.addAll(this.offerService.findAllOffersByValid());
		if (offers.isEmpty()){
			return new ResponseEntity<Collection<Offer>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Collection<Offer>>(offers, HttpStatus.OK);
		}
	}

}
