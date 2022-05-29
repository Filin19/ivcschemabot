package com.railway.ivc.ivcschemabot.services;

import com.railway.ivc.ivcschemabot.entity.User;
import com.railway.ivc.ivcschemabot.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;

/**
 * Class service to handle User.
 * <p>
 * @author Viktor Zaitsev.
 */
@Service
public class UserService {

    /**
     * Variable to save UsersRepository object.
     */
    private final UsersRepository usersRepository;

    /**
     * Constructor of User Service class.
     * <p>
     * @param usersRepository UsersRepository object.
     */
    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Method to check is user exist in database.
     * <p>
     * @param contact Contact object.
     * @return boolean is user exist.
     */
    public boolean checkUser(Contact contact) {
        String phoneNumber = contact.getPhoneNumber();
        phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
        User user = usersRepository.findByUserNumber(phoneNumber);

        return user != null && user.getUserName().equals(contact.getFirstName());
    }
}
