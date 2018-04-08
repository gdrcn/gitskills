# 一. ***java特点***
> * java并不是只是一种语言，java是一个完整的平台，有一个庞大的库。
> * (自我感受)java的包装性[把功能包装为一个对象]，可移植性强大[跨平台]
> * java的面向对象特点显著 
#### jre
> JRE指的是java运行时环境，它包含虚拟机但不包括编译器
# 三. ***java的基本程序设计结构***
> * 关键字class表明java程序中的全部内容都包含在类中。(可将类作为一个加载程序逻辑的容器，程序逻辑定义了应用程序的行为)
> * 类的命名要求:名字必须字母开头，后面可以跟数字跟字母的任意组合
> * **java的main方法必须声明为public**
> * (.)表示调用方法
### 数据类型细节
> * 数据类型4类8种,与c语言不同的是java中整型的范围与运行java代码的机器无关.较好地解决移植方面的问题
> * java7后可以为数字的字面量下加下划线,例如1_000_000_000
> * java没有任何形式的无符号形式的int long short 和byte类型
#### 浮点型
> 可以使用16进制表示浮点数值.例如,0.125 = 2 ^ -3 可以表示为0X1.0p^-3.在16进制中,使用p表示指数,而不是e.注意,尾数采用16进制,指数采用10进制.  

 特殊的浮点数值  
> * 正无穷大  Double.POSITIVE_INFINITY
> * 负无穷大  Double.NEGATIVE_INFINITY
> * NaN(不是一个数字) Double.NaN  
   
   **判断某个数值是否为特殊数值,不可直接用==**

```
if(Double.isNaN(x))//check whther x is "not a number"
```

> * 浮点数值不适用无法接受误差的计算,原因是浮点数值采用二进制系统表示,而导致无法得出精确的分数数值.

#### 字符型
> * 与c语言不同,java的char为两个字节.
  
**注意** - 注释中若存在/u可能产生因换行而导致的错误

#### 布尔型
> * 整数型与布尔型不能相互转换

#### 变量 
> * java中不区分声明和定义

#### 常量
* **定义方法**
```
final double CM_PER_INCH = 2,54;
```
* final表示不可再修改

#### 运算符 
> * 整数除0会抛出异常
> * 浮点数除0会得到特殊值  
> * **strictfp** - 将类标记为strictfp后,该类中所有方法都进行严格的浮点运算
#### 数值之间的类型转换
* 字节大转字节小会丢失精度

#### 枚举类型
```
enum Size{SMALL,MEDIUM,LARGE,EXTRA_LARGE};
Size s = Size.MEDIUM;
```
#### 字符串
> * 不可变字符串--  
 好处: 编译器可以让字符串共享.  
 坏处:不能具体改动字符串
#### 空串与Null串
> * 空串有自己的长度和内容。而null表示与任何对象都没有关联
#### 读取输入

```
 Scanner in = new Scanner(System.in);
String name = in.nextLine();
```
* 输入一行
* **为了适用于从控制台读取密码**
```
Console cons = System.console;
String username = cons.readLine("User name:");
char[] passwd = cons.readPassword("Passwore :");
```
#### 格式化输出
* 在printf中可使用多个参数
```
System.out.printf("Hello,%s,Next year, you will be %d",name.age);
```
#### 文件输入与输出
```
Scanner in = new Scanner(Path.get("myfile.txt"),"UTF-8");
```
* 写入文件
```
PrintWrite out = PrintWrite("myfile.txt","UTF-8");
```
*相对路径(找到路径)
```
String dir = System.getProperty("user.dir");
```
#### 流程控制
* java中没有goto语句，break语句可以带标签，for有变形版
#### break与continue标签
* break和continue跳出标签所在的循环
```
read_data:
while(){
    for(){
      break read_data;  
    }
}
```
* case标签
> 1.类型为char,byte,short和int的常量表达式  
> 2.枚举常量  
> 3.可以是字符串字面量
#### 大数值
> * BigInteger和BigDecimal这两个类可以处理任意长度数字序列
> * 这两个类的运算不能用一般的算术运算符处理
```
BigInteger a = BigInteger.valueOf(100);
```
#### foreach
```
for(variable : collection)statement;
```
* 优点:遍历方便
#### 数组拷贝
```
int [] luckyNumber = smallPrimes;
```
> * 在java中允许将一个数组变量拷贝给另一个数组

