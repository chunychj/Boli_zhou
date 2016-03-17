package boli.ychj.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

public class BoliPic5 extends BmobObject {  //
	
	private static final long serialVersionUID = 38057923051L;
	private String name;
	private BmobFile pic;
	private BmobDate uploadTime;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public BmobFile getPic() {
		return pic;
	}
	public void setPic(BmobFile pic) {
		this.pic = pic;
	}
	
	public BmobDate getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(BmobDate uploadTime) {
		this.uploadTime = uploadTime;
	}


}
