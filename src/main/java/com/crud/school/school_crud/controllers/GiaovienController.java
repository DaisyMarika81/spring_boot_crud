package com.crud.school.school_crud.controllers;

import com.crud.school.school_crud.dto.CUGiaoVienDTO;
import com.crud.school.school_crud.entities.GiaoVien;
import com.crud.school.school_crud.entities.ResponseObject;
import com.crud.school.school_crud.services.GiaoVienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/giaovien")
public class GiaovienController {
    @Autowired
    private GiaoVienService giaovienService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAll() {
        return giaovienService.getAllGiaovien();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable int id) {
        return giaovienService.getGiaovienById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createGiaovien(@RequestBody @Valid GiaoVien giaovien) {
        return giaovienService.saveGiaovien(giaovien);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateGiaovien(@PathVariable Integer id, @RequestBody @Valid CUGiaoVienDTO updatedGiaoVienDTO) {
        return giaovienService.updateGiaovien(id, updatedGiaoVienDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteGiaovien(@PathVariable Integer id) {
        return giaovienService.deleteGiaovien(id);
    }
}