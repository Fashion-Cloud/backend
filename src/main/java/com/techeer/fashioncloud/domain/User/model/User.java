package com.techeer.fashioncloud.domain.User.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length =100)
    private String profileUrl;

    @Column(length =100)
    private String address;

    @Column(length =50, unique = true)
    @NotNull
    private String nickname;

    @Column(length =50, unique = true)
    @NotNull
    private String email;

    @Column(length =100)
    @NotNull
    private String password;

    @Column
    @ColumnDefault("USER")
    @NotNull
    private ERole role;


}