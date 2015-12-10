# CurrencyConverter
[Tokushima.app #6](http://tokushima-app.connpass.com/event/23744/) ハンズオン用アプリ

## 目的
* 通信・DB ライブラリを使って、簡単な Android アプリを作ってみる

## 機能概要
* 100$ → ???円 などの計算ができる
* 為替 API を使って、現在の為替で計算する
 * http://api.aoikujira.com/kawase/
* DB にキャッシュして、最近取得した場合は API アクセスしない

## 使っているライブラリ
* ButterKnife : <http://jakewharton.github.io/butterknife/>
* Retrofit : <http://square.github.io/retrofit/>
 * OkHttp : <http://square.github.io/okhttp/>
 * RxAndroid : <https://github.com/ReactiveX/RxAndroid>
* Realm : <https://realm.io/jp/>
* Stetho : <http://facebook.github.io/stetho/>
 * Stetho-OkHttp : <https://github.com/facebook/stetho/tree/master/stetho-okhttp>
 * Stetho-Realm : <https://github.com/uPhyca/stetho-realm>
 
