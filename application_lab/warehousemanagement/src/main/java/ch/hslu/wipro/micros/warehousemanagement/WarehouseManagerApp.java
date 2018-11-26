package ch.hslu.wipro.micros.warehousemanagement;

import ch.hslu.wipro.micros.warehousemanagement.rabbitmq.ChannelBuilder;
import ch.hslu.wipro.micros.warehousemanagement.rabbitmq.RabbitMqConnector;
import ch.hslu.wipro.micros.warehousemanagement.rabbitmq.config.RabbitMqConfig;
import ch.hslu.wipro.micros.warehousemanagement.rabbitmq.config.RabbitMqConsts;
import ch.hslu.wipro.micros.warehousemanagement.rabbitmq.manager.CommandManager;
import ch.hslu.wipro.micros.warehousemanagement.repository.ArticleRepository;
import ch.hslu.wipro.micros.warehousemanagement.repository.ArticleRepositoryManager;
import ch.hslu.wipro.micros.warehousemanagement.repository.ArticleRepositorySingleton;
import com.nurkiewicz.asyncretry.RetryExecutor;

public class WarehouseManagerApp {
    private static final RabbitMqConfig rabbitMqConfig = new RabbitMqConfig(RabbitMqConsts.CONFIG_FILE);
    private static final RetryExecutor rabbitMqExecutor = RabbitMqConnector.getExecutor();

    public static void main(String[] args) {
        ArticleRepository articleRepository = ArticleRepositorySingleton.getArticleRepository();
        ArticleRepositoryManager articleRepositoryManager = new ArticleRepositoryManager(articleRepository);
        articleRepositoryManager.generateRandomInventory(50);

        CommandTopicsConsumerMap commandTopicsConsumerMap = new CommandTopicsConsumerMap();
        CommandManager commandManager = new CommandManager(commandTopicsConsumerMap);

        rabbitMqExecutor.getWithRetry(() -> new ChannelBuilder()
                .withHost(rabbitMqConfig.getHost())
                .build())
                .thenAccept(commandManager::startWithChannel);
    }
}