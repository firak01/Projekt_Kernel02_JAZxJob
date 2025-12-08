package basic.zKernel.job;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZ;
import use.zKernel.html.step01.writer.TableHeadZZZ;

public class JobStepOutputZZZ implements IJobStepOutputZZZ{
	HashMapIndexedObjectZZZ<Integer, ?> hmIndexed = null;
	File objFile = null;
	
	public JobStepOutputZZZ() {		
	}
	
	public JobStepOutputZZZ(HashMapIndexedObjectZZZ<Integer, ?> hmIndexedOutput) {
		this.setHashMapIndexed(hmIndexedOutput);
	}

	public JobStepOutputZZZ(File fileOutput) {
		this.setFile(fileOutput);
	}

		@Override
	public HashMapIndexedObjectZZZ<Integer, ?> getHashMapIndexed() throws ExceptionZZZ {
		if(this.hmIndexed==null) {
			this.hmIndexed = new HashMapIndexedObjectZZZ();
		}
		return this.hmIndexed;
	}

	@Override
	public void setHashMapIndexed(HashMapIndexedObjectZZZ<Integer, ?> hmIndexed) {
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
