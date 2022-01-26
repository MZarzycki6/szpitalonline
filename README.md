# Aplikacja dla klientów szpitala

## Dane
adres: https://szpitalonlinezm.herokuapp.com

hmac generator: https://www.devglan.com/online-tools/hmac-sha256-online

dane logowania przez basic auth: header AUTHORIZATION, login "fajnylogin", hasło "trudnehaslo"
Basic ZmFqbnlsb2dpbjp0cnVkbmVoYXNsbw==

hmac key "123456"

## Funkcje
Wyświetlanie dostępnych terminów GET /dateslist

Rezerwacja terminu POST /dateslist (data w formacie plaintext w body), konieczność dodania headera X-HMAC-SIGNATURE wraz z treścią body zakodowaną z kluczem "123456" celem weryfikacji integralnosci

wyświetlanie wyników badań pacjentów GET /labresults/{name}, w zamockowano w celach testowych wyniki dla "JanKowalski", "PawelNowak" oraz "AnnaPrzykladowa"


