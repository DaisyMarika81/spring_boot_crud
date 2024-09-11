package com.crud.school.school_crud.entities;

import jakarta.persistence.*;
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
    private String maGv;
    private String tenGv;
    private int tuoi;
    private String ghichu;
}
