package io.swagger.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@NoArgsConstructor
@Getter
public class LabResultsList {
    private List<LabResults> Results;
    public LabResultsList(List<LabResults> listOfLabResults){
        this.Results=new ArrayList<LabResults>();
        this.Results=listOfLabResults;
    }
    public boolean searchResult(String name){
        boolean flag=false;
        for (LabResults result:Results){
            if (result.getName().equals(name)) flag=true;
        }
        return flag;
    }
    public LabResults getResult(String name) {
        for (LabResults result : Results) {
            if (result.getName().equals(name)) return result;
        }
        return null;
    }
}