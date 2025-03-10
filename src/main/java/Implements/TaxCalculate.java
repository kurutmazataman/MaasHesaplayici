package Implements;

import Entity.Employee;

public class TaxCalculate implements Interface.TaxCalculate {
    @Override
    public float calculate(Employee employee) {
        return employee.salary > 1000 ? employee.salary * 0.03f : 0;
    }
}
