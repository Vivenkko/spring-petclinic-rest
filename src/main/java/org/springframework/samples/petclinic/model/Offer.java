package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "offers")
public class Offer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "offer_id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "discount_percentage")
    private double discount;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "expiration_date")
    private Date expiration;
    
    @Column(name = "valid_offer")
    private boolean valid;

	public int getId() {
		return id;
	}

	/* 
	 * Mejor no incluir
	 * 
	 * public void setId(int id) {
		this.id = id;
	}*/

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
    
	/**
	 * Método que comprueba la fecha de la oferta y otorga
	 * el valor correspondiente al atributo valid
	 * 
	 * @param offer
	 */
	public void setValidation() {
		if (this.getExpiration().after(new Date())) {
			this.setValid(true);
		} else {
			this.setValid(false);
		}
    }
    
}
