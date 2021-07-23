# Qool 宇宙極超級無敵插件開發工具
聽起來很中二對吧，但你管我 😂👌
Discord: https://discord.qool.dev  如果你有任何建議歡迎告訴我們

## 功能
### 符號意義
🟢 代表已經完成
🔴 代表計畫製作，尚未完成
🟡 代表正在製作

### 清單
🟡卸載和載入皆使用Dynamic Plugin Loading Technique (類似Plugman，總之不會使用/reload)
🟡生成Ant檔案，並在Pre Processing時卸載插件，在Post Processing時載入插件
🟡HTTP Server，用來接收來自Ant檔案的指令
🟡HotSwap Mode
🟢喝杯咖啡

## 警告
1. 請不要把它放到正式的伺服器，這永遠不會是你這輩子想要嘗試的事情，因為這非常的不安全
2. 因為我們需要使用Reflection做到Illegal Access，所以請確保我們能夠直接Illegal Access，詳情請查看下方 ***Illegal Access 修復***  一段 (我們需要存取URLClassLoader，如果你有更好的辦法，歡迎到Discord聯絡我們)

## Illegal Access 修復
這裡有幾種(目前只有一種)我們已知的方法解決這個問題
首先: 這個問題只存在Java 9以上，並且在JVM有使用參數: --illegal-access=deny 會出錯，而Java16 預設就是 --illegal-access=deny，所以如果你JVM有拒絕Illegal Access，那請把它允許，如果你的Java是16 (因為Minecraft 1.17 開始要求Java16)，請使用以下方法

#### 直接更改伺服器參數
請加入 --illegal-access=permit 和 --add-opens
詳情: https://softwaregarden.dev/en/posts/new-java/illegal-access-in-java-16/#:~:text=In%20Java%2016%20%2D%2Dillegal,no%20more%20with%20the%20defaults.

## 使用
我們都還沒寫完呢，你在期待甚麼呢


## 本人不熟悉Git，我不懂甚麼東西是Pull Request  不要咬我 <br> : >