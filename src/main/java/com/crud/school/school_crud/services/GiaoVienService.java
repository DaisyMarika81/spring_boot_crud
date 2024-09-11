package com.crud.school.school_crud.services;

import com.crud.school.school_crud.dto.CUGiaoVienDTO;
import com.crud.school.school_crud.entities.GiaoVien;
import com.crud.school.school_crud.entities.ResponseObject;
import com.crud.school.school_crud.repositories.GiaoVienRepository;
import com.crud.school.school_crud.repositories.LopHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GiaoVienService {
    @Autowired
    private GiaoVienRepository giaovienRepository;
    @Autowired
    private LopHocRepository lopHocRepository;

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

    public ResponseEntity<ResponseObject> updateGiaovien(Integer id, CUGiaoVienDTO updatedGiaoVienDTO) {
        Optional<GiaoVien> existingGiaoVienOptional = giaovienRepository.findById(id);

        if (existingGiaoVienOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Giao Vien not found!", "")
            );
        }

        GiaoVien existingGiaoVien = existingGiaoVienOptional.get();

        GiaoVien existedGiaoVienByMaGv = giaovienRepository.findByMaGv(updatedGiaoVienDTO.getMaGv());

        if (existedGiaoVienByMaGv != null && !Objects.equals(existingGiaoVien.getId(), existedGiaoVienByMaGv.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "maGv already exists for another Giao Vien!", "")
            );
        }

        CUGiaoVienDTO updatedGiaoVien = new CUGiaoVienDTO(
                updatedGiaoVienDTO.getMaGv(), updatedGiaoVienDTO.getTenGv(), updatedGiaoVienDTO.getTuoi(), updatedGiaoVienDTO.getGhichu());

        GiaoVien updatedSaveGiaoVien = getGiaoVien(updatedGiaoVien, existingGiaoVienOptional);
        giaovienRepository.save(updatedSaveGiaoVien);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Giao vien updated successfully!", "")
        );

    }

    public ResponseEntity<ResponseObject> deleteGiaovien(Integer id) {
        Optional<GiaoVien> existingGiaoVienOptional = giaovienRepository.findById(id);

        if (existingGiaoVienOptional.isPresent()) {
            GiaoVien existedGiaoVien = existingGiaoVienOptional.get();

            boolean hasClasses = lopHocRepository.existsByGiaovien(existedGiaoVien);

            if (hasClasses) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject(400, "Cannot delete the teacher as they are assigned to one or more classes!", "")
                );
            }

            giaovienRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Teacher deleted successfully!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Teacher not found!", "")
            );
        }
    }


    private GiaoVien getGiaoVien(CUGiaoVienDTO updatedGiaoVienDTO, Optional<GiaoVien> existingGiaoVienOptional) {
        GiaoVien existingGiaoVien = existingGiaoVienOptional.get();
        if (updatedGiaoVienDTO.getMaGv() != null)
            existingGiaoVien.setMaGv(updatedGiaoVienDTO.getMaGv());
        if (updatedGiaoVienDTO.getTenGv() != null)
            existingGiaoVien.setTenGv(updatedGiaoVienDTO.getTenGv());
        if (updatedGiaoVienDTO.getTuoi() > 0)
            existingGiaoVien.setTuoi(updatedGiaoVienDTO.getTuoi());
        if (updatedGiaoVienDTO.getGhichu() != null)
            existingGiaoVien.setGhichu(updatedGiaoVienDTO.getGhichu());
        return existingGiaoVien;
    }

}
