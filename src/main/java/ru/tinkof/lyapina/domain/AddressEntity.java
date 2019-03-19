package ru.tinkof.lyapina.domain;

import javax.persistence.*;

@Entity
@Table(name = "address", schema = "public")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "postcode")
    private String postCode;

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house")
    private int house;

    @Column(name = "flat")
    private int flat;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL,
             fetch = FetchType.LAZY, optional = false)
    private PersonsEntity personsEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public PersonsEntity getPersonsEntity() {
        return personsEntity;
    }

    public void setPersonsEntity(PersonsEntity personsEntity) {
        this.personsEntity = personsEntity;
    }
}
