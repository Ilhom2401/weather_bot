package service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import enums.BotState;
import model.Countries;
import model.Country;
import model.Region;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class TelegramBotService {
    public static SendMessage error(Update update){
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("❗️ ❗️ ❗️");
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        list.add(backButton(message.getChatId().toString()));
        sendMessage.setReplyMarkup(new InlineKeyboardMarkup(list));
        return sendMessage;
    }

    public static List<InlineKeyboardButton> backButton(String chatId) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("⬅️ Back");
        inlineKeyboardButton.setCallbackData("MAIN_MENU");
        List<InlineKeyboardButton> inlineKeyboardButtons1 = new ArrayList<>();
        inlineKeyboardButtons1.add(inlineKeyboardButton);
        return inlineKeyboardButtons1;
    }

    public static InlineKeyboardMarkup getMarkUp(String s, String chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        CountryService countryService = new CountryService();
        int i = 1;
        for (Country country : countryService.getCountries()) {
            if (s.toUpperCase().startsWith(country.getName().substring(0, 1))) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(country.getName());
                inlineKeyboardButton.setCallbackData(country.getName());
                inlineKeyboardButtons.add(inlineKeyboardButton);
                if ((i++) % 2 == 0) {
                    list.add(inlineKeyboardButtons);
                    inlineKeyboardButtons = new ArrayList<>();
                }
            }

        }

        list.add(inlineKeyboardButtons);
        list.add(backButton(chatId));
        return new InlineKeyboardMarkup(list);
    }

    public static InlineKeyboardMarkup getDistrictMarkUp(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        CountryService countryService = new CountryService();
        int i = 1;
        for (Country country : countryService.getCountries()) {
            if (country.getName().equals(update.getCallbackQuery().getData()))
                for (String city : country.getCities()) {
                    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                    inlineKeyboardButton.setText(city);
                    inlineKeyboardButton.setCallbackData(city);
                    inlineKeyboardButtons.add(inlineKeyboardButton);
                    if ((i++) % 2 == 0) {
                        list.add(inlineKeyboardButtons);
                        inlineKeyboardButtons = new ArrayList<>();
                    }
                }
        }
        list.add(inlineKeyboardButtons);
        list.add(backButton(message.getChatId().toString()));
        return new InlineKeyboardMarkup(list);
    }


    public static SendMessage chooseCountry(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Davlatni tanlang \uD83D\uDC47\uD83D\uDC47");
        sendMessage.setReplyMarkup(getMarkUp(message.getText(), message.getChatId().toString()));
        return sendMessage;
    }

    public static SendMessage chooseDistrict(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Viloyatni tanlang \uD83D\uDC47\uD83D\uDC47");
        sendMessage.setReplyMarkup(getDistrictMarkUp(update));
        return sendMessage;
    }

    public static SendMessage mainMenu(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("\uD83C\uDF0E Qidirmoqchi bo'lgan davlatning bosh harfini kiriting...");
        return sendMessage;
    }

    public static SendMessage weatherInfo(Update update, String data){
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        WeatherService weatherService = new WeatherService();
        Region regionWeatherData = weatherService.getRegionWeatherData(data);
        sendMessage.setText(
                "⛅️WATHER FORECAST\n"+
                "\n"+
                "\uD83C\uDFD9 Viloyat: " + regionWeatherData.getName() + "\n" +
                "\uD83C\uDFD6 Iqlim: "+regionWeatherData.getWeathers().get(0).getMain()+"\n"+
                "\uD83C\uDF2A Shamol tezligi: "+regionWeatherData.getWind().getSpeed()+" m/s\n"+
                "\uD83C\uDF1E Max temperatura: "+((int)(regionWeatherData.getMain().getTemp_max()-273))+" °C\n"+
                "☀️ Min temperatura: "+((int)(regionWeatherData.getMain().getTemp_min()-273))+" °C\n"+
                "\uD83C\uDF0E O'rtacha harorat: "+((int)(regionWeatherData.getMain().getTemp()-273))+" °C\n");
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        list.add(backButton(message.getChatId().toString()));
        sendMessage.setReplyMarkup(new InlineKeyboardMarkup(list));
        return sendMessage;
    }


}
