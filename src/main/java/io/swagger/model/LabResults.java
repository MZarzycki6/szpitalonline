package io.swagger.model;

import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LabResults {

    private String name;
    private String TestDate;
    private String TestType;
    private String Status;

}
