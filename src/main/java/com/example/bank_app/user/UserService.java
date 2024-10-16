package com.example.bank_app.user;

import com.example.bank_app.account.AccountService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final Map<Integer, User> userMap;
    private int idCounter;

    private final Set<String> takenLogins;
    private final AccountService accountService;

    public UserService(AccountService accountService) {
        this.takenLogins = new HashSet<>();
        this.userMap = new HashMap<>();
        this.idCounter = 0;
        this.accountService = accountService;
    }

    public User createUser(String login) {
        if (takenLogins.contains(login)) {
            throw new IllegalArgumentException("User already exist %s".formatted(login));
        }
        takenLogins.add(login);
        idCounter++;
        var newUser = new User(idCounter, login, new ArrayList<>());
        var newAccount = accountService.createAccount(newUser);
        newUser.getAccountList().add(newAccount);
        userMap.put(idCounter, newUser);
        return newUser;
    }

    public Optional<User> findUserById(int id) {
        return Optional.ofNullable(userMap.get(id));
    }

    public List<User> getAllUsers() {
        return userMap.values().stream().toList();
    }
}
