# Easy-MbpEnhance

## 快速搭建后端，专注业务逻辑，增删改查更简单

## 能力

- ### 自动化实体类关联查询

    - 一对一
    - 一对多
    - 多对多

    

- ### 自动化实体类依赖检查

    - 一对一

        - 反向指针检查：当被其他实体类引用时提示错误信息

    - 一对多，多对多：

        - 正向指针检查，当某一实体类中依赖其他实体类，则检查依赖记录是否存在

        

- ### 统一实体类构建

    - 必须添加`TableName`注解来指定表名
    - 可以选择继承`BaseEntity`来获得包含id，添加修改删除时间，操作人等字段，简化实体类冗余代码

    

- ### 提供更简洁的Service调用Api

    - 业务类继承 `EntityService<Mapper, Entity> `类，不需要编写业务接口
        - 如果实体类未继承`BaseEntity`，则业务类可以选择继承`CommonService<Mapper, Entity>`
    - 依赖检查全自动化，不需要显式编码



- ### 自定义检查类型

    - 编写一个bean，继承DependencyChecker接口，重写里面的check方法

    - ```java
        /**
         * @author qcqcqc
         * @date 2024/03
         * @time 15-16-49
         */
        @Component
        @RequiredArgsConstructor
        public class ProductDependencyCheck implements DependencyChecker {
            private final ProductPreDefineRelaMapper productPreDefineRelaMapper;
        
            @Override
            public void check(Long id) throws ServiceException {
                doCheck(productPreDefineRelaMapper, new LambdaQueryWrapper<ProductPreDefineRela>().eq(ProductPreDefineRela::getProductId, id), "该商品存在于套装模板中");
            }
        
            @Override
            public void next() {
        
            }
        }
        ```

    - 如这段代码所示，可以这样进行调用：

    - ```java
            public void checkAndDelete(Long id) {
                // 检查是否存在依赖
                CheckHandler.doCheck(ProductDependencyCheck.class, id);
                this.removeById(id);
            }
        ```



- ### 日志能力

    - 提供@OperationLog注解，传入spel表达式，可以对方法调用进行日志记录
        - 为了更好的开发体验，可以安装SpEL Assistant插件
        - 依赖于Spel表达式，使用前请确保已经掌握spel表达式用法
    - 提供SystemLogger类，静态调用进行日志记录。
        - 多种日志级别，多种方法



- ### Redis服务能力

    - 基础Redis数据结构的增删改查
    - 对Redis Key事件进行订阅
        - 继承`KeyDeleteObserver|KeyExpiredObserver|KeyUpdateObserver`
            - 重写listenerKey方法，返回正则表达式，匹配需要订阅的Key名称
            - 重写onMessage方法，监听这个Key的`删除|过期|更新`动作

    

- ### 自动化建表

    - 当实体类编写完毕，则可以配置自动化建表，一键创建数据库

    - ```yaml
        mbp-enhance:
          debug: true
          generator:
            enable: true
            table:
            #  在启动时自动创建表，仅在开发环境使用，最好只在第一次启动时使用
              on-boot: true
            #  在启动时自动删除已有表，请谨慎使用
              drop-table: true
        ```

- etc.....

