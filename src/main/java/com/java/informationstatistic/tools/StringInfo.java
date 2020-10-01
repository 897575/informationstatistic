package com.java.informationstatistic.tools;

import com.java.informationstatistic.model.PlatformTableInfo;

import java.util.List;

/**
 * 默认字符串
 *
 * @author luyu
 * @since 20200802
 * @version v1.0
 *
 * copyright 18994139782@163.com
 */
public interface StringInfo {

     /**
      * 值为空的字符串
      */
     String NULL = "null";

     /**
      * 情感正向
      */
     String  POSITIVE = "positive";

     /**
      * 情感负向
      */
     String  NEGATIVE = "negative";

     /**
      * 逗号
      */
     String COMMA = ",";

     /**
      * 品牌
      */
     String BRAND = "品牌";

     /**
      * 渠道
      */
     String POST = "_post";

     /**
      * 切割标识
      */
     String POT = ".";

     /**
      * 切分id标识
      */
     String MARK_ID = "!";

     /**
      * 情感分界线
      */
     double FLAG_dATA = 0.6;

     /**
      * 数字1
      */
     int ONE = 1;
     /**
      * 数字2
      */
     int TWO = 2;
     /**
      * 数字3
      */
     int THREE = 3;

     /**
      * 默认excel标题栏
      */
     String[] TITLE = new String[]{"brand","brandLevel","need","needLevel","channel","sentiment","time","num"};

     /**
      * car库数据
      */
     String CAR = "car";

     /**
      * tag库数据
      */
     String TAG = "tag";

     /**
      * excel默认导出数据量
      */
     int EXCEL_SIZE = 500000;

     /**
      * id最大序列
      */
     int INDEX_MAX = 8;
}
