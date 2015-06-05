package moives.entity;

import java.lang.reflect.Array;

import tw.com.riversoft.core.util.DateUtil;
import modelet.entity.AbstractAppEntity;

public class AbstractMoviesEntity extends AbstractAppEntity {

	private Boolean removed = Boolean.FALSE;
	 
	  public Boolean isRemoved() {
	    return getRemoved();
	  }
	  
	  public Boolean getRemoved() {
	    return removed;
	  }
	  
	  public void setRemoved(Boolean removed) {
	    this.removed = removed;
	  }
	  
	  public void setId(Long id) {
	    super.setId(id);
	  }
	  
	  public String getCreateDateFormat() {
	         return DateUtil.dateFormater(getCreateDate(), DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS_DASH); 
	  }
	  
	  public Long getId() {
	    
	    Object superId = super.getId();
	    if (superId == null)
	      return null;
	    else if (superId.getClass().isArray())
	      return Long.parseLong(Array.get(superId, 0).toString());
	    else
	      return Long.parseLong(super.getId().toString());
	  }

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}
}
