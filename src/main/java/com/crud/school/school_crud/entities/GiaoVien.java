package com.crud.school.school_crud.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "giaovien")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GiaoVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ma_gv", length = 255, unique = true)
    @NotEmpty(message = "Please input maGv")
    private String maGv;

    @NotEmpty(message = "Please input tenGv")
    private String tenGv;

    @Min(value = 18, message = "Tuoi should be at least 18")
    private int tuoi;

    private String ghichu;
}
