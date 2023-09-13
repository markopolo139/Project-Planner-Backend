package pl.ms.projectoverview.app.persistence.converters;

import java.util.List;

public interface Converter<App, Entity>{
    App convertToApp(Entity entity);

    Entity convertToEntity(App appEntity);

    default List<App> convertToApp(List<Entity> entities) {
        return entities.stream().map(this::convertToApp).toList();
    }

    default List<Entity> convertToEntity(List<App> appEntities) {
        return appEntities.stream().map(this::convertToEntity).toList();
    }
}
