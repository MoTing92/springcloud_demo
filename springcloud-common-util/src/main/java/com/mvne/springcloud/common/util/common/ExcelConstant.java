package com.mvne.springcloud.common.util.common;

public class ExcelConstant {

	public static final String[] COLUMN = {
			"iccId"
			,"imsi"
			,"sn"
			,"pin1"
			,"pin2"
			,"puk1"
			,"puk2"
			,"billId"
			,"provCode"
			,"areaCode"
			,"mvnoId"
			,"mnoId"
			,"doneDate"
			,"modifyDate"
			,"useDate"
			,"resNumHlr"
			,"manageStatus"
			,"desc"};
	public static final String[] SETTLE_COLUMN = {"结算年月","渠道商名称","结算金额","备注"};
	public static final String[] SETTLE_COLUMN2 = {"month","channelName","settleAmount","remark"};
	
	public static void main(String[] args) {
		System.out.println(ExcelConstant.COLUMN.length);
	}
}
