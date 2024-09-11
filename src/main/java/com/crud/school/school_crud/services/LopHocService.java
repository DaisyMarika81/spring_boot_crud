package com.crud.school.school_crud.services;

import com.crud.school.school_crud.dto.CULopHocDTO;
import com.crud.school.school_crud.entities.GiaoVien;
import com.crud.school.school_crud.entities.LopHoc;
import com.crud.school.school_crud.entities.ResponseObject;
import com.crud.school.school_crud.repositories.GiaoVienRepository;
import com.crud.school.school_crud.repositories.HocSinhRepository;
import com.crud.school.school_crud.repositories.LopHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LopHocService {
    @Autowired
    private LopHocRepository lophocRepository;
    @Autowired
    private GiaoVienRepository giaoVienRepository;
    @Autowired
    private HocSinhRepository hocSinhRepository;

    public ResponseEntity<ResponseObject> getAllLophoc() {
        List<LopHoc> listLopHoc = lophocRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "", listLopHoc)
        );
    }

    public ResponseEntity<ResponseObject> getLophocById(Integer id) {
        Optional<LopHoc> foundLopHoc = lophocRepository.findById(id);
        return foundLopHoc.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "", foundLopHoc)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(404, "Lop Hoc not found!", "")
                );
    }

    public ResponseEntity<ResponseObject> saveLophoc(CULopHocDTO createLopHocDTO) {
        LopHoc existedLopHoc = lophocRepository.findByMaLop(createLopHocDTO.getMaLop());
        if (existedLopHoc != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseObject(400, "Lop Hoc existed, please try again!", "")
            );
        }

        GiaoVien existedGiaoVien = giaoVienRepository.findByMaGv(createLopHocDTO.getMaGv());

        if (existedGiaoVien == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Giao vien not existed, please try again!", "")
            );
        }

        LopHoc savedLopHoc = new LopHoc
                (createLopHocDTO.getMaLop(), createLopHocDTO.getTenLop(), existedGiaoVien, createLopHocDTO.getGhichu());

        lophocRepository.save(savedLopHoc);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject(201, "Lop Hoc created successfully!", "")
        );
    }

    public ResponseEntity<ResponseObject> updateLopHoc(Integer id, CULopHocDTO updatedLopHocDTO) {
        Optional<LopHoc> existingLopHocOptional = lophocRepository.findById(id);

        if (existingLopHocOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Lop hoc not found!", "")
            );
        }

        LopHoc existingLopHoc = existingLopHocOptional.get();

        LopHoc existedLopHocByMaLop = lophocRepository.findByMaLop(updatedLopHocDTO.getMaLop());

        if (existedLopHocByMaLop != null && !Objects.equals(existingLopHoc.getId(), existedLopHocByMaLop.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Ma Lop already exists for another lop!", "")
            );
        }

        GiaoVien existedGiaoVien = giaoVienRepository.findByMaGv(updatedLopHocDTO.getMaGv());

        if (existedGiaoVien == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Giao vien not existed, please try again!", "")
            );
        }

        LopHoc updatedLopHoc = new LopHoc
                (updatedLopHocDTO.getMaLop(), updatedLopHocDTO.getTenLop(), existedGiaoVien, updatedLopHocDTO.getGhichu());

        LopHoc updatedSaveLopHoc = getLopHoc(updatedLopHoc, existingLopHocOptional);
        lophocRepository.save(updatedSaveLopHoc);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Lop Hoc updated successfully!", "")
        );

    }

    public ResponseEntity<ResponseObject> deleteLophoc(Integer id) {
        Optional<LopHoc> existingLopHocOptional = lophocRepository.findById(id);

        if (existingLopHocOptional.isPresent()) {
            LopHoc existedLopHoc = existingLopHocOptional.get();
            boolean hasStudents = hocSinhRepository.existsByLophoc(existedLopHoc);
            if (hasStudents) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject
                                (400,
                                        "Cannot delete the classroom as it has one or more students!",
                                        "")
                );
            }
            lophocRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Lop Hoc deleted successfully!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Lop Hoc not found!", "")
            );
        }
    }

    private static LopHoc getLopHoc(LopHoc updatedLopHoc, Optional<LopHoc> existingLopHocOptional) {
        LopHoc existingLopHoc = existingLopHocOptional.get();

        if (updatedLopHoc.getMaLop() != null && !updatedLopHoc.getMaLop().isEmpty())
            existingLopHoc.setMaLop(updatedLopHoc.getMaLop());
        if (updatedLopHoc.getTenLop() != null && !updatedLopHoc.getTenLop().isEmpty())
            existingLopHoc.setTenLop(updatedLopHoc.getTenLop());
        if (updatedLopHoc.getGiaovien() != null)
            existingLopHoc.setGiaovien(updatedLopHoc.getGiaovien());
        if (updatedLopHoc.getGhichu() != null && !updatedLopHoc.getGhichu().isEmpty())
            existingLopHoc.setGhichu(updatedLopHoc.getGhichu());
        return existingLopHoc;
    }
}
