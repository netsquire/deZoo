package examples;

import entities.SpecificEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import piano.Job;
import piano.Piano;
import piano.SpringApplication;
import piano.StoreService;

import java.util.UUID;

@Component
public class PianoApplication {

    private static final Logger LOG = LoggerFactory.getLogger(SpringApplication.class);
    @Autowired
    private StoreService storeService;

    @Autowired
    private Piano piano;

    public void run() {

        if (piano == null) {
            System.out.println("----------------------------------");
            System.out.println("--- Piano wasn't instansiated. ---");
            System.out.println("----------------------------------");
        }
        storeService.save(new SpecificEntity(UUID.randomUUID().toString(), 4445));

        piano.addJob("initial", new Job() {
            public void executeJob() {
                SpecificEntity entity = null;
                try {
                    entity = (SpecificEntity) storeService
                            .findById("7f6131f000-0685-4cb2-83f2-6fecc0389ec4")
                            .orElseThrow(() -> new Exception("404 Not Found."));
                    System.out.println("Entity part: " + entity.getValue());
                } catch (Throwable throwable) {
                    //throwable.printStackTrace();
                    LOG.error("Exception happened because of: " + throwable.getMessage() + " // " + throwable.getCause());
                }
                storeService.save(new SpecificEntity(UUID.randomUUID().toString(), 4445));
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
                System.out.println( "Last task executed.");
            }
        });

        Job jobBeforeCurrent = new Job() {
            @Override
            public void executeJob() {
                System.out.println( "jobBeforeCurrent executed.");
            }
        };

        Job jobAfterCurrent = new Job() {
            @Override
            public void executeJob() {
                System.out.println( "jobAfterCurrent executed.");
            }
        };

        piano.play();
    }
}

    /*
     // initial tasks


        Task task = piano.defineTask(new Job() {
            public String executeJob() {
                return "Manually defined task executed.";
            }
        });

        task.setBefore(jobBeforeCurrent);
        task.setAfter(jobAfterCurrent);
        piano.addTask(task);
        // dependent (hooked) tasks
        dispatcher.addResponsableforType(null,  new Job(){
            @Override
            public String executeJob() {
                return "ID";
            }
        });*/

