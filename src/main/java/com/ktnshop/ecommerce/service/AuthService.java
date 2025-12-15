package com.ktnshop.ecommerce.service;

import com.ktnshop.ecommerce.model.User;
import com.ktnshop.ecommerce.model.Customer;
import com.ktnshop.ecommerce.repository.UserRepository;
import com.ktnshop.ecommerce.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Tên đăng nhập hoặc mật khẩu sai"));
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Tên đăng nhập hoặc mật khẩu sai");
        }
        
        if (!user.getIsActive()) {
            throw new RuntimeException("Tài khoản của bạn đã bị khóa");
        }
        
        return user;
    }

    public User register(String username, String email, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại");
        }
        
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email đã được sử dụng");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(User.UserRole.CUSTOMER);
        user.setIsActive(true);

        return userRepository.save(user);
    }

    public Customer getCustomerByUserId(Long userId) {
        return customerRepository.findByUser_Id(userId).orElse(null);
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
