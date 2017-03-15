package com.zhiweicloud.guest.common;

import java.util.List;

public class ListUtil {
	/**
		 * List 转String，以“，”分割
		 * @param <E>
		 * @param <E>
		 */
		public static <E> String List2String(List<E> list){
			if(list==null||list.isEmpty())
				return "";
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<list.size();i++){
				sb.append(list.get(i));
				if(i!=list.size()-1)
					sb.append(",");
			}
			return sb.toString();
		}

	/**
	 * 两个List 转String，以“，”分割
	 * @param <E>
	 * @param <E>
	 */
	public static <E> String List2String(List<E> list, List<E> list2){
		if(list == null||list.isEmpty()){
			if(list2 == null||list2.isEmpty()){
				return "";
			}
			return List2String(list2);
		}else{
			if(list2 == null||list2.isEmpty()){
				return List2String(list);
			}
			return List2String(list)+","+List2String(list2);
		}

	}
}

