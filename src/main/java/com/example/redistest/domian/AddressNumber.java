package com.example.redistest.domian;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@TableName(value = "address_blockNumber")
public class AddressNumber {
    @TableId
    private Long id;

    private String address;

    private String blockNumber;

    private Integer type;
}
