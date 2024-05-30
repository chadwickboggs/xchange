package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.Want;
import com.tiffanytimbric.xchange.core.repository.WantRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RestController
public class WantController {

    private final WantRepository wantRepository;

    public WantController(
            @NonNull final WantRepository wantRepository
    ) {
        this.wantRepository = wantRepository;
    }

    @GetMapping("/wantExist/{id}")
    @NonNull
    public boolean doesExist(@PathVariable final UUID id) {
        return wantRepository.existsById(id);
    }

    @GetMapping("/want/{id}")
    @NonNull
    public ResponseEntity<Want> readWant(@PathVariable final UUID id) {
        return ResponseEntity.of(
                wantRepository.findById(id)
        );
    }

    @PostMapping("/want")
    @NonNull
    public ResponseEntity<Want> createWant(@RequestBody @Nullable final Want want) {
        if (want == null) {
            return ResponseEntity.ofNullable(null);
        }

        if (want.idOpt().isEmpty()) {
            want.setId(UUID.randomUUID());
        }

        return ResponseEntity.of(
                Optional.of(wantRepository.save(want))
        );
    }

    @PutMapping("/want")
    @NonNull
    public ResponseEntity<Want> updateWant(@RequestBody @Nullable final Want want) {
        if (want == null) {
            return ResponseEntity.ofNullable(null);
        }

        return ResponseEntity.of(
                Optional.of(wantRepository.save(want))
        );
    }

    @PatchMapping("/want")
    @NonNull
    public ResponseEntity<Want> patchWant(@RequestBody @Nullable final Want want) {
        if (want == null) {
            return ResponseEntity.ofNullable(null);
        }

        throw new ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Invalid method, method not implemented.  Method Name: \"patchWant\""
        );
    }

    @DeleteMapping("/want/{id}")
    @NonNull
    public ResponseEntity<Want> deleteWant(@PathVariable final UUID id) {
        final Optional<Want> itemOpt = wantRepository.findById(id);
        if (itemOpt.isEmpty()) {
            return ResponseEntity.ofNullable(null);
        }

        wantRepository.deleteById(id);

        return ResponseEntity.of(itemOpt);
    }

}
