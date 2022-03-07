public interface TelegramBotUtils {
    String BOT_USERNAME = "t.me/weather_2401bot";
    String BOT_TOKEN = "5092181741:AAGuFVCzymf5UDzClRmekNb7x9OuKQoUpZo";
    String MAIN_MENU = "MAIN_MENU";
    String CHOOSE_COUNTRY = "CHOOSE_COUNTRY";
    String CHOOSE_DISTRICT = "CHOOSE_DISTRICT";
    String WEATHER_INFO = "WEATHER_INFO";
    String ERROR = "ERROR";

    default boolean isStart(String text){
        return text.equals("/start");
    }

    default boolean isMyCard(String text){
        return text.equals("Kartalarim");
    }
}
