package piano;

import entities.SpecificEntity;
import entities.SpecificEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;

/**
 * TODO:
 *
 *  1)put all entities.repositories here
 *
 *  2) cover it with Introspection and Reflection. AspectJ ?
 */
@Component
@ComponentScan("entities")
public class StoreService <T extends Serializable>{

    private static final Logger LOG = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    private SpecificEntityRepository specificEntityRepository;

    @Autowired
    private Dispatcher dispatcher;

    public void save(Object objectToStore){
        if (objectToStore instanceof SpecificEntity) {
            SpecificEntity store = (SpecificEntity) objectToStore;
            specificEntityRepository.save(store);
            EntityEvent entityEvent = new EntityEvent(SpecificEntity.class.getTypeName(), store.getId(), EntityEvent.Operation.SAVE);
            dispatcher.notify(entityEvent);
        }
    }

    public Iterable<SpecificEntity>  findAll(){
        return specificEntityRepository.findAll();
    }

    public Optional<SpecificEntity> findById(String s) {
        System.out.println(("Given ID value = " + s));
        return specificEntityRepository.findById(s);
    }

}
