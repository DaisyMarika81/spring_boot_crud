package com.crud.school.school_crud.entities;

import jakarta.persistence.*;
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
    private String maLop;
    private String tenLop;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "maGv", referencedColumnName = "ma_gv")
    })
    private GiaoVien giaovien;
    private String ghichu;

}
