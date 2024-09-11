package com.crud.school.school_crud.controllers;

import com.crud.school.school_crud.entities.GiaoVien;
import com.crud.school.school_crud.entities.ResponseObject;
import com.crud.school.school_crud.services.GiaoVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/giaovien")
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
    public ResponseEntity<ResponseObject> createGiaovien(@RequestBody GiaoVien giaovien) {
        return giaovienService.saveGiaovien(giaovien);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateGiaovien(@PathVariable Integer id, @RequestBody GiaoVien giaovien) {
        return giaovienService.updateGiaovien(id, giaovien);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteGiaovien(@PathVariable Integer id) {
        return giaovienService.deleteGiaovien(id);
    }
}