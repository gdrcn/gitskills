 # ***集合***
>  数组长度固定，而集合长度可增可减
> 集合种类  (collection)  
 > 1.List(有序可重复)  
-  常用的ArrayList和LinkList
```
ArrayList<String> arrayList = new ArrayList<String>();
arrayList.add("张三");
```
```
LinkedList<String> linkedList = new LinkedList<String>();
linkedList.add("李四");
```

2. set集合(不允许重复)
```
HashSet<String> hs = new HashSet<String>();
```
3. Map集合(K,V)
```
HashMap<String,Student> hashmap = new Hashmap<String,Student>();
hashmap.put("1号"，new Student("张三"，10));
Student s= hashmap.get("1号");

Student s = hashmap.get("1号");
System.out.println(s);

Iterator <String> it = hashmap.keySet().iterator();
while(it.hashNext()){
    String key = it.next();//获取key
    Student student = hashmap.get(key);//获取值
    Student.out.println("key ="+key,"value ="+student);
}
```
#### 迭代器（Iterator）
> > 迭代器是一种设计模式，它是一个对象，它可以遍历并选择序列中的对象，而开发人员不需要了解该序列的底层结构。迭代器通常被称为“轻量级”对象，因为创建它的代价小。

> Java中的Iterator功能比较简单，并且只能单向移动：

>　　(1) 使用方法iterator()要求容器返回一个Iterator。第一次调用Iterator的next()方法时，它返回序列的第一个元素。注意：iterator()方法是java.lang.Iterable接口,被Collection继承。

>　　(2) 使用next()获得序列中的下一个元素。

>　　(3) 使用hasNext()检查序列中是否还有元素。

>　　(4) 使用remove()将迭代器新返回的元素删除。

> >　　Iterator是Java迭代器最简单的实现，为List设计的ListIterator具有更多的功能，它可以从两个方向遍历List，也可以从List中插入和删除元素。
```
 list l = new ArrayList();
 l.add("aa");
 l.add("bb");
 l.add("cc");
 for (Iterator iter = l.iterator(); iter.hasNext();) {
     String str = (String)iter.next();
     System.out.println(str);
 }
 /*迭代器用于while循环
 Iterator iter = l.iterator();
 while(iter.hasNext()){
     String str = (String) iter.next();
     System.out.println(str);
 }
 *
```
* 一个集合一旦指定了泛型，那么该集合就只能存同一种类型的数据，那么取数据的时候，就不用强转了
* 泛型只支持引用数据类型，基本数据类型要使用它的包装类

| 基本数据类型 | 包装类 |
|:--: |:--: |
| byte|Byte|
|boolean|Boolean|
|short|Short|
|char|Character|
|int|Integer|
|long|Long|
|float|Float|
|double|Double|
```
System.out.println(new Student("张三"，12).toString);
```
- 输出结果为IT.Student@1a2f973  
- 需要重写自定义类的toString方法

> 凡是把类对象放到以哈希表为内部存储结构的容器中，相应的类必须要实现equals方法和hashCode方法，这样才符合哈希表真实的逻辑功能. 

> 1、凡是把类对象放到容器中，相应的类都应该实现Object类中的toString()方法；   
2、凡是需要进行比较排序的类都应该实现Comparable接口中的compareTo()方法；凡是把类对象放到以树为内部结构的容器中都应该实现Comparable接口中的compareTo()方法   
3、凡是把类对象放到以哈希表为内部存储结构的容器中，相应的类必须要实现equals方法和hashCode方法，这样才符合哈希表真实的逻辑功能.  
4、逻辑上来讲，只要两个对象的内容相同，其地址(hashCode()返回值)以及这两个对象就应该相同(equals())。 
 # ***IO流***
> 流是一组有顺序的，有起点和终点的字节集合，是对数据传输的总称或抽象。即数据在俩设备间的传输称为流，流的本质是数据传输，根据数据传输特征将流抽象为各种类，方便更直观的进行数据操作。

