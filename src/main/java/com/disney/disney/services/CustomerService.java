package com.disney.disney.services;

import com.disney.disney.entities.Customer;
import com.disney.disney.enums.Role;
import com.disney.disney.repositories.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CustomerService implements UserDetailsService {

    private CustomerRepository customerrepository;
    private MailService mailService;
    
    @Autowired
    public CustomerService(CustomerRepository customerrepository, MailService mailService) {
        this.customerrepository = customerrepository;
        this.mailService = mailService;
    }
    
    @Transactional(rollbackOn = {Exception.class})
    public void register(Customer customer) throws Exception {
        validation(customer);
        customer.setRole(Role.USER);
        customer.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
        customerrepository.save(customer);
//        mailService.send("You signed up for Disney, welcome!", "Register for Disney", customer.getEmail());
    }
    
     private void validation(Customer customer) throws Exception {
        if (customer.getName().isEmpty() || customer.getName().equals(" ") || customer.getName() == null || customer.getName().length() < 2) {
            throw new Exception("El nombre ingresado es invalido");
        }
        if (customer.getLastName().isEmpty() || customer.getLastName().equals(" ") || customer.getLastName() == null || customer.getLastName().length() < 2) {
            throw new Exception("El apellido ingreado es invalido");
        }
        if (customer.getDni().isEmpty() || customer.getDni().equals(" ") || customer.getDni() == null) {
            throw new Exception("El DNI ingreado es invalido");
        }
        if (customer.getEmail().isEmpty() || customer.getEmail().contains(" ") || customer.getEmail() == null) {
            throw new Exception("El email ingresado es invalido");
        }
        if (customer.getPassword().isEmpty() || customer.getPassword().contains(" ") || customer.getPassword() == null) {
            throw new Exception("La contraseña ingresada es invalida");
        }
    }
     
     @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Customer customer = customerrepository.findByEmail(email);

        if (customer == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        List<GrantedAuthority> permissions = new ArrayList<>();
        GrantedAuthority rolePermissions = new SimpleGrantedAuthority("ROLE_" + customer.getRole().toString());
        permissions.add(rolePermissions);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("customersession", customer);

        return new User(customer.getEmail(), customer.getPassword(), permissions);
    }
}
