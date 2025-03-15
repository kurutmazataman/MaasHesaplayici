import Implements.*;
import Entity.Employee;

public class Main {
    public static void main(String[] args){
        System.out.println("Adı: " + Employee.getInstanceOfEmployee().name + "\n" +
                "Maaşı: " + Employee.getInstanceOfEmployee().salary + "\n" +
                "Çalışma Saati: " + Employee.getInstanceOfEmployee().workHours + "\n" +
                "Başlangıç Yılı: " + Employee.getInstanceOfEmployee().hireYear + "\n" +
                "Vergi: " + new TaxCalculate().calculate(Employee.getInstanceOfEmployee()) + "\n" +
                "Bonus: " + new BonusCalculate().calculate(Employee.getInstanceOfEmployee()) + "\n" +
                "Maaş Artışı: " + new RaiseSalaryCalculate().calculate(Employee.getInstanceOfEmployee()) + "\n" +
                "Vergi ve Bonuslarla ile birlikte maaş: " + new BonusAndTax(new BonusCalculate(),new TaxCalculate()).calculate(Employee.getInstanceOfEmployee()) + "\n" +
                "Toplam Maaş: " + new TotalSalaray(new BonusCalculate(),new TaxCalculate(), new RaiseSalaryCalculate()).calculate(Employee.getInstanceOfEmployee()));
    }
}