> file类new出的实例并不是说明真正创建一个文件，只是在堆里创建了一个对象


* inputstream，首先在硬盘创建一个文件
```
File file = new File("C://测试文件.txt");
InputStream inputStream = new FileInputStream(file);
byte b[] = new byte[1024];
inputStream.read(b);
inputStream.close();
System.out.println("读取的内容 ：" + new String(b));
```

-  输出字符
```
File file = new File("C://测试文件.txt");
OutputStream out = new FileOutputStream(file);
String str = "你好，我好，大家好";
byte [] b = str.getBytes();
out.write(b);
```
- 读取控制台输入
```
BufferedReader br = new BufferedReader(new  InputStreamReader(System.in));
```
- 读取单个字符
> read读取单个 readLine读取一行
```
import java.io.*;
 
public class BRRead {
  public static void main(String args[]) throws IOException
  {
    char c;
    // 使用 System.in 创建 BufferedReader 
    BufferedReader br = new BufferedReader(new 
                       InputStreamReader(System.in));
    System.out.println("输入字符, 按下 'q' 键退出。");
    // 读取字符
    do {
       c = (char) br.read();
       System.out.println(c);
    } while(c != 'q');
  }
}
```
```
import java.io.*;
public class BRReadLines {
  public static void main(String args[]) throws IOException
  {
    // 使用 System.in 创建 BufferedReader 
    BufferedReader br = new BufferedReader(new
                            InputStreamReader(System.in));
    String str;
    System.out.println("Enter lines of text.");
    System.out.println("Enter 'end' to quit.");
    do {
       str = br.readLine();
       System.out.println(str);
    } while(!str.equals("end"));
  }
}
```
- Java中的目录
创建目录：
File类中有两个方法可以用来创建文件夹：

> mkdir( )方法创建一个文件夹，成功则返回true，失败则返回false。失败表明File对象指定的路径已经存在，或者由于整个路径还不存在，该文件夹不能被创建。  

> mkdirs()方法创建一个文件夹和它的所有父文件夹。

- 字节流
> * 字符流中的对象融合了编码表，也就是系统默认的编码表。我们的系统一般都是GBK编码。

> * 字符流只用来处理文本数据，字节流用来处理媒体数据。

> * 数据最常见的表现方式是文件，字符流用于操作文件的子类一般是FileReader和FileWriter。
- 字符流
> * 写入文件后必须要用flush()刷新。

> * 用完流后记得要关闭流

> * 使用流对象要抛出IO异常

 

> * 定义文件路径时，可以用“/”或者“\\”。

> * 在创建一个文件时，如果目录下有同名文件将被覆盖。

> * 在读取文件时，必须保证该文件已存在，否则出异常

```
import java.io.*;  
  
  
class BufferedWriterDemo {  
    public static void main(String[] args)  throws IOException {  
  
        //创建一个字符写入流对象  
        FileWriter fw = new FileWriter("buf.txt");  
  
        //为了提高字符写入效率，加入了缓冲技术。  
        //只要将需要被提高效率的流作为参数传递给缓冲区的构造函数即可  
        BufferedWriter bfw = new BufferedWriter(fw);  
  
        //bfw.write("abc\r\nde");  
        //bfw.newLine();               这行代码等价于bfw.write("\r\n"),相当于一个跨平台的换行符  
        //用到缓冲区就必须要刷新  
        for(int x = 1; x < 5; x++) {  
            bfw.write("abc");  
            bfw.newLine();                  //java提供了一个跨平台的换行符newLine();  
            bfw.flush();  
        }  
  
  
  
        bfw.flush();                                                //刷新缓冲区  
        bfw.close();                                                //关闭缓冲区，但是必须要先刷新  
  
        //注意，关闭缓冲区就是在关闭缓冲中的流对象  
        fw.close();                                                 //关闭输入流对象  
  
    }  
}  
```
