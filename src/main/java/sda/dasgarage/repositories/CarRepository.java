package sda.dasgarage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sda.dasgarage.entities.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}