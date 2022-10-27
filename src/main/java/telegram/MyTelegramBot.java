package telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegram.commands.BeginCommand;
import telegram.commands.HelpCommand;

import java.util.concurrent.TimeUnit;

public class MyTelegramBot extends TelegramLongPollingCommandBot {
    public static final String BOT_TOKEN = "5443301300:AAEkAKTHJsXjtt6mqKCeVBxYEr9i_v2bWIo";
    public static final String BOT_USERNAME = "PomodoroBot_mybot";
    public static final String CHAT_ID = "1587382634";


    public static final int WORK = 25;
    public static final int BREAK = 5;


    public MyTelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApi.registerBot(this);
            register(new BeginCommand("begin", "Начнем"));
            register(new HelpCommand("help", "Помощь"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }


    @Override
    public void processNonCommandUpdate(Update update) {
        if(update.hasMessage()&& isNumeric(update.getMessage().getText())&& BeginCommand.isStart){
           //BeginCommand.isStart = false;
            String message;
            int count = Integer.parseInt(update.getMessage().getText());

            for (int i =1; i <= count; i++){
                message ="Задача №"+ i + " началась";
                sendMessage(message);
                try {
                    TimeUnit.MINUTES.sleep(WORK);
                } catch (InterruptedException e){
                    throw new RuntimeException();
                }
                message = "Задача №"+i + " завершилась";
                sendMessage(message);
                if (count -i !=0){
                    message ="Время отдыхать!";
                    sendMessage(message);
                    try {
                        TimeUnit.MINUTES.sleep(BREAK);
                    } catch (InterruptedException e){
                        throw new RuntimeException();
                    }
                    message = "Время работать!";
                    sendMessage(message);
                }
                BeginCommand.isStart = false;
            }

        } else {
            String message = "Я не понимаю";
            sendMessage(message);
        }


    }

    private void sendMessage(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(CHAT_ID);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}

