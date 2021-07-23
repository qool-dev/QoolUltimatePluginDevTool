# 🛰️Qool 宇宙極超級無敵插件開發工具🛰️
聽起來很中二對吧，但你管我 😂👌<br>
Discord: https://discord.qool.dev  如果你有任何建議歡迎告訴我<br>
(才過一天，此專案已經不再維護 lol，這個比我想像得還來得輕便，所以裡面的程式碼很亂是正常的)<br>
(這個大約400行就能完成 : >)
## 📄專案資訊:
作者: fan87<br>
Discord: fan87#0774<br>
發布日期: 7/23/2021<br>
公開日期: 7/23/2021<br>
註解: Logo今天翹班，抓.w.
## ⚠️需要Plugman⚠️
## ⚠️需要Plugman⚠️
## ⚠️需要Plugman⚠️
重要的事情說三遍 - 弗里德里希·尼采
## ⛏️功能
### 符號意義
🟢 代表已經完成<br>
🔴 代表計畫製作，尚未完成<br>
🟡 代表正在製作<br>

### 清單
🟢卸載和載入皆使用Dynamic Plugin Loading Technique (類似Plugman，總之不會使用/reload)<br>
🟢支援Maven，因此你可以在Pre Processing時卸載插件，在Post Processing時載入插件<br>
🟢HTTP Server，用來接收來自Pre Processing and Post Processing的指令<br>
🔴 HotSwap Mode<br>
🟢送你一杯咖啡不加咖啡<br>

### 完整清單: https://github.com/qool-dev/QoolUltimatePluginDevTool/projects

## ⚠️警告
1. 請不要把它放到正式的伺服器，這永遠不會是你這輩子想要嘗試的事情，因為這非常的不安全<br>
2. 因為我們需要使用Reflection做到Illegal Access，所以請確保我們能夠直接Illegal Access，詳情請查看下方 ***Illegal Access 修復***  一段 (我們需要存取URLClassLoader，如果你有更好的辦法，歡迎到Discord聯絡我們)<br>

## 🛠️Illegal Access 修復
這裡有幾種(目前只有一種)我們已知的方法解決這個問題<br>
首先: 這個問題只存在Java 9以上，並且在JVM有使用參數: --illegal-access=deny 會出錯，而Java16 預設就是 --illegal-access=deny，所以如果你JVM有拒絕Illegal Access，那請把它允許，如果你的Java是16 (因為Minecraft 1.17 開始要求Java16)，請使用以下方法<br>

#### 直接更改伺服器參數
請加入 --illegal-access=permit 和 --add-opens<br>
詳情: https://softwaregarden.dev/en/posts/new-java/illegal-access-in-java-16/#:~:text=In%20Java%2016%20%2D%2Dillegal,no%20more%20with%20the%20defaults.<br>

## 🤗使用
Maven 插件範例使用方式:
```xml
<plugin>
    <groupId>dev.qool</groupId>
    <artifactId>QoolUltimatePluginDevTool-Maven</artifactId>
    <version>1.0</version>
    <executions>
    <execution>
        <id>prepare</id>
        <phase>pre-clean</phase>
        <goals>
            <goal>prepare</goal>
        </goals>
    </execution>
    <execution>
        <id>finish</id>
        <phase>package</phase>
        <goals>
            <goal>finish</goal>
        </goals>
    </execution>
    </executions>
    <configuration>
        <pluginName>plugin.yml裡面的插件名稱</pluginName>
        <path>[你的伺服器位置]plugins\輸出檔案.jar</path>
        <originalPath>${project.basedir}\target\輸出檔案.jar</originalPath>
    </configuration>
</plugin>
```
Configuration裡面可以有以下兩個參數，但是不是必需的:

1. host (你Minecraft伺服器的Host，如果不是Localhost記得在Spigot Server/QoolUltimatePluginDevTool/config.yml 更改成0.0.0.0)，預設為localhost
2. port (在Spigot/QoolUltimatePluginDevTool/config.yml裡面的port)，預設為42753 (你絕對猜不對為何我會用這個數字lol，這個數字對我有一定意義 ~~我絕對不會說我本來要打6969~~)

接著只要clean package就可以了<br>

### 🎉EZ🎉

### ⬇️安裝方式
最直接的方式就是用IntelliJ IDEA打開QoolUltimatePluginDevTool-Maven的pom.xml，然後Build一次，照理來說應該就安裝完成了<br>
接著就是安裝Spigot插件，相信你應該很了解，而Github Release應該會有最新版本，你可以直接安裝<br><br>
***⚠️你會需要Plugman!!!!!!!!!!!!!!!!!!⚠️***



## 本人不熟悉Git，我不懂甚麼東西是Pull Request  不要咬我 <br> 🤗
## 本人第一次寫Maven插件   不要咬我.w.