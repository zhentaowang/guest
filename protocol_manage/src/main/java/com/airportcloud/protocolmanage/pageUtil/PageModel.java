package com.airportcloud.protocolmanage.pageUtil;

import java.util.List;

public class PageModel {
	  
    //结果集  
    private List<?> list ;  
      
    //查询总记录数  
    private int totalRecords ;  
      
    //每页多少条数据  
    private int pCount=10 ;  
      
    //第几页  
    private int pageNo=1 ; 
    
    private int startPageNo;
    
    public PageModel() {
		super();
	}

	public PageModel(int pageNo, int pCount) {
		super();
		this.pageNo = pageNo;
		this.pCount = pCount;
	}



	/**  
     * 总页数  
     * @return  
     */  
    public int getTotalPages(){  
        return (totalRecords + pCount -1) / pCount ;  
    }  
      
    /**  
     * 取得首页  
     * @return  
     */  
    public int getTopPageNo(){  
        return 1 ;  
    }  
      
    /**  
     * 上一页  
     * @return  
     */  
    public int getPreviousPageNo(){  
        if(pageNo <= 1){  
            return 1 ;  
        }  
        return pageNo - 1 ;  
    }  
      
    /**  
     * 下一页  
     * @return  
     */  
    public int getNextPageNo(){  
        if(pageNo >= getBottomPageNo()){  
            return getBottomPageNo() ;  
        }  
        return pageNo + 1 ;  
    }  
      
    /**  
     * 取得尾页  
     * @return  
     */  
    public int getBottomPageNo(){  
        return getTotalPages() ;  
    }  
  
    public List<?> getList() {  
        return list;  
    }  
  
    public void setList(List<?> list) {  
        this.list = list;  
    }  
  
    public int getTotalRecords() {  
        return totalRecords;  
    }  
  
    public void setTotalRecords(int totalRecords) {  
        this.totalRecords = totalRecords;  
    }  
  
    public int getPCount() {  
        return pCount;  
    }  
  
    public void setPCount(int pCount) {  
        this.pCount = pCount;  
    }  
  
    public int getPageNo() {  
        return pageNo;  
    }  
  
    public void setPageNo(int pageNo) {  
        this.pageNo = pageNo;  
    }

	protected int getpCount() {
		return pCount;
	}

	protected void setpCount(int pCount) {
		this.pCount = pCount;
	}

	public int getStartPageNo() {
		if (pageNo > 0) {
			return (pageNo -1)* pCount;
		} else {
			startPageNo = 0;
		}
		return startPageNo;
	}

	public void setStartPageNo(int startPageNo) {
		this.startPageNo = startPageNo;
	}  
    
    
}
