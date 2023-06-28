package basic.zKernel.job;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.net.client.IApplicationUserZZZ;

public interface IJobZZZ extends IJobStepControllerUserZZZ, IApplicationUserZZZ, IKernelUserZZZ{
	
	public boolean process() throws ExceptionZZZ;

	public String getJobAliasCustom() throws ExceptionZZZ;
	public String getJobAlias() throws ExceptionZZZ;
	public void setJobAlias(String sAlias);

	public ArrayList<IJobStepZZZ> getJobSteps() throws ExceptionZZZ;
	public void setJobSteps(ArrayList<IJobStepZZZ> listaJobStep) throws ExceptionZZZ;
	void addJobStep(IJobStepZZZ objJobStep) throws ExceptionZZZ;

	ArrayList<IJobStepZZZ> createCustomJobSteps() throws ExceptionZZZ;
}
