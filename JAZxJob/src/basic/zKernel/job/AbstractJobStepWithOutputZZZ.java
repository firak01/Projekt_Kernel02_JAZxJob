package basic.zKernel.job;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.net.client.IMainZZZ;
import custom.zKernel.LogZZZ;

public abstract class AbstractJobStepWithOutputZZZ extends AbstractJobStepZZZ implements IJobStepOutputProviderZZZ{
	HashMap<String,IJobStepOutputZZZ> hmOutput = null;	
	
	public AbstractJobStepWithOutputZZZ() throws ExceptionZZZ{
		super();
	}
	
	public AbstractJobStepWithOutputZZZ(IJobStepControllerZZZ objController) throws ExceptionZZZ {
		super(objController);
		AbstractJobStepWithOutputNew_();
	}
	
	private boolean AbstractJobStepWithOutputNew_() throws ExceptionZZZ {

		return true;
	}
	
	//Aus IJobStepOutputProviderZZZ
	@Override 
	public HashMap<String,IJobStepOutputZZZ> getHashMapOutput() throws ExceptionZZZ{
		if(this.hmOutput==null) {
			this.hmOutput = new HashMap<String,IJobStepOutputZZZ>();
		}
		return this.hmOutput;
	}
	
	@Override
	public void setHashMapOutput(HashMap<String,IJobStepOutputZZZ> hmOutput) {
		this.hmOutput = hmOutput;
	}
	
	@Override 
	public void addOutput(String sOutputAlias, IJobStepOutputZZZ objOutput) throws ExceptionZZZ{
		this.getHashMapOutput().put(sOutputAlias, objOutput);
	}
	
	@Override 
	public IJobStepOutputZZZ getOutput(String sOutputAlias) throws ExceptionZZZ {
		return this.getHashMapOutput().get(sOutputAlias);
	}
}
