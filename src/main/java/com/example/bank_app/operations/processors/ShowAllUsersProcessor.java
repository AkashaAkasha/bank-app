package com.example.bank_app.operations.processors;

import com.example.bank_app.operations.ConsoleOperationType;
import com.example.bank_app.operations.OperationCommandProcessor;
import com.example.bank_app.user.User;
import com.example.bank_app.user.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowAllUsersProcessor implements OperationCommandProcessor {
    private final UserService userService;
    public ShowAllUsersProcessor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processOperation() {
        List<User> users = userService.getAllUsers();
        System.out.println("List of users");
        users.forEach(System.out::println);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }
}
