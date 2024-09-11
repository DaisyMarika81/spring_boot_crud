package com.crud.school.school_crud.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CUGiaoVienDTO {

    @NotEmpty(message = "Please input maGv")
    private String maGv;

    @NotEmpty(message = "Please input tenGv")
    private String tenGv;

    @Min(value = 18, message = "Tuoi should be at least 18")
    private int tuoi;

    private String ghichu;

}
