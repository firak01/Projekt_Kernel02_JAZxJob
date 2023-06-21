package basic.zKernel.job;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.net.client.IMainUserZZZ;

public interface IJobStepZZZ extends IMainUserZZZ, IJobStepControllerUserZZZ{
	
	public String getJobStepAliasCustom() throws ExceptionZZZ;
	public String getJobStepAlias() throws ExceptionZZZ;
	public void setJobStepAlias(String sAlias);
	
	public boolean process() throws ExceptionZZZ;
	
}
