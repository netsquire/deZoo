package examples;

import entities.SpecificEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import piano.Job;
import piano.Piano;
import piano.StoreService;
import piano.Task;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@Component
@ComponentScan({"entities", "piano"})
public class Melody {

    private static final Logger LOG = LoggerFactory.getLogger(Melody.class);

    @Autowired
    private StoreService<Serializable> storeService;

    @Autowired
    private Piano piano;

    public void compose() {

        LOG.info("=== Start composition ===");
        if (piano == null) {
            System.out.println("----------------------------------");
            System.out.println("--- Piano wasn't instansiated. ---");
            System.out.println("----------------------------------");
        }
        storeService.save(new SpecificEntity(UUID.randomUUID().toString(), 4445));

        piano.addJob("initial", new Job() {
            public void executeJob() {
                System.out.println();
                String givenId = "5fd09568-bb80-4272-8fa0-2c3c72e30d4d";
                System.out.println("givenId = " + givenId);
                LOG.info("(Melody) Given ID value = ", givenId);
                storeService.save(new SpecificEntity(UUID.randomUUID().toString(), 4445));

                SpecificEntity specificEntity = storeService.findById("84163a90-59a1-499b-8084-d2e615eb3a1e")
                        .orElse(new SpecificEntity("SpecificEntity not found", 0));
                System.out.println("FOUND VALUE: " + specificEntity.getValue());
            }
        });

        piano.addJob("second", new Job() {
            public void executeJob() {
                System.out.println("Second task executed.");
            }
        });

        piano.addJob("last", new Job() {
            @Override
            public void executeJob() {
                System.out.println("Last task executed.");
            }
        });

        Job jobBeforeCurrent = new Job() {
            @Override
            public void executeJob() {
                System.out.println("jobBeforeCurrent executed.");
            }
        };

        Job jobAfterCurrent = new Job() {
            @Override
            public void executeJob() {
                System.out.println("jobAfterCurrent executed.");
            }
        };

        Task task = piano.defineTask(new Job() {
            public void executeJob() {
                System.out.println("Manually defined task executed.");
            }
        });
        task.setBefore(jobBeforeCurrent);
        task.setAfter(jobAfterCurrent);
        piano.addTask(task);

        LOG.info("=== End composition ===");
    }

    void dumpAllSpecificEntities() {
        System.out.println("ALL SpecificEntities: ");
        Iterable<SpecificEntity> allSpecific = storeService.findAll();
        allSpecific.forEach(s -> {
            System.out.println(s.getId());
            System.out.println(s.getValue());
            System.out.println(s.getNumber());
            System.out.println();
        });
    }
}