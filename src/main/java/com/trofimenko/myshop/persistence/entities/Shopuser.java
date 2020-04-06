package com.trofimenko.myshop.persistence.entities;

import com.trofimenko.myshop.persistence.entities.utils.PersistableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "shopuser")
@EqualsAndHashCode(callSuper = true)
public class Shopuser extends PersistableEntity {

    private String phone;

    private String password;

    private String firstName;

    private String lastName;

    private String email;


    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "shopuser_role",
            joinColumns = @JoinColumn(name = "shopuser"),
            inverseJoinColumns = @JoinColumn(name = "role"))
    private Collection<Role> roles;



}
