package entities;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines mapping of present entities to datasources
 *
 */
public class EntityMapping {

    private Map<Entity, String> map = new HashMap<>();
    private Map<Entity, DataSource> datasources = new HashMap<>();

    DataSource getDatasource(Entity entity){
        return datasources.get(entity);
    }

    void putDataSource(Entity entity, DataSource dataSource) {
        datasources.put(entity, dataSource);
    }

    public Map<Entity, String> getMap() {
        return map;
    }

    public void setMap(Map<Entity, String> map) {
        this.map = map;
    }

    public Map<Entity, DataSource> getDatasources() {
        return datasources;
    }

    public void setDatasources(Map<Entity, DataSource> datasources) {
        this.datasources = datasources;
    }
}