# ***四.对象与类***
> 类是构造对象的模板和蓝图。由类构造对象的过程称为创建类的实例  
#### 封装
> 封装有时称为数据隐藏。从形势上看是将数据和行为组合在一个包里，并对对象的使用者隐藏了数据的实现方式。封装的关键在于绝对不能让类中的方法直接地访问其他类的实例域。
#### 类之间的关系
> 依赖("uses-a")  
> 聚合("has-a")  
> 继承("is-a")  
#### 对象与对象变量
```
Date birthday = new Date();
```
* 明白对象变量只是引用，而不是对象
#### 构造器
> 1.构造器与类同名  
> 2.每个类可以有一个以上的构造器  
> 3.构造器可以有参数或者无参数  
> 4.构造器没有返回值  
> 5.构造器总是伴随new操作一起调用
#### 封装的优点
* 良好的封装需要获得或者设置实例域的值要提供的内容
> 一个私有的数值域   
> 一个共有的域访问方法
> 一个共有的域更改方法
#### final实例域
> 如果一个域的值在被设置，并且后面的操作中，不需要再对其修改。可以利用final将其不可变。
```
private final String name;
```
#### 静态域和静态方法
> 如果将域定义为static，每个类中只有一个这样的域。
```
class Employee{
    private static int nextId = 1;
    private int id;
}
```
* 现在每个雇员对象都有自己的id域，但是却共享一个nextId域。即使没有对象，静态域也存在，它属于类，而不是对象。
#### 静态常量
```
public class Math{
    public static final double PI = 3.141592653;
}
```
* 在程序中可以用Math.PI的形式获取常量，而不需要创建对象。
#### 静态方法
> 静态方法是一种不能向对象实施操作的方法。
```
Math.pow(x,a)
```
> 没有创建对象静态方法可调用自身类的静态域，而不可调用非静态域。
#### 方法参数
> 1.一个方法不能修改一个基本数据类型的参数  
> 2.一个方法可以改变一个对象的参数状态  
> 3.一个方法不能让对象参数引用一个新的对象
#### 无参数的构造器
> 仅当类没有提供任何构造器的时候，系统才会提供一个默认的无参数构造器。
#### 参数名
```
public Employee(String name,double salary){
    this.name = name;
    this.salary = salary;
}
```
> this指示隐式参数，也就是所构造的对象
#### 调用另一个构造器
```
public Employee(double s){
    this("Employee #" + nextId,s);
    nextId++;
}
```
#### 方法注释
> @param方法变量描述  
> @return描述  
> @throws类描述
# ***五.继承***
#### 定义子类
```
public class Manager extends Employee{
    
}
```
#### 覆盖方法
```
public class Manager extends Employee{
    public double getSalary(){
        
    }
}
```
* 当父类的方法为私有时
```
public double getSalary(){
   double baseSalary = super.getSalary();
   return baseSalary+bonus;
}

```
> 在覆盖一个方法时，子类的方法不能低于超类方法的可见性。
#### 子类构造器
* 子类构造方法的实现需要父类的方法参与

```
public Manager(String name,double salary,int year,int month,int day){
    super(name,salary,year,month,day);
    bonus=0;
}
```
### 多态
> 1.要有继承  
> 2.要有重写  
> 3.要有父类引用指向子类对象  
> > 多态实现了同一操作作用于不同的对象，可以有不同的解释，产生不同的执行结果，这就是多态性。
#### 类型转换
> 只能在继承层次内进行类型转换  
> 在讲超类转换成子类前，应该用instanceof进行检查

