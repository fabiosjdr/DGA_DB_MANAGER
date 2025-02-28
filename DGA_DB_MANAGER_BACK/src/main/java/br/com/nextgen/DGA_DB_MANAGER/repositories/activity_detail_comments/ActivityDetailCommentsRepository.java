package br.com.nextgen.DGA_DB_MANAGER.repositories.activity_detail_comments;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nextgen.DGA_DB_MANAGER.domain.activity_detail_comments.ActivityDetailComments;

public interface ActivityDetailCommentsRepository extends JpaRepository<ActivityDetailComments,BigInteger>{

     Optional<List<ActivityDetailComments>> findByIdActivityDetail(BigInteger id_activity_detail);

     Optional<List<ActivityDetailComments>> findByIdActivityDetailAndId(BigInteger id_activity_detail,String id);
}
