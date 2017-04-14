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
}

