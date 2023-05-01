package com.bipin.hibernate;

import jakarta.persistence.*;

import java.util.List;
import java.util.Random;

@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    private int id;
    private String firstName;
    private String lastName;
    private int salary;

    private Certificate certificate;
    @OneToOne
    private EPFO epfo;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Activity> activities;

    @ManyToMany
    @JoinTable(name = "Employee_Project" , joinColumns = {@JoinColumn(name = "EmployeeId")},
            inverseJoinColumns = {@JoinColumn(name = "ProjectId")})
    private List<Project> projects;

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public EPFO getEpfo() {
        return epfo;
    }

    public void setEpfo(EPFO epfo) {
        this.epfo = epfo;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public Employee() {}
    public Employee(String fname, String lname, int salary, Certificate certificate, EPFO epfo) {
        this.id=new Random().nextInt();
        this.firstName = fname;
        this.lastName = lname;
        this.salary = salary;
        this.certificate=certificate;
        this.epfo=epfo;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String first_name ) {
        this.firstName = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String last_name ) {
        this.lastName = last_name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary( int salary ) {
        this.salary = salary;
    }
}