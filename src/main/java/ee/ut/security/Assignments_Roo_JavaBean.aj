// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ee.ut.security;

import ee.ut.security.Assignments;
import ee.ut.security.Authorities;
import ee.ut.security.Users;

privileged aspect Assignments_Roo_JavaBean {
    
    public Users Assignments.getUserBuildIt() {
        return this.userBuildIt;
    }
    
    public void Assignments.setUserBuildIt(Users userBuildIt) {
        this.userBuildIt = userBuildIt;
    }
    
    public Authorities Assignments.getAuthority() {
        return this.authority;
    }
    
    public void Assignments.setAuthority(Authorities authority) {
        this.authority = authority;
    }
    
}
