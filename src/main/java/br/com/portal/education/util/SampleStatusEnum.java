package br.com.portal.education.util;

public enum SampleStatusEnum {
    SCHEDULED(1l), CANCELED(2l);
    
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    private Long status;
    
    SampleStatusEnum(Long status){
	this.status = status;
    }
    
    

}
