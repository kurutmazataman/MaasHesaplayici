package Implements;

import Entity.Employee;
import Interface.BonusCalculate;
import Interface.Calculate;
import Interface.RaiseSalaryCalculate;
import Interface.TaxCalculate;

public class TotalSalaray implements Calculate {

    private BonusCalculate bonusCalculate;
    private TaxCalculate taxCalculate;
    private RaiseSalaryCalculate raiseSalaryCalculate;

    public TotalSalaray (Calculate... calculates){
        this.bonusCalculate = (BonusCalculate)  calculates[0];
        this.taxCalculate =  (TaxCalculate) calculates[1];
        this.raiseSalaryCalculate = (RaiseSalaryCalculate) calculates[2];
    }

    @Override
    public float calculate(Employee employee) {
        return employee.salary + bonusCalculate.calculate(employee) + raiseSalaryCalculate.calculate(employee) - taxCalculate.calculate(employee);
    }
}
