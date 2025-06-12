package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.User;

public class ClientDTO {

    private Long id;
    private String name;

    public ClientDTO() {
    }

    public ClientDTO(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public ClientDTO(User u) {
        name = u.getName();
        id = u.getId();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
