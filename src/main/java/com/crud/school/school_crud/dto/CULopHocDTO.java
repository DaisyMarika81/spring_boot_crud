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
public class CULopHocDTO {

    @NotEmpty(message = "Please input maLop")
    private String maLop;

    @NotEmpty(message = "Please input tenLop")
    private String tenLop;

    @NotEmpty(message = "Please input maGv")
    private String maGv;

    private String ghichu;
}
