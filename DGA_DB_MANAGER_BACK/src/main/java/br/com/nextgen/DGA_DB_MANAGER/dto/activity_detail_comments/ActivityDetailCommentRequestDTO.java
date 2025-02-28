package br.com.nextgen.DGA_DB_MANAGER.dto.activity_detail_comments;

import java.math.BigInteger;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record ActivityDetailCommentRequestDTO(
                  String id,
                  String text,
        @NotNull  BigInteger id_activity_detail,
                  BigInteger id_user,
                  LocalDateTime time
){}
