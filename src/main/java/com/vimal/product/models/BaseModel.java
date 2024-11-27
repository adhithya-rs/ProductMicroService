package com.vimal.product.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date lastModifiedAt;
    private boolean isDeleted;


    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        this.createdAt = now;
        this.lastModifiedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedAt = new Date();
    }

}
