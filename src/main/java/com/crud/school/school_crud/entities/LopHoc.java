package com.crud.school.school_crud.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lophoc")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LopHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ma_lop", length = 255, unique = true)
    @NotEmpty(message = "Please input maLop")
    private String maLop;

    @NotEmpty(message = "Please input tenLop")
    private String tenLop;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "maGv", referencedColumnName = "ma_gv")
    private GiaoVien giaovien;

    private String ghichu;

    public LopHoc(String maLop, String tenLop, GiaoVien giaovien, String ghichu) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.giaovien = giaovien;
        this.ghichu = ghichu;
    }
}
