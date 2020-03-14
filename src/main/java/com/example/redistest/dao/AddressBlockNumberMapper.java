package com.example.redistest.dao;

import com.example.redistest.domian.AddressNumber;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressBlockNumberMapper {

    List<AddressNumber> selectAllAddress();

    List<String> selectBlockMax(@Param("type") Integer type);
    //批量插入
    void insertAddressAndBlockNumber(@Param("list") List<AddressNumber> list);
}
