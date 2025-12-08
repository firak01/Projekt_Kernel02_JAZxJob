package basic.zKernel.job;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjektZZZ;
import use.zKernel.html.step01.writer.TableHeadZZZ;

public class JobStepOutputZZZ implements IJobStepOutputZZZ{
	HashMapIndexedObjektZZZ<Integer, ?> hmIndexed = null;
	File objFile = null;
	
	public JobStepOutputZZZ() {		
	}
	
	public JobStepOutputZZZ(HashMapIndexedObjektZZZ<Integer, ?> hmIndexedOutput) {
		this.setHashMapIndexed(hmIndexedOutput);
	}

	public JobStepOutputZZZ(File fileOutput) {
		this.setFile(fileOutput);
	}

		@Override
	public HashMapIndexedObjektZZZ<Integer, ?> getHashMapIndexed() throws ExceptionZZZ {
		if(this.hmIndexed==null) {
			this.hmIndexed = new HashMapIndexedObjektZZZ();
		}
		return this.hmIndexed;
	}

	@Override
	public void setHashMapIndexed(HashMapIndexedObjektZZZ<Integer, ?> hmIndexed) {
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
