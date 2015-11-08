## 项目需求 ##

**项目名称：快递查询App**

**项目简介：**

为了方便广大网购族及时知晓自己的快递信息，我们开发这个用于快递查询的APP。
系统基于网络查询服务端，获取服务端的关于快递的最新信息，还可以根据用户的位置进行定位周围的快递站点，预约快递员机型快递的邮寄……

**项目功能：**

* 用户注册和登陆
* 查询快递
* 预约快递（邮寄快递）

**具体功能：**

1. 用户注册：
	* 若是新用户则注册
	* 注册成功
	* 进行登录
2. 用户登录：
	* 拥有账号，进行登录
	* 登陆成功，进入系统主页面
	* 在主页面内进行相关业务的操作
3. 查询快递：
	* 选择快递公司
	* 输入运单号（扫描条形码）
	* 查询按钮进行查询
	* 若有该订单，跳转到详细信息页面
	* 在信息页面进行详细信息的显示
	* 若没有该订单，则Toast提示：快递公司选择有误或订单号有误，请重新进行选择和输入
4. 预约快递：
	* 定位当前地点
	* 输入目的地，系统自动计算估计费用和可以承接的快递公司站点和快递员
	* 根据当前地点查询周边的快递公司站点和相关负责人及负责收派范围
	* 可以点击进入详情页面，查询相关电话进行电话预约

**系统结构图：**

![系统结构图.png](http://upload-images.jianshu.io/upload_images/596839-96544eb3a3f30538.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**页面设计**

1. 欢迎页面：
	* 只在第一次打开应用时显示
	* 三秒之后自动进入系统登出界面
2. 登录界面：
	* 两个EditText，等待输入用户名和密码
	* 一个TextView蓝色文本提示用户，新用户请注册，具有点击跳转事件
	* 若点击跳转到注册界面
	* 一个Button，点击登陆
	* 登陆成功，跳转到系统主页面（查询界面）
3. 注册界面：
	* 个EditText等待输入用户个人信息
	* 一个Button，输入完毕点击按钮注册
	* 注册成功，返回到登陆界面，用户名和密码自动补全
	* 点击登陆按钮，登陆
4. 查询页面：
	* 两个EditText，一个不可输入可点击跳转到快递公司选择页面进行公司选择<br>一个可输入快递单号也可点击右上角扫描条形码图标，进行扫描查询
	* 一个Button，点击进行查询操作
	* 点击后，查询成功跳转到详情页面
5. 快递公司选择页面：
	* 一个ListView，显示所有的快递公司简单信息和Logo标志
	* 点击Item，表示选择该公司，并跳转到查询界面
	* 点击查询按钮，进行查询操作
6. 快递信息详情页面：
	* 一个ListView显示快递详情


7. 系统主页面：
	* **细节内容：**底部actionbar进行不同页面的选择（查询页面、预约页面）



#### 创建的包和类以及接口 ####
1. Entity包
	* 封装用户的实体类：User
2. adapter包
	* 适配Listview的Adapter类：MyAdapter
3. http包
	* 进行网络访问的接口：HttpGetDataInterface
4. Util包
	* 封装各种常量的工具接口：ToolInterface
5. Activity包
    * 呈现界面的各个Activity类：WelcomeActivity、LoginActivity、<br>RegisterActivity、ResearchActivity、CompanyChooseActivity<br>、DetialInformationActivity

#### 大致界面呈现 ####
![welcome.png](http://upload-images.jianshu.io/upload_images/596839-6796459a777286de.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![login.png](http://upload-images.jianshu.io/upload_images/596839-22b3f60a232ba2d0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![register.png](http://upload-images.jianshu.io/upload_images/596839-dd9d5615f793aeab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![search.png](http://upload-images.jianshu.io/upload_images/596839-fc37315d297a6065.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![choosecompany.png](http://upload-images.jianshu.io/upload_images/596839-8a617fa9df80a78f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![detial.png](http://upload-images.jianshu.io/upload_images/596839-a149c53164fb76a4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



### 快递查询项目分工 ###

**模块划分：**

* UI界面模块（界面呈现和数据加载：xml文件的制作、还有adapter的适配）
* 各种业务逻辑模块（登陆、注册、查询、预约：最后实现，因为涉及到地图定位）
* 数据获取和解析模块（联网、解析json）
* 数据保存本地（SQL模块）
* 实体类、工具接口封装模块

****

**具体分工：**

* UI模块涉及到的知识：布局、自定义适配器
* Biz业务模块：涉及到各种逻辑的分析和判断以及数据的辨识
* Http、Parser模块：调用第三方Sdk，联网，解析json，数据的封装
* 数据库操作：建数据库，写SQL语句，执行增删改查操作
* 构建需要的各个实体类，工具接口的创建

****

**类图讲解:**

* **View包**：
<br>**LogoActivity**:启动应用的欢迎界面，三秒后自动跳转到登陆界面
<br>L**oginActivity**:登陆界面，提示用户注册或登陆操作，已有用户输入用户名和密码，点击登录按钮，登录到应用主界面，新用户，点击提示文本跳转到注册界面
<br>**RegisterActivity**:注册界面，填写个人信息，点击注册，注册成功自动跳转到登陆界面，用户名及密码自动填写，点击登录，进入系统主界面
<br>**MainActivity**:应用主界面，底部通过RadioGroup和RadioButton实现页面的切换，页面使用ViewPager加载Fragment，分别显示“我的”、“查询”、“预约”三个界面，ViewPager要实现滑动切屏的效果和与底部RadioButton联动效果
<br>**CompanyChooseActivity**:通过GridView加载网络上获取的所有快递公司信息，通过点击选择公司返回到查询界面
<br>**DetialActivity**:通过ListView呈现查询到的订单信息，消除点击事件，只可以滑动查看

* **Adapter包**：
<br>**MyRecordFragmentAdapter、MySearchFragmentAdapter、MyOrderFragmentAdapter**：三个FragmentAdapter类适配应用主页面里三个不同的布局界面；要继承FragmentAdapter
<br>**SearchCompanyAdapter**:适配查询到的物流公司信息；继承BaseAdapter
<br>**DetialShowAdapter**:适配查询到的订单详情信息；继承BaseAdapter
* **Util包**：
<br>**DBHelper**:数据库辅助类，通过此类封装一些方法进行数据库操作（数据的增删改查更新）
<br>**Const**:工具接口，封装一些常量值

* **Parser包**：
<br>**ParserCompany**:解析从网络获得的公司数据（json字符串）
<br>**ParserInformation**:解析从网络获得的订单详情数据（json字符串）

* **Fragment包**：
<br>**RecordFragment、SearchFragment、OrderFragment**:三个Fragment，分别显示应用主界面里的三个子界面

* **Entity包**：
<br>**User、Company、Information、Number、Mark**:五个实体类，分别封装用户信息、快递公司信息、订单信息、订单号、具体快递详情信息

* **Http包**:
<br>**HttpGetDataInterface**:一个联网获得数据的接口

* **Biz包**：
<br>封装所有的逻辑操作，**RegisterBiz、LoginBiz、SearchBiz、OrderBiz、SaveDataBiz、GetLocalDataBiz、GetNetDataBiz**

* **Application包**：
<br>**MyApplication**:初始化第三方SDK
****
