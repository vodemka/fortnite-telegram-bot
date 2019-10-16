package by.vodemka;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;


public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirdRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("\uD83C\uDF40 - Random"));

        keyboardSecondRow.add(new KeyboardButton("\uD83C\uDF08 - Outfit"));
        keyboardSecondRow.add(new KeyboardButton("\uD83C\uDF03 - Pickaxe"));
        keyboardSecondRow.add(new KeyboardButton("\uD83C\uDF06 - Emote"));

        keyboardThirdRow.add(new KeyboardButton("❓ - Help"));
        keyboardThirdRow.add(new KeyboardButton("\uD83D\uDD27 - Settings"));
        keyboardThirdRow.add(new KeyboardButton("\uD83D\uDCAC - Creator"));

        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);
        keyboardRowList.add(keyboardThirdRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        SendPhoto sendPhoto = new SendPhoto();
        Model model = new Model();

        sendMessage.setChatId(message.getChatId().toString());
        sendPhoto.setChatId(message.getChatId().toString());

        if (message != null && message.hasText()) {
            setButtons(sendMessage);
            switch (message.getText()) {
                case "Help":
                case "❓ - Help":
                case "/help":
                case "/help@FortniteStoreBot":
                    try {
                        execute(sendMessage.setText("How can I help You?"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;

                case "Settings":
                case "\uD83D\uDD27 - Settings":
                case "/settings":
                case "/settings@FortniteStoreBot":
                    try {
                        execute(sendMessage.setText("What are we going to customize?"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;

                case "Creator":
                case "\uD83D\uDCAC - Creator":
                case "/creator":
                case "/creator@FortniteStoreBot":
                    try {
                        execute(sendMessage.setText("Creator - @vodemka"));
                        execute(sendPhoto.setPhoto("https://avatars1.githubusercontent.com/u/38399172?s=460&v=4"));

                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;

                case "Outfit":
                case "\uD83C\uDF08 - Outfit":
                case "/outfit":
                case "/outfit@FortniteStoreBot":
                    try {
                        execute(sendPhoto.setCaption((Store.getStore(model, "Outfit").toString())).setPhoto(model.getBackground()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;

                case "Pickaxe":
                case "\uD83C\uDF03 - Pickaxe":
                case "/pickaxe":
                case "/pickaxe@FortniteStoreBot":
                    try {
                        execute(sendPhoto.setCaption((Store.getStore(model, "Pickaxe").toString())).setPhoto(model.getBackground()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;

                case "Emote":
                case "\uD83C\uDF06 - Emote":
                case "/emote":
                case "/emote@FortniteStoreBot":
                    try {
                        execute(sendPhoto.setCaption((Store.getStore(model, "Emote").toString())).setPhoto(model.getBackground()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    try {
                        Random rn = new Random();
                        int n = rn.nextInt(3 - 1 + 1) + 1;
                        String type = "";

                        switch (n) {
                            case 1:
                                type = "Outfit";
                                break;
                            case 2:
                                type = "Pickaxe";
                                break;
                            case 3:
                                type = "Emote";
                                break;
                        }
                        execute(sendPhoto.setCaption((Store.getStore(model, type).toString())).setPhoto(model.getBackground()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    public static String getToken(String key) {
        FileInputStream fis;
        Properties property = new Properties();
        String token = null;
        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);
            token = property.getProperty(key);
        } catch (IOException e) {
            System.err.println("Error: file not found!");
        }
        return token;
    }

    @Override
    public String getBotUsername() {
        return "FortniteStoreBot";
    }

    @Override
    public String getBotToken() {
        return getToken("apiBotKey");
    }
}

