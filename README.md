# 概要
Gradleのマルチプロジェクト構成で、KotlinによるgRPCサーバーおよびクライアント実装を行うサンプル。  
自己署名だがSSL対応済み。  

# 目次
- [構成](#構成)
- [動作確認手順](#動作確認手順)
- [自己証明作成手順](#自己証明作成手順)


# 構成
プロジェクトの構成は以下の通り。  
```text
grpc-kotlin
|-- client ：gRPCのクライアント実装。stubサブプロジェクトに依存。
|  |-- src/main
|  |   |-- kotlin ： 組み込みNettyで実装
|  |   `-- resources ：独自CA証明書を置いている
|  `-- build.gradle.kts
|
|-- server ：gRPCサーバー実装。stubサブプロジェクトに依存。
|  |-- src/main
|  |   |-- kotlin ：組み込みNettyで実装
|  |   `-- resources ：自己署名したSSL証明書および秘密鍵を置いている
|  `-- build.gradle.kts : fat.jarの設定も含む
|
`-- stub
   |-- src/main/proto　：protoファイル置き場
   `-- build.gradle.kts ：protocおよびkotlinコード生成のタスク設定
```

# 動作確認手順
1. 後述自己証明作成手順を完了している。(各証明書および鍵をserver, clientコード中のファイル名と合わせる)
2. プロジェクトルートで以下を実行
    ```shell
    $ ./gradlew build
    ```
3. serverを起動
    ```shell
    # 以下いずれかで起動「Server started, listening on 8443」がログ出力されればOK
   
    # Gradleで起動する場合
    $ ./gradlew :server:run
    # fat.jarとして起動したい場合
    $ java -jar server/build/libs/server.jar
    ```
4. clientを起動し通信できることを確認
    ```shell
    # 「Hello kanetugu Reply!」がログ出力されればOK
    $ ./gradlew :client:run
    ```

# 自己証明作成手順
https://zenn.dev/jeffi7/articles/10f7b12d6044ad  
上記を参考にさせてもらった。  
本サンプル実行に必要なものは以下の通り。  

- ルート証明書：ca.crt
    - clientサブプロジェクトで使用
    - `TlsChannelCredentials#trustManager()` で指定
- サーバー証明書：server.crt
    - serverサブプロジェクトで使用
    - `ServerBuilder` で使用
- サーバー秘密鍵：server.pem(Javaで読めるようにPKCS8形式に変換すること)
    - serverサブプロジェクトで使用
    - `ServerBuilder` で使用
