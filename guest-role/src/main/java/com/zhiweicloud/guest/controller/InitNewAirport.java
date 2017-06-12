package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wyun.thrift.server.business.IBusinessService;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.common.HttpClientUtil;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by THINK on 2017-06-09.
 */
@Component
public class InitNewAirport{
    // JDBC 驱动名及数据库 URL
    static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static String DB_URL = "";

    // 数据库的用户名与密码，需要根据自己的设置
    static String db_name = "";
    static String db_password = "";

    /*@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DB_URL = (String)req.getParameter("db_url");
        db_name = (String)req.getParameter("db_name");
        db_password = (String)req.getParameter("db_password");

        String copyFromAirportCode = (String)req.getParameter("copyFromAirportCode");
        String initAirportCode = (String)req.getParameter("client_id");
        String client_secret = (String)req.getParameter("client_secret");

        String authorUrl = (String)req.getParameter("authorUrl");
        String grant_type = (String)req.getParameter("grant_type");
        String username = (String)req.getParameter("username");
        String password = (String)req.getParameter("password");
        String password_confirmation = (String)req.getParameter("password_confirmation");






        String res =  this.initNewAirport(copyFromAirportCode,initAirportCode,authorUrl,grant_type,client_secret,username,password,password_confirmation);
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>" + res + "</h1>");
        out.println("</body>");
        out.println("</html>");
    }*/

    /**
     * 初始化新机场
     * @param request
     * @return
     */
    public String addNewAirport(JSONObject request){
        LZResult<Object> result = new LZResult<>();
        try{
            DB_URL = request.getString("db_url");
            db_name = request.getString("db_name");
            db_password = request.getString("db_password");

            String copyFromAirportCode = request.getString("copyFromAirportCode");
            String initAirportCode = request.getString("client_id");
            String client_secret = request.getString("client_secret");

            String authorUrl = request.getString("authorUrl");
            String grant_type = request.getString("grant_type");
            String username = request.getString("username");
            String password = request.getString("password");
            String password_confirmation = request.getString("password_confirmation");

            String res =  this.initNewAirport(copyFromAirportCode,initAirportCode,authorUrl,grant_type,client_secret,username,password,password_confirmation);

            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(res);
        } catch (Exception e) {
            return this.errorMsg(e);
        }
        return JSON.toJSONString(result);
    }

    /**
     *
     * @param copyFromAirportCode 参考机场三字码
     * @param initAirportCode 新建机场三字码
     */
    public String initNewAirport(String copyFromAirportCode, String initAirportCode, String authorUrl, String grant_type, String client_secret, String username, String password, String password_confirmation){
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,db_name,db_password);

            // 1： 添加角色 sys_role
            String addRoleSql = "INSERT INTO  sys_role (name, description, airport_code) VALUES ('超级管理员', '超级管理员角色', ?);";
            ps = conn.prepareStatement(addRoleSql);
            ps.setString(1, initAirportCode);
            ps.executeUpdate();

            //2：给上一步刚刚添加的角色分配菜单 sys_role_menu
            String addSysRoleMenuSql = "INSERT INTO sys_role_menu (role_id,menu_id,airport_code) " +
                    "SELECT * from (" +
                    "	select (select MIN(role_id) from sys_role where airport_code = ?),menu_id,? as airport_code from sys_menu" +
                    ") as tb";
            ps = conn.prepareStatement(addSysRoleMenuSql);
            ps.setString(1, initAirportCode);
            ps.setString(2, initAirportCode);
            ps.executeUpdate();

            //调用认证中心，返回user_id
            JSONObject paramObj = new JSONObject();
            paramObj.put("client_id",initAirportCode);
            paramObj.put("grant_type",grant_type);
            paramObj.put("client_secret",client_secret);
            paramObj.put("username",username);
            paramObj.put("password",password);
            paramObj.put("password_confirmation",password_confirmation);

            JSONObject userObj = JSON.parseObject(HttpClientUtil.httpPostRequest(authorUrl,paramObj));
            Long userId = userObj.getLong("user_id");

            // 3:新增员工
            String addUserSql = "INSERT INTO employee (employee_id, airport_code, account, name)\n" +
                    "            VALUES (?, ?, 'admin', '管理员');";
            ps = conn.prepareStatement(addUserSql);
            ps.setLong(1,userId);
            ps.setString(2, initAirportCode);
            ps.executeUpdate();

            //4：给员工分配角色
            String assignRoleToUser = "INSERT INTO sys_user_role (user_id, role_id, is_deleted, airport_code) \n" +
                    "select * from (\n" +
                    "\tselect (select MIN(employee_id) from employee where airport_code = ?) ,(select MIN(role_id) from sys_role where airport_code = ?) as role_id,0 as is_deleted,? as airport_code\n" +
                    ") as temp;\n" +
                    " ";
            ps = conn.prepareStatement(assignRoleToUser);
            ps.setString(1, initAirportCode);
            ps.setString(2, initAirportCode);
            ps.setString(3, initAirportCode);
            ps.executeUpdate();


            //5: permission 表基础数据
            String addPermissionSql = "insert into permission(airport_code,url,menu_name,name,data_permission,is_deleted)\n" +
                    "select * from (\n" +
                    "\tselect ? as airport_code, url,menu_name,name,data_permission,0 as is_deleted from permission where airport_code = ?\n" +
                    ") as temp;";
            ps = conn.prepareStatement(addPermissionSql);
            ps.setString(1, initAirportCode);
            ps.setString(2,copyFromAirportCode);
            ps.executeUpdate();

