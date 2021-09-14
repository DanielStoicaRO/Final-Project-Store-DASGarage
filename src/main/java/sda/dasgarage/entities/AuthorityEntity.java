package sda.dasgarage.entities;

import javax.persistence.*;

@Entity
@Table(name="authorities")
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorityId;

    private String username;
    private String authority;
}
