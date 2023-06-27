package ru.kata.spring.boot_security.demo.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsServerImpl;

@Component
public class UserValidator implements Validator {
    private final UserDetailsServerImpl userDetailsServer;

    @Autowired
    public UserValidator(UserDetailsServerImpl userDetailsServer) {
        this.userDetailsServer = userDetailsServer;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        try {
            userDetailsServer.loadUserByUsername(user.getUsNa());
        } catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue("usNa", "", "человек с таким именем существует");
    }
}

