package com.dut.bookstore.repository;

import com.dut.bookstore.model.BookImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookImageRepository extends CrudRepository<BookImage, Integer> {
}
