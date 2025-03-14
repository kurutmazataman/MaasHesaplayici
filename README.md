Bu kod, bir çalışanın maaşını hesaplamak için çeşitli faktörleri dikkate alarak maaş hesaplaması yapan bir yazılım örneğidir. Çalışanın maaşına etki eden faktörler, bonus, vergi ve maaş artışıdır. Kodda ayrıca **Strategy Design Pattern** (Strateji Tasarım Deseni) kullanılmıştır; yani farklı hesaplama algoritmaları birbirinden bağımsız bir şekilde tanımlanır ve bunlar bir araya getirilerek çalışanın maaşı hesaplanır.

Kodun detaylı açıklamasını şu şekilde yapabiliriz:

### 1. `Employee` Sınıfı
**`Employee`** sınıfı, çalışana ait temel bilgileri temsil eder:
- **`name`**: Çalışanın adı.
- **`salary`**: Çalışanın maaşı.
- **`workHours`**: Çalışanın haftalık çalışma saati.
- **`hireYear`**: Çalışanın işe başlama yılı.
- **`CURRENT_YEAR`**: Sabit bir değer olup, şu anda kullanılan yılı temsil eder (bu durumda 2021).

**Yapıcı metod (`constructor`)**:
- Çalışan nesnesini oluştururken, adı, çalışma saati, işe başlama yılı ve maaş gibi bilgileri parametre olarak alır.

### 2. `Calculate` Arayüzü
**`Calculate`** arayüzü, genel hesaplama işlemlerini gerçekleştiren bir arayüzdür. Bu arayüzü implement eden her sınıfın, `calculate(Employee employee)` metodunu sağlamak zorundadır. Bu metod, çalışanın maaşını hesaplamak için kullanılan genel hesaplama algoritmalarını tanımlar.

### 3. `BonusCalculate`, `TaxCalculate`, `RaiseSalaryCalculate` Arayüzleri
Bu üç arayüz, **`Calculate`** arayüzünden türetilmiş olup, sırasıyla bonus, vergi ve maaş artışı hesaplamak için kullanılır:
- **`BonusCalculate`**: Çalışanın bonusunu hesaplar. Haftalık çalışma saati 40 saatin üzerinde ise, fazla saat başına 30 birim ödeme yapılır. Bu bonus sadece fazla mesai yapılan saat kadar hesaplanır.

- **`TaxCalculate`**: Çalışanın maaşına vergi uygular. Eğer maaş 1000 birimin üzerinde ise, maaşın %3'ü kadar bir vergi hesaplanır.

- **`RaiseSalaryCalculate`**: Çalışanın maaşına yapılacak artışı hesaplar. İşteki yıl sayısına bağlı olarak, farklı maaş artışı oranları belirlenmiştir:
    - 9 yıldan az çalışan birine %10 maaş artışı.
    - 9 ile 10 yıl arasında çalışan birine %5 maaş artışı.
    - 19 yıldan fazla çalışan birine %15 maaş artışı.

### 4. `BonusCalculate`, `TaxCalculate`, ve `RaiseSalaryCalculate` Sınıfları
Bu sınıflar, yukarıdaki arayüzleri implement eder ve her bir sınıf, belirli bir hesaplamayı yapar.

#### Bonus Hesaplama (`BonusCalculate`):
```java
public float calculate(Employee employee) {
    return employee.workHours > 40 ? (employee.workHours - 40) * 30 : 0;
}
```
- Eğer çalışanın haftalık çalışma saati 40 saati aşarsa, aşan saat başına 30 birim bonus hesaplanır.

#### Vergi Hesaplama (`TaxCalculate`):
```java
public float calculate(Employee employee) {
    return employee.salary > 1000 ? employee.salary * 0.03f : 0;
}
```
- Eğer çalışanın maaşı 1000 birimin üzerindeyse, maaşın %3'ü kadar vergi hesaplanır. Aksi takdirde vergi 0'dır.

#### Maaş Artışı Hesaplama (`RaiseSalaryCalculate`):
```java
public float calculate(Employee employee) {
    int workingYear = Employee.CURRENT_YEAR - employee.hireYear;
    return workingYear < 9 ? employee.salary * 0.1f : 
           workingYear < 10 ? employee.salary * 0.05f : 
           workingYear > 19 ? employee.salary * 0.15f : 0;
}
```
- Çalışanın işe başlama yılına göre, yıllık maaş artışı hesaplanır. Çalışan 9 yıldan az çalıştıysa %10, 9-10 yıl arasında ise %5 artış alır. 19 yıldan fazla çalışanlara ise %15 artış yapılır.

