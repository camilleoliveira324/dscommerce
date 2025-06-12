package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Payment;
import jakarta.persistence.Column;

import java.time.Instant;

public class PaymentDTO {

    private Long id;

    private Instant moment;

    public PaymentDTO() {
    }

    public PaymentDTO(Long id, Instant moment) {
        this.id = id;
        this.moment = moment;
    }

    public PaymentDTO(Payment p ) {
        id = p.getId();
        moment = p.getMoment();
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

}
