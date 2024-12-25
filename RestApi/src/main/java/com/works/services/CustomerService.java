package com.works.services;

import com.works.entities.Customer;
import com.works.entities.Role;
import com.works.entities.dto.CustomerDto;
import com.works.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService implements UserDetailsService {

    final ModelMapper modelMapper;
    final CustomerRepository customerRepository;
    final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findByUsernameEqualsIgnoreCase(username);
        if (optionalCustomer.isPresent()) {
            Customer c = optionalCustomer.get();
            return new User(
                    c.getUsername(),
                    c.getPassword(),
                    c.getEnable(),
                    true,
                    true,
                    true,
                    parseRole(c.getRoles())
            );
        }
        throw new UsernameNotFoundException("User Not Found!");
    }

    private Collection<? extends GrantedAuthority> parseRole(List<Role> roles) {
        List<GrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority(role.getName()));
        }
        return list;
    }

    public Customer register(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        Optional<Customer> optionalCustomer = customerRepository.findByUsernameEqualsIgnoreCase(customer.getUsername());
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        }else {
            customer.setPassword( passwordEncoder.encode( customer.getPassword() ) );
            customer = customerRepository.save(customer);
            return customer;
        }
    }


}
