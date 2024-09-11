package com.crud.school.school_crud.repositories;

import com.crud.school.school_crud.entities.GiaoVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiaoVienRepository extends JpaRepository<GiaoVien, Integer> {
    GiaoVien findByMaGv(String maGv);
}