            //6: 维护role和permission的关系
            String assignPermissionToRole = "INSERT INTO role_permission(role_id, permission_id, airport_code, data_permission, is_deleted) \n" +
                    "select * from (\n" +
                    "\tselect (select MIN(role_id) from sys_role where airport_code = ?) as role_id,permission_id, ? as airport_code,data_permission,0 as is_deleted from permission where airport_code = ?\n" +
                    ") as temp;";
            ps = conn.prepareStatement(assignPermissionToRole);
            ps.setString(1, initAirportCode);
            ps.setString(2, initAirportCode);
            ps.setString(3, initAirportCode);
            ps.executeUpdate();


            //7:初始化服务类型
            String addSeriveTypeAlocationSql = "INSERT INTO service_type_allocation (service_type_allocation_id, airport_code, category_no, category, type, description, is_deleted) \n" +
                    "select * from (\n" +
                    "\tselect service_type_allocation_id,? as airport_code,category_no,category,type,description,0 as is_deleted from service_type_allocation where airport_code = ?\n" +
                    ") as temp;";
            ps = conn.prepareStatement(addSeriveTypeAlocationSql);
            ps.setString(1, initAirportCode);
            ps.setString(2, copyFromAirportCode);
            ps.executeUpdate();


            //8:初始化服务
            String addServSql = "INSERT INTO serv (airport_code, service_type_allocation_id, no, name, service_detail, is_deleted)\n" +
                    "select * from (\n" +
                    "\tselect ? as airport_code, service_type_allocation_id,no,name,service_detail,0 as is_deleted from serv where airport_code = ?\n" +
                    ") as temp;";
            ps = conn.prepareStatement(addServSql);
            ps.setString(1, initAirportCode);
            ps.setString(2, copyFromAirportCode);
            ps.executeUpdate();

            //9:初始化协议类型
            String addProtocolTypeSql = "INSERT INTO  protocol_type(protocol_type_id,protocol_type_name, airport_code)\n" +
                    "select * from (\n" +
                    "\tselect protocol_type_id,protocol_type_name ,? as airport_code from protocol_type where airport_code = ?\n" +
                    ")as temp;";
            ps = conn.prepareStatement(addProtocolTypeSql);
            ps.setString(1, initAirportCode);
            ps.setString(2, copyFromAirportCode);
            ps.executeUpdate();

            //10:初始化产品
            String addProductSql = "INSERT INTO product (airport_code, product_name, is_deleted)\n" +
                    "select * from (\n" +
                    "\tselect ? as airport_code,product_name,0 as is_deleted from product  where airport_code = ?\n" +
                    ")as temp;";
            ps = conn.prepareStatement(addProductSql);
            ps.setString(1, initAirportCode);
            ps.setString(2, copyFromAirportCode);
            ps.executeUpdate();


            //11:初始化客户类型
            String addInstitutionClientTypeSql = "INSERT INTO institution_type (intitution_name, is_deleted, airport_code)\n" +
                    "select * from (\n" +
                    "\tselect intitution_name,0 as is_deleted,? as airport_code from institution_type where airport_code = ?\n" +
                    ")as temp;";
            ps = conn.prepareStatement(addInstitutionClientTypeSql);
            ps.setString(1, initAirportCode);
            ps.setString(2, copyFromAirportCode);
            ps.executeUpdate();


            //12:初始化示例客户
            String addInstitutionClientSql = "INSERT INTO institution_client (airport_code, no, name, type, employee_id, remark,is_deleted) \n" +
                    "select * from (\n" +
                    "\tselect ? as airport_code , no, name, type, (select MIN(employee_id) from employee where airport_code = ?), remark,is_deleted from institution_client where airport_code = ? LIMIT 10\n" +
                    ")as temp;\n";
            ps = conn.prepareStatement(addInstitutionClientSql);
            ps.setString(1, initAirportCode);
            ps.setString(2, initAirportCode);
            ps.setString(3, copyFromAirportCode);
            ps.executeUpdate();

            //13:初始化一些协议
            String addProtocolSql = "INSERT INTO  protocol (airport_code, institution_client_id, name, type, is_deleted,start_date,end_date) \n" +
                    "select * from (\n" +
                    "\tselect ? as airport_code,institution_client_id,name,10 as type,0 as is_deleted,now() as start_date, date_add(now(), INTERVAL 10 day) as  end_date from institution_client where airport_code = ?\n" +
                    ")as temp;";
            ps = conn.prepareStatement(addProtocolSql);
            ps.setString(1, initAirportCode);
            ps.setString(2, initAirportCode);
            ps.executeUpdate();


            // 完成后关闭
            ps.close();
            conn.close();
            return "完美执行成功";
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
            return String.valueOf(e);
        }finally{
            // 关闭资源
            try{
                if(ps!=null) ps.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }


    }

    /**
     * 统一处理错误信息
     * @param e
     * @return
     */
    private String errorMsg(Exception e){
        e.printStackTrace();
        LZResult result = new LZResult<>();
        result.setMsg(LZStatus.ERROR.display());
        result.setStatus(LZStatus.ERROR.value());
        result.setData(null);
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }

}
