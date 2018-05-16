package piano;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Foreground tasks: have id as String, mentioned in order setup enumeration
 * natural order - as it is listed
 * explicit - as it is mentioned in task order
 *
 * background tasks: linked to type (or class), with operation set - any action on it triggers execution of task
 *
 */
@Component
class Melody {

    private static final Logger LOG = LoggerFactory.getLogger(Melody.class);

    @Autowired
    private Piano piano;

    void compose() {

        LOG.info("=== Start composition ===");
        if (piano == null) {
            System.out.println("----------------------------------");
            System.out.println("--- Piano wasn't instansiated. ---");
            System.out.println("----------------------------------");
        }

        piano.addJob("initial", new Job() {
            public void executeJob() {
                System.out.println();
                String givenId = "5fd09568-bb80-4272-8fa0-2c3c72e30d4d";
                System.out.println("givenId = " + givenId);
                LOG.info("(Melody) Given ID value = ", givenId);
            }
        }).addJob("second", new Job() {
            public void executeJob() {
                System.out.println("Second task executed.");
            }
        }).addJob("last", new Job() {
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

        piano.setOrder("initial", "second", "first", "last");
        LOG.info("=== End composition ===");
    }

}