package br.com.nextgen.DGA_DB_MANAGER.repositories.category;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.nextgen.DGA_DB_MANAGER.domain.category.Category;

public interface CategoryRepository extends JpaRepository<Category,String> {
    Optional<Category> findByName(String name);

    @Query("SELECT c FROM category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :text, '%'))")
    Page<Category> findByNameContainingIgnoreCase(@Param("text") String text, Pageable pageable);
}
