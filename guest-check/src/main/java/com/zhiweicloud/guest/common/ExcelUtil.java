package com.zhiweicloud.guest.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by tc on 2017/3/21.
 */
public class ExcelUtil {

    private static final Log log = LogFactory.getLog(ExcelUtil.class);

    /**
     * 将数据转换为Excel
     * @param list          数据集合
     * @param workName      工作簿名称
     * @param members       数据属性名称
     * @param names         在Excel中显示的列名称，可为空，当为空时，直接显示属性名称
     * @param out           输出流
     * @param <T>           数据类型
     * @throws IOException
     * @throws WriteException
     */
//    public static <T> void toExcel(List<T> list, String workName, String[] members, String[] names, OutputStream out)
//    {
//        if (null == list || list.isEmpty()) throw new RuntimeException("导出元素不能为空");
//        if (null == members || members.length < 1) throw new RuntimeException("导出属性不能为空");
//        try
//        {
//            WritableWorkbook wwb = Workbook.createWorkbook(out);
//            WritableSheet ws = wwb.createSheet(workName, 0);
//            Object val = null;// 值
//            String valStr = null;// 值的字符串形式
//            String nameStr = null;// 列名
//            for (int col = 0; col < members.length; col++)
//            {
//                nameStr = null == names || names.length < col ? members[col] : names[col];// 名称
//
//                // 显示名称
//                ws.addCell(new Label(col, 0, StringUtils.nullToStr(nameStr)));
//
//                for (int row = 0; row < list.size(); row++)
//                {
//                    val = ReflectUtils.getValue(list.get(row), members[col]);
//                    valStr = null == val ? "" : val.toString();// 值
//
//                    // 显示值，因为有列名称，所以行数+1
//                    ws.addCell(new Label(col, row + 1, valStr));
//                }
//            }
//
//            wwb.write();
//            wwb.close();
//            out.flush();
//        } catch (Exception e)
//        {
//            throw new RuntimeException("数据转换Excel异常", e);
//        }
//    }

}
