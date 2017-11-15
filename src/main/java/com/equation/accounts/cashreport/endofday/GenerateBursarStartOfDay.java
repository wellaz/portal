package com.equation.accounts.cashreport.endofday;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class GenerateBursarStartOfDay {
	ResultSet rs, rs1;
	Statement stm, stmt;

	public GenerateBursarStartOfDay(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stmt = stmt;

	}

}
