package com.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.storage.model.enums.Gender;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "directors")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "director"
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Warehouse> warehouses = new HashSet<>();

}
