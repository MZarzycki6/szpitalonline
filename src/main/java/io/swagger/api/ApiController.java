package io.swagger.api;


import io.swagger.model.*;
import java.security.*;
import java.time.LocalDate;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;


@Controller
public class ApiController implements DatesListApi,LabResultsApi {

    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
    private final HttpServletRequest request;
    private DatesList DatesList;
    private LabResultsList LabResultsList;
    private String BasicCredentials = "fajnylogin:trudnehaslo"; //dane logowania do basicauth

    @org.springframework.beans.factory.annotation.Autowired
    public ApiController(ObjectMapper objectMapper, HttpServletRequest request) throws NoSuchAlgorithmException {
        this.objectMapper = objectMapper;
        this.request = request;
    }
    //generowanie wstępne jakichś danych w celach eksperymentalnych
    @PostConstruct
    public void datesDataCreator(){
        List<String> DatesArray = new ArrayList<String>();
        List<LabResults> ResultsArray = new ArrayList<LabResults>();

        DatesArray.add("2022-01-12");
        DatesArray.add("2023-12-30");
        DatesArray.add("2023-05-12");
        DatesArray.add("2023-10-07");
        DatesArray.add("2024-12-03");
        DatesArray.add("2023-01-12");
        DatesList = new DatesList(DatesArray);

        LabResults result1 = LabResults.builder().name("JanKowalski").TestDate("2021-12-12").TestType("morfologia").Status("w normie").build();
        ResultsArray.add(result1);
        LabResults result2 = LabResults.builder().name("PawelNowak").TestDate("2019-06-12").TestType("na COVID").Status("pozytywny").build();
        ResultsArray.add(result2);
        LabResults result3 = LabResults.builder().name("AnnaPrzykladowa").TestDate("2017-03-15").TestType("na COVID").Status("negatywny").build();
        ResultsArray.add(result3);
        LabResultsList=new LabResultsList(ResultsArray);
    }
    //wyswietla liste dostepnych dat do rezerwacji
    @Override
    public ResponseEntity<DatesList> getDates(@RequestHeader("Authorization") String Authorization) {
        if(BasicAuth(Authorization)) {
        return ResponseEntity.ok().body(DatesList);
        }
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    //rezerwuje datę poprzez usunięcie ją z bazy dostępnych terminów
    @Override
    public ResponseEntity<String> postDates(@RequestBody String date, @RequestHeader("Authorization") String Authorization, @RequestHeader("X-HMAC-SIGNATURE") String HeaderHMAC) throws NoSuchAlgorithmException, InvalidKeyException {
        if(BasicAuth(Authorization)) {
            if(HMAC(date,HeaderHMAC)){
            if (DatesList.searchDate(date)) {
                DatesList.excludeDate(date);
                return ResponseEntity.ok().body("Termin zarezerwowany prawidłowo");
            } else return ResponseEntity.badRequest().body("Brak podanego terminu na liście!");
        }
            else return ResponseEntity.badRequest().body("Brak intergralności danych");
        }
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    //udostępnia wynki badań na podstawie imienia i nazwiska
    @Override
    public ResponseEntity getLabResults(String username, @RequestHeader("Authorization") String Authorization) {
        if(BasicAuth(Authorization)) {
        if (LabResultsList.searchResult(username)) return ResponseEntity.ok().body(LabResultsList.getResult(username));
        else return ResponseEntity.badRequest().body("Brak wyników w bazie dla podanego użytkownika!");
        }
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    //basic auth
    public Boolean BasicAuth(String input){
        String concat="Basic "+ Base64.getEncoder().encodeToString(BasicCredentials.getBytes());
        if (concat.equals(input))return true;
        else return false;
    }

    //weryfikacja integralności
    private byte[] hmackey="123456".getBytes();

    public Boolean HMAC(String HMAC,String HeaderHMAC) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] bytes = hmac("HmacSHA256", hmackey, HMAC.getBytes());
        String encoded=bytesToHex(bytes);
        System.out.println(encoded);
        if(encoded.equals(HeaderHMAC)) return true;
        else return false;

    }
    byte[] hmac(String algorithm, byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(message);
    }
    String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
