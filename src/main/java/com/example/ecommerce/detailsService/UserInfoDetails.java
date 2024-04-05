package com.example.ecommerce.detailsService;

import com.example.ecommerce.entities.*;
import com.example.ecommerce.entities.dto.CommandByDateDTO;
import com.example.ecommerce.entities.enumerations.Gender;
import com.example.ecommerce.entities.enumerations.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Getter

public class UserInfoDetails implements UserDetails {

    Long id;
    String userName;
    String password;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    LocalDate creationDate;
    String adresseLivraison;
    Role role;
    Gender gender;
    CoordonneesBancaires coordonneesBancaires;
    List<GrantedAuthority> authorities;
    List<CommandByDateDTO> commandesUser;
    List<PanierDetails> panierUser;
    List<Produit> mesProduits;
    public UserInfoDetails(Client client,List<CommandByDateDTO> commandesUser,List<PanierDetails> panierUser){
        id=client.getId();
        userName= client.getUserName();
        password= client.getPassword();
        firstName= client.getFirstName();
        lastName = client.getLastName();
        email = client.getEmail();
        phoneNumber = client.getPhoneNumber();
        creationDate = client.getCreationDate();
        gender = client.getGender();
        role = Role.Client;
        adresseLivraison = client.getAdresseLivraison();
        authorities = Collections.singletonList(new SimpleGrantedAuthority(client.getClass().getSimpleName()));
        this.commandesUser=commandesUser;
        this.panierUser=panierUser;
    }
    public UserInfoDetails(Vendeur vendeur, List<CommandByDateDTO> commandesUser, List<PanierDetails> panierUser,List<Produit> mesProduits){
        id=vendeur.getId();
        userName= vendeur.getUserName();
        password= vendeur.getPassword();
        firstName= vendeur.getFirstName();
        lastName = vendeur.getLastName();
        email = vendeur.getEmail();
        phoneNumber = vendeur.getPhoneNumber();
        creationDate = vendeur.getCreationDate();
        gender = vendeur.getGender();
        role = Role.Vendeur;
        coordonneesBancaires = vendeur.getCoordonneesBancaires();
        authorities = Collections.singletonList(new SimpleGrantedAuthority(vendeur.getClass().getSimpleName()));
        this.commandesUser=commandesUser;
        this.panierUser=panierUser;
        this.mesProduits=mesProduits;
    }
    public UserInfoDetails(Admin admin){
        id=admin.getId();
        userName= admin.getUserName();
        password= admin.getPassword();
        firstName= admin.getFirstName();
        lastName = admin.getLastName();
        email = admin.getEmail();
        phoneNumber = admin.getPhoneNumber();
        creationDate = admin.getCreationDate();
        gender = admin.getGender();
        role = Role.Admin;
        authorities = Collections.singletonList(new SimpleGrantedAuthority(admin.getClass().getSimpleName()));
    }
    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
