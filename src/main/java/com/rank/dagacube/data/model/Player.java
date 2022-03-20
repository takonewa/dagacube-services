package com.rank.dagacube.data.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString(of="id")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @OneToOne(mappedBy = "player")
    private Account account;
}
