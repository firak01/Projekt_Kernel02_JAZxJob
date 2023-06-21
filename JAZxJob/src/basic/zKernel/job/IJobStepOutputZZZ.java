package basic.zKernel.job;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;

public interface IJobStepOutputZZZ {
	public HashMapIndexedZZZ<Integer,?> getHashMapIndexed() throws ExceptionZZZ;
	public void setHashMapIndexed(HashMapIndexedZZZ<Integer,?> hmIndexed);
	
	public File getFile();
	public void setFile(File objFile);
}
