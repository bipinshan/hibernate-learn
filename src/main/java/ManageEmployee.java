import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import com.bipin.hibernate.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class ManageEmployee {
    private static SessionFactory factory;
    public static void main(String[] args) {

        try {
            factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        ManageEmployee ME = new ManageEmployee();

        /* Add few employee records in database */
        Integer empID1 = ME.addEmployee(new Random().nextInt(1000)+"Zara", "Ali", 1000);
        //Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
        //Integer empID3 = ME.addEmployee("John", "Paul", 10000);

        /* List down all the employees */
        ME.listEmployees();

        /* Update employee's records */
        ME.updateEmployee(empID1, 5000);

        /* Delete an employee from the database */
        //ME.deleteEmployee(empID2);

        /* List down new list of the employees */
        ME.listEmployees();

        int addressId=addAddress();
        System.out.println("Address with Id: "+addressId+" created.");

        Employee employee=getEmployeeWithEPFO(empID1);

        int personId=addPerson();
        System.out.println("Person Saved with ID: "+personId);
    }

    private static int addPerson() {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer personId = null;

        try {
            tx = session.beginTransaction();
            Person person = new Person("Ramesh Kallu");
            personId = (Integer) session.save(person);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
        finally
        {
            session.close();
        }
        return personId;
    }

    private static Employee getEmployeeWithEPFO(Integer EmployeeID) {
        Session session = factory.openSession();
        Transaction tx = null;
        Employee employee=null;
        try {
            tx = session.beginTransaction();
            // Use when you are not sure if record is present or not
             employee= (Employee)session.get(Employee.class, EmployeeID);
            // Lazy loading - Use when you are sure tha record exist in database
            //Employee employeeUsingLoadMethod = (Employee)session.load(Employee.class, EmployeeID);
            System.out.println(employee.getEpfo());
            System.out.println(employee.getActivities());
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return  employee;
    }

    private static int addAddress() {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer addressId = null;

        try {
            FileInputStream fileInputStream=new FileInputStream("src/main/java/sticky-notes.png");
            byte[] imageData=new byte[fileInputStream.available()];
            fileInputStream.read(imageData);
            tx = session.beginTransaction();
            Address address = new Address("Sri ram nagar Colony","Hyderabad", true,1234.43,new Date(),imageData );
            addressId = (Integer) session.save(address);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();;
        }finally
        {
            session.close();
        }
        return addressId;
    }

    /* Method to CREATE an employee in the database */
    public Integer addEmployee(String fname, String lname, int salary){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;

        try {
            tx = session.beginTransaction();
            Certificate certificate=new Certificate("Spring MVC", "6 Months");

            EPFO epfo=new EPFO(new Random().nextInt(), new Random().nextInt(10000,3000000),7);

            Employee employee = new Employee(fname, lname, salary, certificate, epfo);

            Activity email=new Activity("Email","Product launch webinar","Some email additional details");
            email.setEmployee(employee);
            Activity task=new Activity("Task","Send Product Service details","Some task additional details");
            task.setEmployee(employee);
            Activity phoneCall=new Activity("Phone Call","Folow-up","Some phone call additional details");
            phoneCall.setEmployee(employee);
            employee.setActivities(List.of(email,task, phoneCall));

            Project brms=new Project("BRMS");
            Project mrcrm=new Project("MRCRM");

            employee.setProjects(List.of(brms,mrcrm));

            session.save(epfo);

            session.save(brms);
            session.save(mrcrm);

            employeeID = (Integer) session.save(employee);


            //session.save(email);
            //session.save(task);
            //session.save(phoneCall);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeID;
    }

    /* Method to  READ all the employees */
    public void listEmployees( ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // HQL
            List employees = session.createQuery("FROM Employee").list();

            //Parameterization
            String query="from Employee where lastName=:lastname";
            Query q=session.createQuery(query, Employee.class);
            q.setParameter("lastname", "Ali");
            q.setFirstResult(0);
            q.setMaxResults(2);
            List<Employee> employees1=q.list();
            System.out.println("Result using HQL Parameterization");
            for(Employee e: employees1)
            {
                System.out.println(e.getFirstName()+" : "+e.getLastName());
            }

            //SQL
            List<Employee> employeeList= session.createNativeQuery("select *from employee", Employee.class).list();
            System.out.println("Result using native sql");
            for(Employee e: employeeList)
            {
                System.out.println(e.getFirstName()+" : "+e.getLastName());
            }
            for (Iterator iterator = employees.iterator(); iterator.hasNext();){
                Employee employee = (Employee) iterator.next();
                System.out.print("First Name: " + employee.getFirstName());
                System.out.print("  Last Name: " + employee.getLastName());
                System.out.println("  Salary: " + employee.getSalary());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to UPDATE salary for an employee */
    public void updateEmployee(Integer EmployeeID, int salary ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            // Use when you are not sure if record is present or not
            Employee employee = (Employee)session.get(Employee.class, EmployeeID);
            // Lazy loading - Use when you are sure tha record exist in database
            //Employee employeeUsingLoadMethod = (Employee)session.load(Employee.class, EmployeeID);
            employee.setSalary( salary );
            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to DELETE an employee from the records */
    public void deleteEmployee(Integer EmployeeID){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee)session.get(Employee.class, EmployeeID);
            session.delete(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}