package com.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerDto {
    private String name;
    private String email;
    private String Skills;
    private String resumeLink;

}
