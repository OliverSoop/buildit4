// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ee.ut.security;

import ee.ut.security.Authorities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Authorities_Roo_Jpa_Entity {
    
    declare @type: Authorities: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Authorities.id;
    
    @Version
    @Column(name = "version")
    private Integer Authorities.version;
    
    public Long Authorities.getId() {
        return this.id;
    }
    
    public void Authorities.setId(Long id) {
        this.id = id;
    }
    
    public Integer Authorities.getVersion() {
        return this.version;
    }
    
    public void Authorities.setVersion(Integer version) {
        this.version = version;
    }
    
}
