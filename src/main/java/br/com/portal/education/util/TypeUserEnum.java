package br.com.portal.education.util;

public enum TypeUserEnum {
    ADMINSTRATOR(1l), TEACHER(2l), STUDENT(3l);
    
    public Long getIdTypeUser() {
        return idTypeUser;
    }

    public void setIdTypeUser(Long idTypeUser) {
        this.idTypeUser = idTypeUser;
    }

    private Long idTypeUser;
    
    TypeUserEnum(Long idTypeUser){
	this.idTypeUser = idTypeUser;
    }
    
    

}
