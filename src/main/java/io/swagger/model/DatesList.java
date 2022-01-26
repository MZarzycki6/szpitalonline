package io.swagger.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@NoArgsConstructor
@Getter
public class DatesList {
    private List<String> Dates;
    public DatesList(List<String> listOfDates){
        this.Dates = new ArrayList<String>();
        this.Dates = listOfDates;
    }
    public boolean searchDate(String date){
        boolean flag=false;
        for(String Xdate:Dates){
            if (Xdate.equals(date)) flag=true;
        }
        return flag;
    }
    public void excludeDate(String date){
        Dates.removeIf(Xdate -> Xdate.equals(date));
    }
}