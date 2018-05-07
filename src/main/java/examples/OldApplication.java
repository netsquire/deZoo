package examples;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import piano.Dispatcher;
import piano.Job;
import piano.Piano;
import piano.Task;

import java.util.List;

public class OldApplication {

    private static Piano piano = Piano.build();
    private static Dispatcher dispatcher = new Dispatcher();

    private static SessionFactory factory;

    public static void main(String args[]){
        try {
            factory = new Configuration().
                    configure().
                    addAnnotatedClass(SpecificEntity.class).
                    buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        OldApplication app = new OldApplication();
        System.out.println(app.addSpecificEntity("Entity1", 33));
        System.out.println(app.addSpecificEntity("Entity2", 133));
        System.out.println(app.addSpecificEntity("Entity3", 233));
        System.out.println(app.addSpecificEntity("Entity4", 333));

        // initial tasks
        piano.addJob("initial", new Job() {
            public String executeJob() {
                return "Initial task executed.";
            }
        });
        piano.addJob("second", new Job() {
            public String executeJob() {
//                SpecificEntity entity = piano.getEntity(SpecificEntity.class, UUID.randomUUID().toString());
//                entity.setValue(entity.getValue()+7);
//                piano.persistEntity(entity);

                return "Second task executed.";
            }
        });
        piano.addJob("last", new Job() {
            @Override
            public String executeJob() {
                return "Last task executed.";
            }
        });
        Job jobBeforeCurrent = new Job() {
            @Override
            public String executeJob() {
                return "jobBeforeCurrent executed.";
            }
        };
        Job jobAfterCurrent = new Job() {
            @Override
            public String executeJob() {
                return "jobAfterCurrent executed.";
            }
        };
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
        });
        piano.play();
        factory.close();
    }

    private String addSpecificEntity(String name, int number){
        Transaction tx = null;
        String specificEntityID = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            SpecificEntity specificEntity = new SpecificEntity();
            specificEntity.setValue(name);
            specificEntity.setNumber(number);
            specificEntityID = (String) session.save(specificEntity);
            tx.commit();
        } catch (HibernateException e) {
            //if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return specificEntityID;
    }

    public void listSpecificEntity( ){
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            List specificEntities = session.createQuery("FROM Employee").list();
            for (Object entity : specificEntities) {
                SpecificEntity specificEntity = (SpecificEntity) entity;
                System.out.print("ID: " + specificEntity.getId());
                System.out.print(" Value : " + specificEntity.getValue());
                System.out.println(" Number: " + specificEntity.getNumber());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void updateSpecificEntity(Integer EmployeeID, int salary ){
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            SpecificEntity specificEntity = session.get(SpecificEntity.class, EmployeeID);
            specificEntity.setNumber(salary);
            session.update(specificEntity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void deleteSpecificEntity(Integer EmployeeID){
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            SpecificEntity specificEntity = session.get(SpecificEntity.class, EmployeeID);
            session.delete(specificEntity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

}
