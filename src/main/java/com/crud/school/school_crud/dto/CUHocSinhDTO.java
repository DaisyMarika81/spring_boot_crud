package com.crud.school.school_crud.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CUHocSinhDTO {

    @NotEmpty(message = "Please input maHs")
    private String maHs;

    @NotEmpty(message = "Please input tenHs")
    private String tenHs;

    @NotEmpty(message = "Please input maLop")
    private String maLop;

    private String ghichu;
}
