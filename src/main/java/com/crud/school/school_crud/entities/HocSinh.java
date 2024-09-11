package com.crud.school.school_crud.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "hocsinh")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HocSinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String maHs;
    private String tenHs;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "maLop", referencedColumnName = "ma_lop")
    })
    private LopHoc lophoc;

    private String ghichu;

}