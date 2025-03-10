package Entity;

public class Employee {

    public String name;

    public float salary;

    public byte workHours;

    public short hireYear;

    public static final int CURRENT_YEAR = 2021;

    public Employee(String name,byte workHours,short hireYear,float salary){
        this.name = name;
        this.workHours = workHours;
        this.hireYear = hireYear;
        this.salary = salary;
    }
}
