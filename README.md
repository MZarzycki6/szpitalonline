# Aplikacja dla klientów szpitala

## Założenia teoretyczne
Github projektu dostępny pod adresem https://github.com/MZarzycki6/szpitalonline

Aplikacja ma symulować interakcję pacjenta z wewnętrznym systemem szpitala, z którego pobierałaby dane dotyczące terminów wizyt oraz wyników badań i umożliwiałaby ich wyświetlenie i interakcję z wewnętrznym systemem szpitala poprzez rezerwację wybranej date sposód listy wyślwietlonych wcześniej dostępnych dat.

## Dane
adres: https://szpitalonlinezm.herokuapp.com

hmac generator: https://www.devglan.com/online-tools/hmac-sha256-online

dane logowania przez basic auth: header Authorization, login "fajnylogin", hasło "trudnehaslo"
Basic ZmFqbnlsb2dpbjp0cnVkbmVoYXNsbw==

hmac key "123456"

## Funkcje
Wyświetlanie dostępnych terminów GET /dateslist

Rezerwacja terminu POST /dateslist (data w formacie plaintext w body jako np. 2000-01-01), konieczność dodania headera X-HMAC-SIGNATURE wraz z treścią body zakodowaną z kluczem "123456" celem weryfikacji integralnosci

wyświetlanie wyników badań pacjentów GET /labresults/{name}, w zamockowano w celach testowych wyniki dla "JanKowalski", "PawelNowak" oraz "AnnaPrzykladowa"


