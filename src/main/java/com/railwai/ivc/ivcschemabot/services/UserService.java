package com.railwai.ivc.ivcschemabot.services;

import com.railwai.ivc.ivcschemabot.entity.User;
import com.railwai.ivc.ivcschemabot.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean checkUser(Contact contact) {
        String phoneNumber = contact.getPhoneNumber();
        phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
        User user = usersRepository.findByUserNumber(phoneNumber);
        if(user != null && user.getUserName().equals(contact.getFirstName())) {
            return true;
        }

        return false;
    }
}
