package ee.ut.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ee.ut.model.ConstructionSite;

@RooJpaRepository(domainType = ConstructionSite.class)
public interface ConstructionSiteRepository {
	
	@Query("SELECT c FROM ConstructionSite AS c WHERE UPPER(c.name) LIKE UPPER(:name) AND  UPPER(c.location) LIKE UPPER(:location)")
	@Transactional(readOnly=true)
	List<ConstructionSite> findByNameAndLocation(@Param("name") String name,
										@Param("location") String location);
}
