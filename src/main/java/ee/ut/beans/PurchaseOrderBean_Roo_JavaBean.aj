// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ee.ut.beans;

import ee.ut.beans.PurchaseOrderBean;
import ee.ut.domain.PurchaseOrderStatus;
import ee.ut.model.PlantHireRequest;
import java.util.Date;

privileged aspect PurchaseOrderBean_Roo_JavaBean {
    
    public PurchaseOrderStatus PurchaseOrderBean.getStatus() {
        return this.status;
    }
    
    public void PurchaseOrderBean.setStatus(PurchaseOrderStatus status) {
        this.status = status;
    }
    
    public PlantHireRequest PurchaseOrderBean.getPlantHireRequest() {
        return this.plantHireRequest;
    }
    
    public void PurchaseOrderBean.setPlantHireRequest(PlantHireRequest plantHireRequest) {
        this.plantHireRequest = plantHireRequest;
    }
    
    public Date PurchaseOrderBean.getDateCreated() {
        return this.dateCreated;
    }
    
    public void PurchaseOrderBean.setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public String PurchaseOrderBean.getExternalID() {
        return this.ExternalID;
    }
    
    public void PurchaseOrderBean.setExternalID(String ExternalID) {
        this.ExternalID = ExternalID;
    }
    
}
