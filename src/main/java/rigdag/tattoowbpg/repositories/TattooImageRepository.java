package rigdag.tattoowbpg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rigdag.tattoowbpg.entities.TattooImage;

public interface TattooImageRepository extends JpaRepository<TattooImage, Long> {
}