package pl.ms.projectoverview.app.converters;

import java.util.List;

public interface Converter<App, Entity, Model>{
    App convertEntityToApp(Entity entity);

    Entity convertToEntity(App appEntity);

    default List<App> convertEntityToApp(List<Entity> entities) {
        return entities.stream().map(this::convertEntityToApp).toList();
    }

    default List<Entity> convertToEntity(List<App> appEntities) {
        return appEntities.stream().map(this::convertToEntity).toList();
    }

    App convertModelToApp(Model entity);

    Model convertToModel(App appEntity);

    default List<App> convertModelToApp(List<Model> models) {
        return models.stream().map(this::convertModelToApp).toList();
    }

    default List<Model> convertToModel(List<App> appEntities) {
        return appEntities.stream().map(this::convertToModel).toList();
    }
}
