package com.crud.school.school_crud.services;

import com.crud.school.school_crud.entities.GiaoVien;
import com.crud.school.school_crud.entities.ResponseObject;
import com.crud.school.school_crud.repositories.GiaoVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiaoVienService {
    @Autowired
    private GiaoVienRepository giaovienRepository;

    public ResponseEntity<ResponseObject> getAllGiaovien() {
        List<GiaoVien> listGiaoVien = giaovienRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "", listGiaoVien)
        );
    }

    public ResponseEntity<ResponseObject> getGiaovienById(Integer id) {
        Optional<GiaoVien> foundGiaoVien = giaovienRepository.findById(id);
        return foundGiaoVien.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "", foundGiaoVien)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(404, "Giao vien not found!", "")
                );
    }

    public ResponseEntity<ResponseObject> saveGiaovien(GiaoVien giaovien) {
        GiaoVien existedGiaoVien = giaovienRepository.findByMaGv(giaovien.getMaGv());
        if (existedGiaoVien != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Giao vien existed, please try again!", "")
            );
        }
        giaovienRepository.save(giaovien);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject(201, "Giao vien created successfully!", "")
        );
    }

    public ResponseEntity<ResponseObject> updateGiaovien(Integer id, GiaoVien updatedGiaoVien) {
        Optional<GiaoVien> existingGiaoVienOptional = giaovienRepository.findById(id);

        if (updatedGiaoVien.getMaGv() != null && !updatedGiaoVien.getMaGv().isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(400, "Can not perform update, please try again!", "")
            );

        if (existingGiaoVienOptional.isPresent()) {
            GiaoVien existingGiaoVien = getGiaoVien(updatedGiaoVien, existingGiaoVienOptional);
            giaovienRepository.save(existingGiaoVien);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Giao vien updated successfully!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Giao vien not found!", "")
            );
        }
    }

    public ResponseEntity<ResponseObject> deleteGiaovien(Integer id) {
        if (giaovienRepository.existsById(id)) {
            giaovienRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new ResponseObject(204, "Giao vien deleted successfully!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Giao vien not found!", "")
            );
        }
    }

    private GiaoVien getGiaoVien(GiaoVien updatedGiaoVien, Optional<GiaoVien> existingGiaoVienOptional) {
        GiaoVien existingGiaoVien = existingGiaoVienOptional.get();
        if (updatedGiaoVien.getTenGv() != null)
            existingGiaoVien.setTenGv(updatedGiaoVien.getTenGv());
        if (updatedGiaoVien.getTuoi() > 0)
            existingGiaoVien.setTuoi(updatedGiaoVien.getTuoi());
        if (updatedGiaoVien.getGhichu() != null)
            existingGiaoVien.setGhichu(updatedGiaoVien.getGhichu());

        return existingGiaoVien;
    }

}
