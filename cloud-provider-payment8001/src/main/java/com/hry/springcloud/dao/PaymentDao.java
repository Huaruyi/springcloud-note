package com.hry.springcloud.dao;

import com.hry.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 这里的注解不是@Repository  是因为Repository在某些时候插入会出现问题
 */
@Mapper
public interface PaymentDao {

    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
