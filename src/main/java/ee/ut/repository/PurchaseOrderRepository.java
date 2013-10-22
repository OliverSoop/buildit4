package ee.ut.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ee.ut.model.PlantHireRequest;
import ee.ut.model.PurchaseOrder;

@RooJpaRepository(domainType = PurchaseOrder.class)
public interface PurchaseOrderRepository {
	
	@Query("SELECT p FROM PurchaseOrder AS p WHERE p.plantHireRequest = :phr")
	@Transactional(readOnly=true)
	PurchaseOrder findByPHR(@Param("phr") PlantHireRequest phr);
}
