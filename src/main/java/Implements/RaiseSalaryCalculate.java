package Implements;

import Entity.Employee;

public class RaiseSalaryCalculate implements Interface.RaiseSalaryCalculate {
    @Override
    public float calculate(Employee employee) {
        int workingYear = Employee.CURRENT_YEAR - employee.hireYear;
        return workingYear < 9 ? employee.salary * 0.1f : workingYear < 10 ? employee.salary * 0.05f : workingYear > 19 ? employee.salary * 0.15f : 0;
    }
}
