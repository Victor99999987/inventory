package ru.petproject.inventory.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "organizations")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private Long name;
}
