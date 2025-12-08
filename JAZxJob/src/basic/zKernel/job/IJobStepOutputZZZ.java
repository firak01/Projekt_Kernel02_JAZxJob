package basic.zKernel.job;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZ;

public interface IJobStepOutputZZZ {
	public HashMapIndexedObjectZZZ<Integer,?> getHashMapIndexed() throws ExceptionZZZ;
	public void setHashMapIndexed(HashMapIndexedObjectZZZ<Integer,?> hmIndexed);
	
	public File getFile();
	public void setFile(File objFile);
}
