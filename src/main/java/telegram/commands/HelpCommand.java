package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends ServiceCommand{
    public HelpCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        sendAnswer(absSender, chat.getId(),
                "Привет! Я тайм-менеджер из Pomodoro.\n"+
                "Я помогу тебе сосредоточиться на решении важных задач.\n"+
                "Укажи, пожалуйста, количество необходимых для выполнения задач, а я буду сообщать тебе, когда работать, а когда сделать перерыв.\n"+
                "Начни командой /begin !");

    }
}