### 5. `TotalSalaray` Sınıfı
**`TotalSalaray`** sınıfı, **`Calculate`** arayüzünü implement eder ve tüm hesaplamaları birleştirir.
- Bu sınıf, bonus, vergi ve maaş artışı hesaplamalarını birleştirerek toplam maaşı hesaplar.

**Yapıcı metod (`constructor`)**:
- `TotalSalaray` sınıfı, üç hesaplama sınıfı olan `BonusCalculate`, `TaxCalculate` ve `RaiseSalaryCalculate` sınıflarını parametre olarak alır.
- Bu sınıf, her hesaplamayı ilgili nesneler aracılığıyla yaparak toplam maaşı döndürür.

**`calculate` metodu**:
- Çalışanın maaşını alır, ardından bonus, maaş artışı ve vergi hesaplamalarını yaparak toplam maaşı hesaplar.

```java
@Override
public float calculate(Employee employee) {
    return employee.salary + bonusCalculate.calculate(employee) + 
           raiseSalaryCalculate.calculate(employee) - 
           taxCalculate.calculate(employee);
}
```

### 6. `Main` Sınıfı
**`Main`** sınıfı, programın başlangıç noktasıdır. Burada:
- Bir `Employee` nesnesi oluşturuluyor.
- Çalışanın maaşıyla ilgili vergi, bonus ve maaş artışı hesaplamaları yapılıyor.
- `TotalSalaray` sınıfı ile tüm hesaplamalar birleştirilerek toplam maaş hesaplanıyor ve ekrana yazdırılıyor.

**`Main` sınıfındaki kod**:
```java
public static void main(String[] args){
    Employee employee = new Employee("Ataman", (byte) 45, (short) 1985, 2000f);
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
```
Burada **`BonusAndTax`** sınıfı kullanılmış, ancak bu sınıfın tanımlanmadığı bir hata içeriyor. Bu hatanın düzeltilmesi gerekir.

### 7. Özet
- **Çalışan bilgilerini tutan `Employee` sınıfı** var.
- **Hesaplamalar için `Calculate` arayüzünü implement eden** `BonusCalculate`, `TaxCalculate`, `RaiseSalaryCalculate` sınıfları bulunuyor.
- **`TotalSalaray` sınıfı**, tüm hesaplamaları birleştirerek çalışanın **toplam maaşını** hesaplıyor.
- Kod, **hesaplamaları birbirinden bağımsız bir şekilde tanımlayarak** esneklik sağlamış ve farklı stratejilerle hesaplama yapmayı kolaylaştırmıştır.

Bu kodda birkaç yazılım tasarım deseni (design pattern) kullanılmıştır. En belirgin kullanılan desenler şunlardır:

### 1. **Strategy Design Pattern** (Strateji Tasarım Deseni)
**Strategy Design Pattern**, bir algoritmanın ailesini tanımlar, her bir algoritmayı kapsülleyip birbirinin yerine geçebilecek şekilde sağlar. Bu tasarım deseni, algoritmaların dinamik olarak seçilmesine ve kullanılmasına olanak tanır.

Kodda **`Calculate`, `BonusCalculate`, `TaxCalculate`, `RaiseSalaryCalculate`** ve **`TotalSalaray`** sınıfları bu desene örnektir:

- **`Calculate`** arayüzü, hesaplama için genel bir sözleşme tanımlar.
- **`BonusCalculate`, `TaxCalculate`, ve `RaiseSalaryCalculate`** sınıfları, `Calculate` arayüzünü implement eder ve belirli hesaplamalar için farklı stratejiler sağlar.
- **`TotalSalaray`** sınıfı, bu stratejileri birleştirip, çalışanın maaşını hesaplamak için kullanır.

Bu yapı, **çalışan maaş hesaplama** işlemini, her biri kendi hesaplama yöntemine sahip olan farklı stratejilerle yapmayı sağlar. Böylece, yeni hesaplama stratejileri eklemek kolaylaşır, mevcut stratejiler ise birbirinin yerine kullanılabilir.

### 2. **Factory Method Design Pattern** (Fabrika Yöntemi Tasarım Deseni)
**Factory Method** deseni, bir sınıfın nesnelerinin yaratılmasını alt sınıflara bırakmak için kullanılır. Yani, nesnelerin oluşturulma işlemini üst sınıf yerine alt sınıflar gerçekleştirir.

