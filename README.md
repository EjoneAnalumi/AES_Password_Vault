# Password Manager

Ky është një projekt i punuar nga Edonita Gashi dhe Ejone Analumi, studente të vitit të dytë të Universitetit "Hasan Prishtina" - Fakulteti i Inxhinierisë Elektrike dhe Kompjuterike, në lëndën "Data Security" - Prof. Blerim Rexha dhe Asc. Mergim Hoti.

## Tema e projektit

Shkruani një aplikacion për menaxhimin e fjalëkalimeve duke përdorur çfarëdo gjuhe programuese që ruan në mënyrë të sigurt kredencialet e përdoruesit duke përdorur enkriptimin **AES**.

Aplikacioni duhet të përfshijë veçori për:
- Gjenerimin e fjalëkalimeve të forta
- Organizimin e fjalëkalimeve në kategori
- Sinkronizimin e të dhënave të enkriptuara nëpër pajisje të shumta (opsionalisht)

## Përshkrimi i projektit

Ky projekt mundëson menaxhimin e fjalëkalimeve për përdorues të ndryshëm përmes:

- Regjistrimit dhe kyçjes së përdoruesve me fjalëkalim master të hash-uar
- Gjenerimit të fjalëkalimeve të forta automatikisht
- Ruajtjes së të gjitha fjalëkalimeve të enkriptuara me algoritmin **AES** (Advanced Encryption Standard)
- Organizimit të fjalëkalimeve sipas kategorive/llogarive
- Sinkronizimit manual të file-ve të enkriptuara për përdorim në pajisje të ndryshme

Të gjitha fjalëkalimet ruhen vetëm të enkriptuara dhe dekriptohen vetëm kur përdoruesi është i kyçur.

## Përmbajtja e kodit

**MainApp.java**  
Starton aplikacionin dhe ngarkon ndërfaqen kryesore të login-it.

**LoginController.java**  
Menaxhon regjistrimin dhe kyçjen e përdoruesve.

**MainController.java**  
Mundëson shtimin, ruajtjen, fshirjen, dhe gjenerimin e fjalëkalimeve, organizimin sipas kategorive, dhe kopjimin e tyre.

**AESEncryption.java**  
Implementon enkriptimin dhe dekriptimin me AES.

**DatabaseManager.java**  
Menaxhon ruajtjen e përdoruesve dhe të fjalëkalimeve të enkriptuara në file CSV.

**PasswordGenerator.java**  
Gjeneron fjalëkalime të forta sipas kritereve të ndryshme.

**PasswordEntry.java / User.java**  
Përfaqësojnë strukturën e të dhënave për fjalëkalimet dhe përdoruesit.

## Teknologjitë dhe Veglat e përdorura

- Java SE 17+
- JavaFX për ndërfaqen grafike
- AES për enkriptimin e të dhënave
- PBKDF2 për hashimin e fjalëkalimit master
- Ruajtje në file lokale (CSV)

## Si u testua aplikacioni

Aplikacioni është testuar duke:
- Krijuar përdorues të ndryshëm dhe duke u kyçur me sukses
- Shtuar, ruajtur dhe fshirë fjalëkalime për llogari të ndryshme
- Gjeneruar fjalëkalime të forta dhe kontrolluar enkriptimin/dekriptimin me AES
- Kontrolluar që të gjitha të dhënat ruhen të enkriptuara në file dhe dekriptohen vetëm me çelësin e përdoruesit
- Sinkronizuar manualisht file-t e enkriptuara për përdorim në kompjuter tjetër

## Udhëzime për nisje

1. Hap projektin në IntelliJ IDEA ose ndonjë IDE tjetër për Java.
2. Run MainApp.java.
3. Regjistrohu ose kyçu me përdorues ekzistues.
4. Shto, ruaj, fshij ose kopjo fjalëkalime sipas nevojës.

## Përfundim

Projekti është realizuar me sukses duke siguruar ruajtje të enkriptuar të fjalëkalimeve me AES, gjenerim të fjalëkalimeve të forta dhe organizim të qartë të kredencialeve për përdorues të ndryshëm.

---

> Ky projekt është zhvilluar si pjesë e lëndës **Data Security**.
