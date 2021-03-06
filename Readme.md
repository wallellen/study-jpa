ORM入门（JPA实现）
------------------

 

1. 基础：
---------

### 1.1 从JDBC到ORM：

-   JDBC解决的问题及其优劣

-   ORM解决的问题及其优劣

 

### 1.2 配置JPA：

>   **基本配置：**

-   创建 `EntityManagerFactory`

-   创建 `EntityManager`

 

>   **进阶配置：**

-   使用 `persistence.xml` 

-   `EntityManagerFactory` 的单例模式

-   使用 `ThreadLocal` 管理 `EntityManager`

 

### 1.3 实体

>   **实体，实体属性及其注解**

-   `@Entity` 注解：标注一个实体

-   `@Table` 注解：设置实体所对应的数据表

    -   `@SecondaryTable` / `@SecondaryTables`：设置协同保存数据的其它数据表

-   `@Id` 注解：设置主键

    -   `@GeneratedValue` 注解：设置主键生成规则

        -   `IDENTITY`：利用数据表的字段自增规则产生主键

        -   `SEQUENCE`：利用数据库的自增序列对象产生主键

        -   `TABLE`：利用另一张数据表的字段值产生主键

        -   `AUTO`：主键生成规则由数据库维护，JPA不关心

-   `@Basic` / `@Column` 注解：设置实体字段和数据表字段的对应法则

    -   `name`：设置数据表字段名称 (String)

    -   `insertable` / `updateable`：设置字段是否可以被插入和更新 (Boolean)

    -   `length`：设置字段的长度（字符串类型）(Integer)

    -   `unique`：设置字段的唯一约束 (Boolean)

    -   `nullable`：设置字段的非空约束 (Boolean)

    -   `precision` / `scale`：设置decimal类型字段的精度和小数位   (Integer)

-   `@Transient`：瞬时字段（不进行持久化）

-   `@Lob`：大字段

-   `@Enumerated`：枚举类型字段

 

>   **实体的状态**

-   JPA的的作用是将实体对象在以下四个状态中转换，一个实体对象在一瞬间必然处于下述四个状态之一。在JPA事务结束前，所有『受控态』对象会被同步到数据表；所有『删除态』对象会从数据表中删除

    -   new（新建态）：通过Java的new创建的对象，该对象无主键

    -   managed（受控态）：受JPA实体容器管理的对象，该对象拥有主键

    -   detached（游离态）：拥有主键但不受JPA容器管理（前一个事务获取的对象或通过编程设置的主键值）

    -   removed（删除态）：对象之前在JPA容器中，但已经被移除

-   JPA基本操作：

    -   `persist`：将 new / removed 状态的对象转为 managed 状态，对于 managed
        状态对象不做操作

    -   `merge`：将 new / detached 状态的对象转为 managed 状态，对于 managed
        状态对象做update操作

    -   `remove`：将 manage 状态的对象转为 removed 状态

    -   `find`：通过主键值获取一个 managed 状态的实体对象

    -   `flush`操作：提交事物中所有实体的状态

     

### 1.4 事务

>   JPA事务和JDBC事务的区别：

 

>   JPA事务传播性：

-   JPA要求：

    -   insert / update / delete 操作必须受事务管理（Mandatory）

    -   select
        操作可以有也可以没有事务（Support），但如果要使用锁，则应该在事务中（Required）

    -   事务必须具备原子性

-   一般情况下JPA建议以线程为单位隔离事务，在同一个线程中，事务会发生合并，即后发生的事务会合并到之前发生的事务中，子事务会合并到父事务中（使用不同的框架，对事务隔离级别的处理也会略有不同，例如
    Spring 允许自定义事务隔离级别，而 Guice 则自动处理这些问题）

 

>   JPA事务隔离级别：

-   问题：对于A、B两个并发事务

    -   脏读：A事务读取了B事务尚未提交的数据

    -   不可重复读：由于B事务的 update 操作，A事务无法重复读取相同的数据

    -   幻读：A事务无法确定读取到的数据

-   隔离级别：

    -   未提交读：最低级别的事务隔离，保证不会发生读取错误；

    -   已提交读：无法读取其它未提交事务产生的数据，避免了脏读，一般是数据库的默认隔离级别

    -   重复读：避免脏读和不可重复读，即无法更新另一个事务尚未提交的数据

    -   序列化：避免幻读，即在另一个事务操作结束前无法操作相同的资源

-   锁以及锁定类型

    -   一般情况：JPA会将锁操作交由数据库来完成，JPA只是启动/提交和回滚事务

    -   特殊情况：JPA支持对查询加锁，以解决更高要求的事务隔离问题

        -   在查询时通过指定锁的类型完成锁定，锁会持续到事务结束：

            -   NONE：不加锁

            -   乐观锁：乐观锁利用版本控制的方法，以最小的性能代价换取大部分时间都可以接受的事务控制

                -   OPTIMISTIC\_FORCE\_INCREMENT

                -   OPTIMISTIC

            -   悲观锁：利用数据库本身的功能加锁（例如 select … from … where …
                for update）

                -   PESSIMISTIC\_WRITE

                -   PESSIMISTIC\_READ

            -   `@Version` 注解：通过在表中添加版本号来保证“读/写”的原子性

-   缓存机制：

    -   一级缓存：一级缓存存在于每个JPA的Session中，和Session的生命周期一致

        -   `clear`操作：清除缓存

    -   二级缓存：

 

### 1.5 关系

>   一对一关系：对于A、B两个实体，如果其互相引用一次，则表现为一对一关系，对应到数据表，则B表需引用A表的主键字段作为外键

    -   `@OneToOne`：

        -   `mappedBy`：

        -   `@JoinColumn`：  
             

>   一对多关系：对于A、B两个实体，如果A实体持有B实体的集合引用，且B实体持有A实体的单一引用，则称为一对多关系，B表需许引用A表的主键字段作为外键

    -   `@OneToMany`：

        -   `mappedBy`：

    -   `@ManyToOne`：

        -   `@JoinColumn`：

     

>   级联操作：`cascade`与` orphanRemoval` 

    -   n + 1问题：

        -   原因：

解决方案：

         

>   多对多关系：

    -   中间关系表：

    -   慎用多对多关系：
