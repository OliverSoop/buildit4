// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ee.ut.model;

import ee.ut.domain.PurchaseOrderStatus;
import ee.ut.model.PlantHireRequest;
import ee.ut.model.PurchaseOrder;

privileged aspect PurchaseOrder_Roo_JavaBean {
    
    public PurchaseOrderStatus PurchaseOrder.getStatus() {
        return this.status;
    }
    
    public void PurchaseOrder.setStatus(PurchaseOrderStatus status) {
        this.status = status;
    }
    
    public PlantHireRequest PurchaseOrder.getPlantHireRequest() {
        return this.plantHireRequest;
    }
    
    public void PurchaseOrder.setPlantHireRequest(PlantHireRequest plantHireRequest) {
        this.plantHireRequest = plantHireRequest;
    }
    
}
