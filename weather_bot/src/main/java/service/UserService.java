package service;

import enums.BotState;
import model.User;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    public static List<User> userList = new ArrayList<>();

    public static User getAndCheck(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        String chatId = message.getChatId().toString();
        User user = null;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getChatId().equals(chatId)) {
                user = userList.get(i);
                break;
            }
        }

        if (user == null) {
            var from = message.getFrom();
            user = new User(from.getFirstName(), from.getUserName(), chatId, BotState.MAIN_MENU, 0, 15);
            userList.add(user);
        }

        return user;
    }

    public static void saveStateAndLan(Update update, BotState state) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        String chatId = message.getChatId().toString();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getChatId().equals(chatId)) {
                User user = new User("", "", chatId, state, 0, 15);
                userList.set(i, user);
            }
        }
    }

    public static BotState getState(String chatId) {
        for (User user : userList) {
            if (user.getChatId().equals(chatId)) return user.getState();
        }
        return null;
    }

}
