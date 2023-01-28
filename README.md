# 概要
KotlinでgRPCサーバーおよびクライアント実装を行うサンプル。  
Gradleのマルチプロジェクト構成としている。

# 目次
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
|  `-- build.gradle.kts
|
`-- stub
   |-- src/main/proto　：protoファイル置き場
   `-- build.gradle.kts ：protocおよびkotlinコード生成のタスク設定
```

# 自己証明作成手順
https://zenn.dev/jeffi7/articles/10f7b12d6044ad  
上記に従い以下を作成する。  
- ルート証明書：ca.crt
  - clientサブプロジェクトで使用
  - `TlsChannelCredentials#trustManager()` で指定
- サーバー証明書：server.crt
  - serverサブプロジェクトで使用
  - `ServerBuilder` で使用
- サーバー秘密鍵：server.pem(PKCS8形式)
  - serverサブプロジェクトで使用
  - `ServerBuilder` で使用
