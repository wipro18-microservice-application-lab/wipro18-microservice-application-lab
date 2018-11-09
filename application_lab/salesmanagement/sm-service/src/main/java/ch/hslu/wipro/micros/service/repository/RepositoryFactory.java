package ch.hslu.wipro.micros.service.repository;

import ch.hslu.wipro.micros.service.config.ConfigConsts;
import ch.hslu.wipro.micros.service.config.ConfigService;

public class RepositoryFactory {
    private static ConfigService configService = new ConfigService();

    public static RepositoryService getRepository() {
        RepositoryService repositoryService;

        switch (configService.getRepositoryStrategy()) {
            case "mongo_db":
                repositoryService = new RepositoryServiceMongo();
                break;
            default:
                repositoryService = new RepositoryServiceDefault();
        }

        return repositoryService;
    }
}
