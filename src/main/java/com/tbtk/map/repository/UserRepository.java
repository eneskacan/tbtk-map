package com.tbtk.map.repository;

import com.tbtk.map.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