### 抽象类
> 如果自上而下在类的继承层次结构中上移，位于上层的类更具有通用性，甚至更抽象。例如：如果有人这个类，那么我们除了姓名，对人这个类一无所知。这次抽象类作用就出来了
```
public abstract class Person{
    
}
```
* 包含一个或者多个抽象方法的类本身必须被声明为抽象类
```
public abstract class Person{
    private String name;
    public Person(String name){
        this.name = name;
    }
    public abstract String getDescription();
    
    public String getName(){
        return name;
    }
}
```
* 虽然不能实例化抽象对象，但是可以定义一个抽象类的对象变量，而且只能将其引用至非抽象类的对象。  
* 抽象类与接口的区别  
>  1.一个类可以实现多个接口 ，但却只能继承最多一个抽象类。  
> 2.抽象类可以包含具体的方法 ， 接口的所有方法都是抽象的。  
> 3.抽象类可以声明和使用字段 ，接口则不能，但接口可以创建静态的final常量。  
> 4.接口的方法都是public的，抽象类的方法可以是public，protected，private或者默认的package；  
> 5.抽象类可以定义构造函数，接口却不能
#### 受保护的访问
> 仅对本类可见 —— private  
> 对所有类可见 —— public  
> 对本包和所有子类可见 —— protected  
> 对本包可见 —— （默认）
### Object 
```
Object obj = new Employee("Harry Hacker", 3500);
Employee e = (Employee) obj;
```
* Object 为所有类的父类
* Object 的equals方法是比较引用是否相同，需重写。
#### equals方法特点
>   1.自反性：对于任何非空引用x，x.equals(x)应当返回true  

>  2.对称性：对于任何引用x和y，当且仅当y.equals(x)返回true，x.equals(y)也应该返回true  

>  3.传递性：对于任何引用x,y和z。如果y.equals(x)返回true，y.equals(z)也返回true，则x.equals(z)也应该返回true  

> 4. 一致性： 如果x和y的引用对象没有发生任何变化，反复调用x.equals(y)应该返回同样的结果  

> 5. 对于任何非空引用x，x.equals(null)应返回false
#### hashcode
> hashcode是由对象导出的一个整型值，无规律。
> String的字符串相同的对象的hashCode相同，而StringBuffer的却不相同，因为StringBuffer类中没有定义hashCode方法，所以其默认是Object类的hashCode方法
#### toString方法
> 它用于返回表示对象值的字符串

> 当对象与字符串用'+'进行连接时，系统自动调用toString方法

> 调用x.toString()可以用 ""+x 替代

#### 泛型数组列表
```
ArrayList<Employee> staff = new ArryList<Employee>();
```
```
ArrayList<Employee> staff = new ArryList<>();
```
* 添加成员
```
staff.add(new Employee("Harry Hacker",……));
```
* 如果集合中的内部数组已经满了，将自动创建一个更大的数组

* 分配内容
```
staff.ensureCapacity(100);
```
* 将初始容量传递给构造器
```
ArrayList<Employee> staff = new ArrayList<>(100);
```
>  > 与数组不同的是容器在定义了大小后并不分配空间，只是说明它需要当前大小空间的潜力
* trimToSize 当容器不再需要多余的空间，可调用该方法回收多余的空间

#### 对象包装器和自动装箱
> > 自动装箱与自动拆箱会抛出异常
>  对象包装器的类是不可更改的，即一旦构造了包装器，就不允许更改其中的值
```
list.add(3); //等价
list.add(Integer.valueOf(3)); //自动装箱
```

```
int n = list.get(i);//自动拆箱
int n = list.get(i).intValue();//等价
```
```
Integer n = 3;
n++;
```
* 编译器将自动插入一条对象拆箱的指令，然后进行自增计算，最后将结果装箱。
#### 捕获异常
> 异常： 未检查异常和检查异常
# ***六.接口，lambda表达式与内部类***

```
class Employee implements Comparable
```
> 接口中的所以方法都为public static final类型
```
class Employee implements Comparable<Employee>{
    public int compareTo(Employee other){
        return Double.compare(salary,other.salary);
    }
}
```
#### 解决默认方法冲突
> 一个类继承的父类与实现的接口含有完全一样的方法，则在调用时会优先选择父类的方法
* 若一个实现的两个接口含有相同的方法，解决方法为在实现方法时具体写明(即使有一个方法没有具体实现，也有声明实现哪个方法)
```
class Student implements Person,Named(){
    public String getNamed(){
        return Person.super.getName();
    }    
}
    
```
# ***七.异常，断言和日志***
#### throw 与 throws区别
> 1、throw是在代码块内的，即在捕获方法内的异常并抛出时用的；

> 2、throws是针对方法的，即将方法的异常信息抛出去

> 3、可以理解为throw是主动（在方法内容里我们是主动捕获并throw的），而throws是被动（在方法上是没有捕获异常进行处理，直接throws的）
```
public void str2 int (String str) throws Exception{
    try{
        System.out.println(Integer.parseInt(str));
    }catch(NumbeFormatException e){
        throw new Exception("格式化异常");
    }
}
```

