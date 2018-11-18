package ch.hslu.wipro.micros.service;

public class StaticMessageRepository {

    private static MessageRepository messageRepository = null;

    private static void prepareRepository() {
        MessageDomain orderDomain = new MessageDomain();
        orderDomain.addCommand("order.command.create");
        MessageDomain articleDomain = new MessageDomain();
        articleDomain.addCommand("article.command.get");

        messageRepository.addDomain("order", orderDomain);
        messageRepository.addDomain("article", articleDomain);
    }

    public static MessageRepository getMessageRepository() {
        if (messageRepository == null) {
            messageRepository = new MessageRepository();
            prepareRepository();
        }
        return messageRepository;
    }
}
