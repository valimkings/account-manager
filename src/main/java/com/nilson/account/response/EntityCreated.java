package com.nilson.account.response;

public class EntityCreated {

    Long id;

    public EntityCreated(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
