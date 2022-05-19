# CryptocurrencyPriceTrackerApp

### Kullanılan Teknolojiler

* Room
* Retrofit, Gson
* Hilt
* Navigation Component
* ViewModel
* Coroutines
* Glide
* FirebaseFirestore, FirebaseAuth
* WorkManager

### Daha Sonra Projeye Dahil Edilecek Teknolojiler
* UseCase
* Flow
* DataBinding
* UI Test
* Unit Test

Favori Coin'in ücreti arkaplanda workmanager kullanılarak periyodik olarak kontrol edilir eğer favorideki coinlerden birinin ücreti değişmiş ise bildirim oluşturuluyor. 

### Giriş Yap Sayfası
* Kullanıcı daha önce giriş yapmış ise Coin Ana sayfasına gönderiliyor. Eğer kullanıcı hesabından çıkış yapmış veya ilk defa giriş yapacak ise ekrandan giriş yapması gerekmektedir. Kullanıcı giriş yap butonuna tıklar ise CoinGecko servisinden coin listesi çekilir ve databasedeki coin tablosu güncellenir.

![](https://github.com/yunusbedir/CryptocurrencyPriceTrackerApp/blob/master/screenshots/login.png)

### Progress

![](https://github.com/yunusbedir/CryptocurrencyPriceTrackerApp/blob/master/screenshots/loading.png)

### Kayıt Ol Sayfası
* Firebase ile kullanıcı kayıt ediliyor.

![](https://github.com/yunusbedir/CryptocurrencyPriceTrackerApp/blob/master/screenshots/register.png)


### Parolamaı Unuttum Sayfası
* Fİrebase ile kulllanıcının parolasını sıfırlaması için email gönderiliyor.

![](https://github.com/yunusbedir/CryptocurrencyPriceTrackerApp/blob/master/screenshots/forgot.png)


### Coin Ana Sayfası 
* Databasedeki coin tablosundan beslenerek ekranda coinler listelenir. Arama alanında yapılan her bir değişiklikte databasedeki coin tablosu symbol ve name alanlarına göre filtrelenir. 

![](https://github.com/yunusbedir/CryptocurrencyPriceTrackerApp/blob/master/screenshots/markets.png)


### Coin Detay Sayfası 
* Coin Ana Sayfasından gelen coin id'si ile beraber CoinGecko servisine coin detayları için istek atılır ve ekrandaki alanlar doldurulur. Ekranda favori ikonu ise FirebaseFirestore da coin id'si ile arama yapılır eğer FirebaseFirestore da var ise favorilere eklenmiştir ve ikon değişir. 
* Kullanıcı favori ikonuna bastığında eğer coin favorilerde değilse FirebaseFireStore eklenir, eğer coin favorilerde ise FirebaseFirestore dan coin silinir.
* Detay sayfasında mevcut ücret değeri 10 sanıyede bir servise istek atılıp kontrol edilir ve güncelleme yapılır.

![](https://github.com/yunusbedir/CryptocurrencyPriceTrackerApp/blob/master/screenshots/detail.png)


### Favoriler Sayfası 
* Favoriler firebaseFirestore' a eklenen favorileri çekip ekranda gösteriliyor.

![](https://github.com/yunusbedir/CryptocurrencyPriceTrackerApp/blob/master/screenshots/favorites.png)


### Kullanıcı Sayfası 
* Kullanıcın oturumunu sonlandıracağı ekrandır.

![](https://github.com/yunusbedir/CryptocurrencyPriceTrackerApp/blob/master/screenshots/user.png)




