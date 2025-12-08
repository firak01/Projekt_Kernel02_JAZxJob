package basic.zKernel.job;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjektZZZ;

public interface IJobStepOutputZZZ {
	public HashMapIndexedObjektZZZ<Integer,?> getHashMapIndexed() throws ExceptionZZZ;
	public void setHashMapIndexed(HashMapIndexedObjektZZZ<Integer,?> hmIndexed);
	
	public File getFile();
	public void setFile(File objFile);
}
