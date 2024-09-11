package com.crud.school.school_crud.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Please input maHs")
    private String maHs;
    @NotEmpty(message = "Please input tenHs")
    private String tenHs;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumns({
            @JoinColumn(name = "maLop", referencedColumnName = "ma_lop", foreignKey = @ForeignKey(name = "fk_hocsinh_lophoc", foreignKeyDefinition = "FOREIGN KEY (maLop) REFERENCES lophoc(ma_lop) ON DELETE CASCADE ON UPDATE CASCADE"))
    })
    private LopHoc lophoc;

    private String ghichu;

    public HocSinh(String maHs, String tenHs, LopHoc lophoc, String ghichu) {
        this.maHs = maHs;
        this.tenHs = tenHs;
        this.lophoc = lophoc;
        this.ghichu = ghichu;
    }
}