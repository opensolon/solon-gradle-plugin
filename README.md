
<h1 align="center" style="text-align:center;">
Solon
</h1>
<p align="center">
	<strong>Java 新的生态型应用开发框架，更小、更快、更简单！</strong>
</p>
<p align="center">
	<a href="https://solon.noear.org/">https://solon.noear.org</a>
</p>

<p align="center">
    <a target="_blank" href="https://central.sonatype.com/search?q=org.noear%3Asolon-parent">
        <img src="https://img.shields.io/maven-central/v/org.noear/solon.svg?label=Maven%20Central" alt="Maven" />
    </a>
    <a target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0.txt">
		<img src="https://img.shields.io/:License-Apache2-blue.svg" alt="Apache 2" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html">
		<img src="https://img.shields.io/badge/JDK-8-green.svg" alt="jdk-8" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-11-green.svg" alt="jdk-11" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-17-green.svg" alt="jdk-17" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-21-green.svg" alt="jdk-21" />
	</a>
    <br />
    <a target="_blank" href='https://gitee.com/noear/solon/stargazers'>
		<img src='https://gitee.com/noear/solon/badge/star.svg' alt='gitee star'/>
	</a>
    <a target="_blank" href='https://github.com/noear/solon/stargazers'>
		<img src="https://img.shields.io/github/stars/noear/solon.svg?logo=github" alt="github star"/>
	</a>
</p>

<br/>
<p align="center">
	<a href="https://jq.qq.com/?_wv=1027&k=kjB5JNiC">
	<img src="https://img.shields.io/badge/QQ交流群-22200020-orange"/></a>
</p>

<hr />

启动快 5 ～ 10 倍；qps 高 2～ 3 倍；运行时内存节省 1/3 ~ 1/2；打包可以缩到 1/2 ~ 1/10

<hr />





## solon-gradle-plugin

#### 1.打包插件配置示例

在 `build.gradle` 中使用

```groovy
buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        classpath 'org.noear:solon-gradle-plugin:x.y.z'
      
        // 需要用 native-image 的使用此插件
        // classpath 'org.noear:solon-native-gradle-plugin:x.y.z'
    }
}


// 使用
apply plugin: 'org.noear.solon'

// 导入的是 org.noear:solon-native-gradle-plugin 使用
// apply plugin: 'org.noear.solon.native'



compileJava {
    // 这两个配置可以不添加了，插件中会默认自动添加
    options.encoding = "UTF-8"
    options.compilerArgs << "-parameters"
}

// 配置启动文件名
solon {
    mainClass = "com.example.demo.App"
}

// 也可以针对 jar包和 war包指定不同的 mainClass

solonJar{
    mainClass = "com.example.demo.App"
}

// 使用 solonWar 需要添加 war 插件
solonWar{
    mainClass = "com.example.demo.App"
}

```

在 `build.gradle.kts` 中使用

```kotlin
buildscript {
    repositories {
        mavenLocal()
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        mavenCentral()
    }

    dependencies {
        classpath("org.noear:solon-gradle-plugin:x.y.z")
        // classpath("org.noear:solon-native-gradle-plugin:x.y.z")
    }
}

// 引用插件
apply(plugin = "org.noear.solon")
// apply(plugin = "org.noear.solon.native")

// 统一配置
extensions.configure(org.noear.solon.gradle.dsl.SolonExtension::class.java) {
    mainClass.set("com.example.demo.App")
}

// 单独配置
tasks.withType<org.noear.solon.gradle.tasks.bundling.SolonJar> {
    mainClass.set("com.example.demo.App")
}

tasks.withType<org.noear.solon.gradle.tasks.bundling.SolonWar> {
    mainClass.set("com.example.demo.App")
}

```

#### 2. 构建打包

* `gradle solonJar`
* ~~`gradle solonWar`~~
* `gradle nativeCompile`

#### 3. 更新
* `0.0.2` 
  
  1.  修复未 `build`情况下直接 运行  `gradle solonJar` 报错问题
  
  2. 自动扫描 `main` 方法，但是如果有多个的时候仍然需要手动配置，~~可自行新增注解类`org.noear.solon.annotation.SolonMain`~~(solon 2.2开始自带此类)，将注解添加到启动类上
  
    ```java
    package org.noear.solon.annotation;
    
    import java.lang.annotation.*;
    
    /**
     * Solon 主类（入口类）
     *
     * <pre><code>
     * @SolonMain
     * public class App{
     *     public static void main(String[] args){
     *         Solon.start(App.class, args);
     *     }
     * }
     * </code></pre>
     *
     * @author noear
     * @since 2.2
     * */
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface SolonMain {
    
    }
    
    // 启动类上添加
    
    @SolonMain
    public class App {
        public static void main(String[] args) {
            Solon.start(App.class, args);
        }
    }
    ```
  
**Thanks**
- [SpringBoot-gradle-plugin](https://github.com/spring-projects/spring-boot/tree/main/build-plugin/spring-boot-gradle-plugin)
