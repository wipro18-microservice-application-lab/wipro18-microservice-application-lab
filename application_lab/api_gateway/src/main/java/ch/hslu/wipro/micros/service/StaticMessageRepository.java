package ch.hslu.wipro.micros.service;

/**
 * This class represents a Repository with static command information.
 */
public class StaticMessageRepository {

    private static MessageRepository messageRepository = null;

    private static void prepareRepository() {
        MessageDomain orderDomain = new MessageDomain("ch.hslu.wipro.micros.Order");
        orderDomain.addCommand("order.command.create");
        orderDomain.addCommand("order.command.getAll");
        orderDomain.addCommand("order.command.getAllByCustomerId");
        orderDomain.addCommand("order.command.updateStatus");

        MessageDomain articleDomain = new MessageDomain("ch.hslu.wipro.micros.article");
        articleDomain.addCommand("article.command.checkQuantity");
        articleDomain.addCommand("article.command.getAll");
        articleDomain.addCommand("article.command.getById");

        MessageDomain customerDomain = new MessageDomain("ch.hslu.wipro.micros.customer");
        customerDomain.addCommand("customer.command.create");
        customerDomain.addCommand("customer.command.getAll");
        customerDomain.addCommand("customer.command.getById");
        customerDomain.addCommand("customer.command.getReminderByCustomerId");

        MessageDomain userDomain = new MessageDomain("ch.hslu.wipro.micros.user");
        userDomain.addCommand("user.command.checkPermission");
        userDomain.addCommand("user.command.login");

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
