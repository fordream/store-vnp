package com.caferhythm.csn.data;

import java.util.ArrayList;

import com.caferhythm.csn.utils.StringUtils;

public class SP04Entity {
	private InfoEntity infoEntity;
	private TimeEntity seiri;
	private TimeEntity hairan;
	private ArrayList<PeriodTimeEntity> term;
	private String image;
	private String imageSub;
	private ErrorEntity errorEntity;
	
	public ErrorEntity getErrorEntity() {
		return errorEntity;
	}

	public void setErrorEntity(ErrorEntity errorEntity) {
		this.errorEntity = errorEntity;
	}

	public SP04Entity() {
		this.infoEntity = new InfoEntity();
		this.seiri = new TimeEntity();
		this.hairan = new TimeEntity();
		this.term = new ArrayList<PeriodTimeEntity>();
		this.image = "";
		this.imageSub = "";
		this.errorEntity = new ErrorEntity();
	}
	
	public void sortDate(){
		for(int i=0;i< term.size()-1;i++){
			for(int j = i;j< term.size();j++){
				PeriodTimeEntity t1 = term.get(i);
				PeriodTimeEntity t2 = term.get(j);
				if(!StringUtils.compareDate(t1.getStart(), t2.getStart())){
					PeriodTimeEntity temp = t1;
					term.set(i, t2);
					term.set(j, temp);
				}
			}
		}
	}
	
	public void dateFormat() {
		for (int i = 0; i < term.size(); i++) {
			term.get(i).setStart(StringUtils.dateFormat(term.get(i).getStart()));
			term.get(i).setEnd(StringUtils.dateFormat(term.get(i).getEnd()));
		}
	}
	
	public void removeNull() {
		this.infoEntity.removeNull();
		this.seiri.removeNull();
		this.hairan.removeNull();
		for (PeriodTimeEntity p :  term) {
			p.removeNull();
		}
		this.image = "";
		this.imageSub = "";
		this.errorEntity.removeNull();
	}
	
	public InfoEntity getInfoEntity() {
		return infoEntity;
	}
	public void setInfoEntity(InfoEntity infoEntity) {
		this.infoEntity = infoEntity;
	}
	
	public TimeEntity getSeiri() {
		return seiri;
	}

	public void setSeiri(TimeEntity seiri) {
		this.seiri = seiri;
	}

	public TimeEntity getHairan() {
		return hairan;
	}
	public void setHairan(TimeEntity hairan) {
		this.hairan = hairan;
	}
	public ArrayList<PeriodTimeEntity> getTerm() {
		return term;
	}
	public void setTerm(ArrayList<PeriodTimeEntity> term) {
		this.term = term;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImageSub() {
		return imageSub;
	}
	public void setImageSub(String imageSub) {
		this.imageSub = imageSub;
	}
	
	
}
