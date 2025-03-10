import Implements.*;
import Entity.Employee;
import Interface.Calculate;

public class Main {
    public static void main(String[] args){
        Employee employee = new Employee("Ataman",(byte) 45, (short) 1985,2000f);

        System.out.println("Adı: " + employee.name + "\n" +
                "Maaşı: " + employee.salary + "\n" +
                "Çalışma Saati: " + employee.workHours + "\n" +
                "Başlangıç Yılı: " + employee.hireYear + "\n" +
                "Vergi: " + new TaxCalculate().calculate(employee) + "\n" +
                "Bonus: " + new BonusCalculate().calculate(employee) + "\n" +
                "Maaş Artışı: " + new RaiseSalaryCalculate().calculate(employee) + "\n" +
                "Vergi ve Bonuslarla ile birlikte maaş: " + new BonusAndTax(new BonusCalculate(),new TaxCalculate()).calculate(employee) + "\n" +
                "Toplam Maaş: " + new TotalSalaray(new BonusCalculate(),new TaxCalculate(), new RaiseSalaryCalculate()).calculate(employee));
    }
}

