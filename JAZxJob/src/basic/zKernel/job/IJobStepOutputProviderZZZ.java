package basic.zKernel.job;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;

public interface IJobStepOutputProviderZZZ extends IJobStepZZZ {
	public HashMap<String, IJobStepOutputZZZ> getHashMapOutput() throws ExceptionZZZ;
	public void setHashMapOutput(HashMap<String, IJobStepOutputZZZ> objOutput);
	
	public void addOutput(String sOutputAlias, IJobStepOutputZZZ objOutput) throws ExceptionZZZ;
	public IJobStepOutputZZZ getOutput(String sOutputAlias) throws ExceptionZZZ;
}
