package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);


    @Insert("insertBatch into employee(name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) VALUES " +
            "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);


    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    @Select("select * from employee where id = #{id}")
    Employee getById(String id);

    @Update("update employee set " +
            "username = #{username}," +
            " name = #{name}," +
            " phone = #{phone}," +
            " id_number = #{idNumber}," +
            " sex = #{sex} " +
            "where id = #{id}")
    int update(Employee employee);

    @Update("update employee set status = #{status} where id = #{id}")
    @AutoFill(value = OperationType.UPDATE)
    int updateStatus(Long id, Integer status);
}
