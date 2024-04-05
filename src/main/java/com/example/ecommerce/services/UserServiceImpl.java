package com.example.ecommerce.services;
import com.example.ecommerce.Repositories.ProduitRepository;
import com.example.ecommerce.controllers.PanierDetailsController;
import com.example.ecommerce.detailsService.UserInfoDetails;
import com.example.ecommerce.Repositories.UserRepository;
import com.example.ecommerce.entities.*;
import com.example.ecommerce.entities.dto.CommandByDateDTO;
import com.example.ecommerce.entities.dto.ProduitDTO;
import com.example.ecommerce.exceptions.DataNotFoundException;
import com.example.ecommerce.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IPanierDetailsService panierDetailsService;
    @Autowired
    PanierDetailsController panierDetailsController;
    @Autowired
    ProduitRepository produitRepository;
    @Override
    public List<Utilisateur> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Utilisateur createUser(Utilisateur user) {
        if(userRepository.findByUserName(user.getUserName()).isPresent()){
            throw new DataNotFoundException("Username already exist to another user");

        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Utilisateur createAdmin(Utilisateur admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return userRepository.save(admin);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Utilisateur findById(Long id) {
        Utilisateur user = userRepository.findById(id).orElse(null);
        if(user==null){
            throw new DataNotFoundException("User not found");
        }else{
            return user;
        }
    }
        List<CommandByDateDTO> getCommandByUser(Long idUser) {
            Map<LocalDateTime, List<PanierDetails>> groupingByDate = panierDetailsService.getGroupingByDateCommandeByUser(idUser);
            List<CommandByDateDTO> commandByDateDTOList = null;
            if (!groupingByDate.isEmpty()) {
                commandByDateDTOList = new ArrayList<>();
                for (Map.Entry<LocalDateTime, List<PanierDetails>> entry : groupingByDate.entrySet()) {LocalDateTime dateCommande = entry.getKey();
                 List<PanierDetails> panierDetailsList = entry.getValue();
                    CommandByDateDTO commandByDateDTO = new CommandByDateDTO(dateCommande, panierDetailsList);
                    commandByDateDTOList.add(commandByDateDTO);
        }
    }
            return commandByDateDTOList;
}
    @Override
    public UserDetails loadUserByUsername(String username) throws DataNotFoundException {
        Optional<Utilisateur> userInfo = userRepository.findByUserName(username);
        Utilisateur utilisateur = userInfo.orElseThrow(() -> new DataNotFoundException("User not found: " + username));
        List<CommandByDateDTO> commandListUser = this.getCommandByUser(utilisateur.getId());
        List<PanierDetails> panierListUser = panierDetailsService.getAllByUserNonCommander(utilisateur.getId());

        if (utilisateur instanceof Client) {
            return new UserInfoDetails((Client) utilisateur,commandListUser,panierListUser);
        }
        else if (utilisateur instanceof Vendeur) {
            List<Produit> myProducts = produitRepository.findByVendeurId(utilisateur.getId());
            return new UserInfoDetails((Vendeur) utilisateur,commandListUser,panierListUser,myProducts);
        }
        else if (utilisateur instanceof Admin) {
            return new UserInfoDetails((Admin) utilisateur);
        }
        else{
            throw new DataNotFoundException("Invalid user type for: " + username);
        }
    }
}
