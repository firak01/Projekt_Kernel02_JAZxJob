package basic.zKernel.job;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.net.client.IApplicationUserZZZ;

public interface IJobZZZ extends IJobStepControllerUserZZZ, IApplicationUserZZZ{
	
	public boolean process() throws ExceptionZZZ;

	String getJobAliasCustom() throws ExceptionZZZ;
	String getJobAlias() throws ExceptionZZZ;
	void setJobAlias(String sAlias);
}
