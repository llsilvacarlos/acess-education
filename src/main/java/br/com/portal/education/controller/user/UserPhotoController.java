package br.com.portal.education.controller.user;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class UserPhotoController implements Serializable {

    private StreamedContent content = null;
    private byte [] photoBytes = null;
    

    public void loadPhoto(byte[] bs, String fileName) {
	content = new DefaultStreamedContent(new ByteArrayInputStream(bs), "image/jpeg");
	photoBytes = bs;
    }

    public StreamedContent getContent() {
	if(photoBytes != null)
	content = new DefaultStreamedContent(new ByteArrayInputStream(photoBytes), "image/jpeg");
	return content;
    }

    public void setContent(StreamedContent content) {
	this.content = content;
    }

    public byte[] getPhotoBytes() {
        return photoBytes;
    }

}
