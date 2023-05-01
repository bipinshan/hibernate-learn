package com.bipin.hibernate;

import jakarta.persistence.*;

@Entity
@Table(name = "EPFO")
public class EPFO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uanNumber;
    private int pfNumber;
    private int monthlyPayout;
    private double interesetRate;

    @OneToOne(mappedBy = "epfo")
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public EPFO() {
    }

    public EPFO(int pfNumber, int monthlyPayout, double interesetRate) {
        this.pfNumber = pfNumber;
        this.monthlyPayout = monthlyPayout;
        this.interesetRate = interesetRate;
    }

    public int getUanNumber() {
        return uanNumber;
    }

    public void setUanNumber(int uanNumber) {
        this.uanNumber = uanNumber;
    }

    public int getPfNumber() {
        return pfNumber;
    }

    public void setPfNumber(int pfNumber) {
        this.pfNumber = pfNumber;
    }

    public int getMonthlyPayout() {
        return monthlyPayout;
    }

    public void setMonthlyPayout(int monthlyPayout) {
        this.monthlyPayout = monthlyPayout;
    }

    public double getInteresetRate() {
        return interesetRate;
    }

    public void setInteresetRate(double interesetRate) {
        this.interesetRate = interesetRate;
    }

    @Override
    public String toString() {
        return "EPFO{" +
                "uanNumber=" + uanNumber +
                ", pfNumber=" + pfNumber +
                ", monthlyPayout=" + monthlyPayout +
                ", interesetRate=" + interesetRate +
                ", employee=" + employee +
                '}';
    }
}
