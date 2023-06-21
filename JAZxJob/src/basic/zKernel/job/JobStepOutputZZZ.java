package basic.zKernel.job;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import use.zKernel.html.step01.writer.TableHeadZZZ;

public class JobStepOutputZZZ implements IJobStepOutputZZZ{
	HashMapIndexedZZZ<Integer, ?> hmIndexed = null;
	File objFile = null;
	
	public JobStepOutputZZZ() {		
	}
	
	public JobStepOutputZZZ(HashMapIndexedZZZ<Integer, ?> hmIndexedOutput) {
		this.setHashMapIndexed(hmIndexedOutput);
	}

	public JobStepOutputZZZ(File fileOutput) {
		this.setFile(fileOutput);
	}

		@Override
	public HashMapIndexedZZZ<Integer, ?> getHashMapIndexed() throws ExceptionZZZ {
		if(this.hmIndexed==null) {
			this.hmIndexed = new HashMapIndexedZZZ();
		}
		return this.hmIndexed;
	}

	@Override
	public void setHashMapIndexed(HashMapIndexedZZZ<Integer, ?> hmIndexed) {
		this.hmIndexed = hmIndexed;
	}

	@Override
	public File getFile() {		
		return objFile;
	}

	@Override
	public void setFile(File objFile) {
		this.objFile = objFile;
	}

}
