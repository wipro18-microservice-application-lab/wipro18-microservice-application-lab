package ch.hslu.wipro.micros.customermanagement;

import ch.hslu.wipro.micros.customermanagement.rabbitmq.ChannelBuilder;
import ch.hslu.wipro.micros.customermanagement.rabbitmq.RabbitMqConnector;
import ch.hslu.wipro.micros.customermanagement.rabbitmq.config.RabbitMqConfig;
import ch.hslu.wipro.micros.customermanagement.rabbitmq.config.RabbitMqConsts;
import ch.hslu.wipro.micros.customermanagement.rabbitmq.manager.CommandManager;
import ch.hslu.wipro.micros.customermanagement.repository.CustomerRepository;
import ch.hslu.wipro.micros.customermanagement.repository.CustomerRepositoryManager;
import ch.hslu.wipro.micros.customermanagement.repository.CustomerRepositorySingleton;
import com.nurkiewicz.asyncretry.RetryExecutor;

public class CustomerManagementApp {
    private static final RabbitMqConfig rabbitMqConfig = new RabbitMqConfig(RabbitMqConsts.CONFIG_FILE);
    private static final RetryExecutor rabbitMqExecutor = RabbitMqConnector.getExecutor();

    public static void main(String[] args) {
        CustomerRepository customerRepository = CustomerRepositorySingleton.getCustomerRepository();
        CustomerRepositoryManager customerRepositoryManager = new CustomerRepositoryManager(customerRepository);
        customerRepositoryManager.generateCustomers(50);

        CommandTopicsConsumerMap commandTopicsConsumerMap = new CommandTopicsConsumerMap();
        CommandManager commandManager = new CommandManager(commandTopicsConsumerMap);

        rabbitMqExecutor.getWithRetry(() -> new ChannelBuilder()
                .withHost(rabbitMqConfig.getHost())
                .build())
                .thenAccept(commandManager::startWithChannel);
    }
}