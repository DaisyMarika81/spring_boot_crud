package com.crud.school.school_crud.services;

import com.crud.school.school_crud.entities.LopHoc;
import com.crud.school.school_crud.entities.ResponseObject;
import com.crud.school.school_crud.repositories.LopHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LopHocService {
    @Autowired
    private LopHocRepository lophocRepository;

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

    public ResponseEntity<ResponseObject> saveLophoc(LopHoc lophoc) {
        LopHoc existedLopHoc = lophocRepository.findByMaLop(lophoc.getMaLop());
        System.out.println(existedLopHoc);
        if (existedLopHoc != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseObject(400, "Lop Hoc existed, please try again!", "")
            );
        }
        lophocRepository.save(lophoc);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject(201, "Lop Hoc created successfully!", "")
        );
    }

    public ResponseEntity<ResponseObject> updateLopHoc(Integer id, LopHoc updatedLopHoc) {
        Optional<LopHoc> existingLopHocOptional = lophocRepository.findById(id);

        if (updatedLopHoc.getMaLop() != null && !updatedLopHoc.getMaLop().isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(400, "Can not perform update, please try again!", "")
            );

        if (existingLopHocOptional.isPresent()) {
            LopHoc existingLopHoc = getLopHoc(updatedLopHoc, existingLopHocOptional);
            lophocRepository.save(existingLopHoc);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Lop Hoc updated successfully!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Lop Hoc not found!", "")
            );
        }
    }


    public ResponseEntity<ResponseObject> deleteLophoc(Integer id) {
        if (lophocRepository.existsById(id)) {
            lophocRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new ResponseObject(204, "Lop Hoc deleted successfully!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Lop Hoc not found!", "")
            );
        }
    }

    private static LopHoc getLopHoc(LopHoc updatedLopHoc, Optional<LopHoc> existingLopHocOptional) {
        LopHoc existingLopHoc = existingLopHocOptional.get();

        if (updatedLopHoc.getTenLop() != null && !updatedLopHoc.getTenLop().isEmpty())
            existingLopHoc.setTenLop(updatedLopHoc.getTenLop());
        if (updatedLopHoc.getGiaovien() != null)
            existingLopHoc.setGiaovien(updatedLopHoc.getGiaovien());
        if (updatedLopHoc.getGhichu() != null && !updatedLopHoc.getGhichu().isEmpty())
            existingLopHoc.setGhichu(updatedLopHoc.getGhichu());
        return existingLopHoc;
    }
}
