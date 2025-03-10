package Implements;

import Entity.Employee;

public class BonusCalculate implements Interface.BonusCalculate {
    @Override
    public float calculate(Employee employee) {
        return employee.workHours > 40 ? (employee.workHours - 40) * 30 : 0;
    }
}
