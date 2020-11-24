package dev.aid.boost4j.mbp;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.DB2TypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

import java.sql.Timestamp;

/**
 * 自定义参数类型转换
 *
 * @author 04637@163.com
 * @date 2020/11/18
 */
public class MyTypeConvert implements ITypeConvert {


    /**
     * 执行类型转换
     *
     * @param globalConfig 全局配置
     * @param fieldType    字段类型
     * @return ignore
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        if (fieldType.contains("timestamp")) {
            return new DateTimeType();
        }
        if (fieldType.contains("bool")) {
            return new BooleanType();
        }
        return DB2TypeConvert.INSTANCE.processTypeConvert(globalConfig, fieldType);
    }
}

/**
 * 日期类型转换为 java.sql.Timestamp {@link Timestamp}
 */
class DateTimeType implements IColumnType {

    /**
     * 获取字段类型
     *
     * @return 字段类型
     */
    @Override
    public String getType() {
        return Timestamp.class.getSimpleName();
    }

    /**
     * 获取字段类型完整名
     *
     * @return 字段类型完整名
     */
    @Override
    public String getPkg() {
        return Timestamp.class.getName();
    }
}

/**
 * pg布尔值类型转换 {@link Boolean}
 */
class BooleanType implements IColumnType {

    /**
     * 获取字段类型
     *
     * @return 字段类型
     */
    @Override
    public String getType() {
        return Boolean.class.getSimpleName();
    }

    /**
     * 获取字段类型完整名
     *
     * @return 字段类型完整名
     */
    @Override
    public String getPkg() {
        return Boolean.class.getName();
    }
}
