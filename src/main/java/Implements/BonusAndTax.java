package Implements;

import Entity.Employee;
import Interface.Calculate;

public class BonusAndTax implements Calculate {

    private Calculate[] calculates;

    public BonusAndTax(Calculate... calculates){
        this.calculates = calculates;
    }

    @Override
    public float calculate(Employee employee) {
        return calculates[0].calculate(employee) - calculates[1].calculate(employee);
    }
}
