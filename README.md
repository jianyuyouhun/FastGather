# FastGather

集合各个框架形成开发基础库，方便快速创建项目

## How to get it into your project:

### Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
### Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.jianyuyouhun:FastGather:1.2'
	}

## 集成情况 ##

*	简单的控制器-界面分离设计（manager和activity/fragment）
*	[OkRequester](https://github.com/jianyuyouhun/OkRequester)，对okhttp的简单封装，将请求处理和结果处理进行解耦
*	[KotterKnife](https://github.com/JakeWharton/kotterknife)及其扩展（**自定义的一些类中使用lazy策略进行懒加载注入**）

#### to be continued
