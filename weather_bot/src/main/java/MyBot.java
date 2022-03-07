import enums.BotState;
import lombok.SneakyThrows;
import model.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import service.TelegramBotService;
import service.UserService;

import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


public class MyBot extends TelegramLongPollingBot implements TelegramBotUtils {


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        User user = UserService.getAndCheck(update);
        BotState state = user.getState();

        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                if (text.equals("/start")) {
                    state = BotState.MAIN_MENU;
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId().toString());
                    sendMessage.setText("Salom \uD83D\uDC4B");

                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if (text.length()==1){
                    state = BotState.CHOOSE_COUNTRY;
                }else state = BotState.ERROR;

            }
        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            switch (data) {
                case MAIN_MENU -> state = BotState.MAIN_MENU;
                case CHOOSE_COUNTRY -> state = BotState.CHOOSE_COUNTRY;
                case CHOOSE_DISTRICT -> state = BotState.CHOOSE_DISTRICT;
                case WEATHER_INFO -> state = BotState.WEATHER_INFO;
                default -> {
                    if ((state == BotState.CHOOSE_DISTRICT)) {
                        state = BotState.WEATHER_INFO;
                    } else {
                        state = BotState.CHOOSE_DISTRICT;
                    }
                }
            }
        }
        switch (state) {
            case MAIN_MENU -> execute(TelegramBotService.mainMenu(update));
            case CHOOSE_COUNTRY -> execute(TelegramBotService.chooseCountry(update));
            case CHOOSE_DISTRICT -> execute(TelegramBotService.chooseDistrict(update));
            case WEATHER_INFO -> execute(TelegramBotService.weatherInfo(update, update.getCallbackQuery().getData()));
            case ERROR -> execute(TelegramBotService.error(update));
        }
        UserService.saveStateAndLan(update, state);

    }


    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
