Learning Time Keeper
====================
学习时间记录者（如果有做得不好的地方，还请见谅）

Introduction
------------
Learning Time Keeper is an app for recording learning time for each day.
You can record a start and stop time, assign a quality rating, and clear the database.

This project includes:
1. How to use RecycleView and optimize it with DiffUtil；
2. How to use simple coroutine for thread management；
3. How to use Room DAO as a backend repository with coroutine and LiveData；
4. How to use data binding (and BindingAdapter) to make a project conform to the MVVM architecture；
5. Singleton Pattern, Simple Factory Pattern, Adapter Pattern and related Design principles.
 
There are a lot of comments added to the project, which may have an impact on the readability of the code, 
but the purpose is to help some learners like me learn Kotlin Android development better. 
Sincerely hope this project could help more people. Cheers.

Learning Time Keeper是一款记录每天学习时间的应用程序。
您可以记录启动和停止时间，分配质量评级，并清除数据库。

这个项目设计的技术栈包括:
1. 如何使用RecycleView以及如何用DiffUtil对其进行优化；
2. 如何使用简单的协程进行线程管理；
3. 如何使用Room DAO和LiveDate，coroutine配合使用来作为后端存储库；
4. 如何使用数据绑定(以及BindingAdapter)使项目符合MVVM架构；
5. 单例模式，工厂模式，适配器模式的实际运用和相应的设计原则。

项目中添加了很多注释，这些注释可能会影响代码的可读性。
但这么做是为了帮助像我这样记性不好的同学更好地学习Kotlin Android开发。
希望这个项目能帮助更多的人。Cheers～！

References
------------
这个项目参考了谷歌官方的代码实验室和UdaCity的视频课程。
非常感谢他们给予了我做这个项目所需的灵感和支持。
以下是相关链接，以供参考和学习帮助。

[Kotlin CodeLab](https://developer.android.com/codelabs/kotlin-bootcamp-introduction#0)

[Kotlin Android Basic CodeLab](https://developer.android.com/courses/android-basics-kotlin/course)

[Kotlin Android Fundamentals CodeLab](https://developer.android.com/courses/kotlin-android-fundamentals/toc)

[Coroutine CodeLab](https://developer.android.com/codelabs/kotlin-coroutines#0)

[UdaCity Video](https://www.udacity.com/course/developing-android-apps-with-kotlin--ud9012)

代码的注释中详细解释了使用的设计模式设计原则，如果你希望了解更多重构技巧和设计模式，设计原则可以参考Linkedin Learning的视频课程，RefactoringRuru网站和Martin Fowler的《重构》。

[Code smell and Design smell](https://www.linkedin.com/learning/software-design-code-and-design-smells/vet-class-level-smells?u=57895809)

[Refactoring](https://www.linkedin.com/learning/agile-software-development-refactoring/refactoring-for-better-code?u=57895809)

[Design Patterns](https://www.linkedin.com/learning/programming-foundations-design-patterns-2/don-t-reinvent-the-wheel?u=57895809)

[Design Principles](https://www.linkedin.com/learning/advanced-design-patterns-design-principles/take-your-design-to-the-next-level?u=57895809)

[refactoringGuru](https://refactoring.guru/)

[Martin Fowler's book "Refactoring"](https://refactoring.com/)

如果想进一步了解我的学习路线可以参考我的Notion博客链接，里面收集和整理了基础知识，同时记录了我最近在看的书《Android开发艺术探索》

（注：需使用VPN才能登陆Notion）

[My Notion Blog](https://www.notion.so/3c83d733b7b84ad0aac45a75994cc394?v=83158c7e5f354a4383e2a59b445605a4)

ScreenShot & Usage
------------
主页面记录着之前的数据，底部的清楚数据按钮可以清除所有数据

![Image description](https://github.com/LiamDai/Learning-Time-Keeper/blob/master/ScreenShot/begining.png)

点击开始记录后会开始计时

![Image description](https://github.com/LiamDai/Learning-Time-Keeper/blob/master/ScreenShot/afterClickStart.png)

点击结束计时后会跳转到打卡页面，选择多种状态

![Image description](https://github.com/LiamDai/Learning-Time-Keeper/blob/master/ScreenShot/afterClickEnd.png)

在主页面点击每个小图片都可以进入详细信息页面

![Image description](https://github.com/LiamDai/Learning-Time-Keeper/blob/master/ScreenShot/detail.png)

This project is for personal study only, not for other purposes. 
If you need to forward it, please indicate the source.

此项目仅供个人学习使用，不可用于其它用途，如需转发请注明出处。