Kodda, **`TotalSalaray`** sınıfındaki yapıcı metot (constructor) bir tür "fabrika yöntemi" olarak çalışıyor. Çünkü bu sınıf, farklı hesaplama stratejilerini (`BonusCalculate`, `TaxCalculate`, `RaiseSalaryCalculate`) alarak çalışanın maaşını hesaplayan nesneleri dinamik bir şekilde oluşturuyor.

```java
public TotalSalaray (Calculate... calculates){
    this.bonusCalculate = (BonusCalculate)  calculates[0];
    this.taxCalculate =  (TaxCalculate) calculates[1];
    this.raiseSalaryCalculate = (RaiseSalaryCalculate) calculates[2];
}
```
Bu yapıda, farklı hesaplama stratejileri (`BonusCalculate`, `TaxCalculate`, `RaiseSalaryCalculate`) gibi nesneler dinamik olarak yaratılmakta ve birleştirilip tek bir nesne (`TotalSalaray`) oluşturulmaktadır. Bu sayede, belirli hesaplamaları değiştirmek veya yeni hesaplama türleri eklemek daha kolay hale gelir.

### 3. **Composite Design Pattern** (Bileşen Tasarım Deseni)
**Composite Design Pattern**, bir nesne ağaç yapısını (tree structure) temsil eder ve nesneleri birbirine benzer şekilde yönetmeyi sağlar. Bu desen, tek bir nesneyle aynı şekilde bir grup nesneyle çalışmayı mümkün kılar.

Kodda, **`TotalSalaray`** sınıfı, farklı hesaplama stratejilerinden (`BonusCalculate`, `TaxCalculate`, `RaiseSalaryCalculate`) birleştirilen bir nesnedir ve farklı hesaplama bileşenlerini birleştirir. Bu sınıf, çeşitli hesaplama stratejilerini tek bir nesne gibi davranacak şekilde kapsüller.

Örneğin, `TotalSalaray` sınıfı, toplam maaşı hesaplamak için `BonusCalculate`, `TaxCalculate`, ve `RaiseSalaryCalculate` sınıflarını içeren bir bileşen olarak çalışır:
```java
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
```
Burada, **`TotalSalaray`**, farklı bileşenlerin (bonus, vergi, maaş artışı hesaplamalarını) birleştiren bir yapıdır. Bu da Composite deseni olarak kabul edilebilir çünkü çeşitli hesaplamaları (bileşenleri) tek bir hesaplama yapısında birleştirir.

### 4. **Dependency Injection** (Bağımlılık Enjeksiyonu)
Bağımlılık enjeksiyonu, nesnelerin birbirlerine olan bağımlılıklarını dışarıdan, genellikle yapıcı metotlarla (constructor) sağlamak için kullanılır. Bu desen, bağımlılıkları (yani bir sınıfın kullandığı diğer sınıflar) doğrudan sınıf içinde yaratmak yerine dışarıdan sağlar.

Kodda, **`TotalSalaray`** sınıfının yapıcı metodunda (`constructor`), **`BonusCalculate`, `TaxCalculate`** ve **`RaiseSalaryCalculate`** nesneleri dışarıdan alınır ve sınıfın içinde kullanılır. Bu sayede, **`TotalSalaray`** sınıfı, bağımlılıklarını dışarıdan alır ve daha kolay test edilebilir ve bakım yapılabilir bir hale gelir.

```java
public TotalSalaray (Calculate... calculates){
    this.bonusCalculate = (BonusCalculate)  calculates[0];
    this.taxCalculate =  (TaxCalculate) calculates[1];
    this.raiseSalaryCalculate = (RaiseSalaryCalculate) calculates[2];
}
```

Buradaki yapıcı metod, **bağımlılık enjeksiyonunu** sağlar. Bu sayede, `TotalSalaray` sınıfı, dışarıdan verilen nesneleri kullanarak işlem yapar ve belirli sınıflara olan bağımlılıkları azaltılmış olur.

### Özetle:
Bu kodda kullanılan başlıca yazılım tasarım desenleri şunlardır:

1. **Strategy Design Pattern**: Çalışan maaşını hesaplamak için farklı stratejilerin tanımlanması ve uygulanması.
2. **Factory Method Design Pattern**: `TotalSalaray` sınıfının hesaplama stratejilerini alarak nesne yaratma işlevi.
3. **Composite Design Pattern**: Farklı hesaplama bileşenlerinin birleştirilmesi.
4. **Dependency Injection**: Nesnelerin bağımlılıklarının dışarıdan sağlanması.

Bu desenler, kodun daha esnek, bakım yapılabilir ve genişletilebilir olmasına olanak tanır.