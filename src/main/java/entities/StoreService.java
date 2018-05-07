package entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import piano.Dispatcher;
import piano.Event;

import java.io.Serializable;
import java.util.Optional;

/**
 * TODO:
 *
 *  1)put all repositories here
 *
 *  2) cover it with Introspection and Reflection. AspectJ ?
 */
@Component
public class StoreService < T extends Serializable>{

    @Autowired
    private SpecificEntityRepository specificEntityRepository;

    @Autowired
    private Dispatcher dispatcher;

    //private Map<String, T> declaredTypes = new HashMap<>();

    @Autowired
    public StoreService() {
    }

    public void save(Object objectToStore){
        if (objectToStore instanceof SpecificEntity) {
            SpecificEntity store = (SpecificEntity) objectToStore;
            specificEntityRepository.save(store);
            dispatcher.notify(SpecificEntity.class.getTypeName(), store.getId(), Event.Operation.SAVE);
        }
    }

    Iterable<SpecificEntity>  findAll(){
        return specificEntityRepository.findAll();
    }

    public Optional<SpecificEntity> findById(String s) {
        return specificEntityRepository.findById(s);
    }
}
