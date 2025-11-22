package basic.zKernel.job;

import java.util.ArrayList;

import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import custom.zKernel.LogZZZ;

public abstract class AbstractJobStepControllerZZZ extends AbstractObjectZZZ implements IJobStepControllerZZZ{
	private IJobZZZ objJob = null;
	private ArrayList<IJobStepZZZ> listaJobStep = null;
		
	public AbstractJobStepControllerZZZ()throws ExceptionZZZ{
		//super();
	}
	public AbstractJobStepControllerZZZ(IJobZZZ objJob)throws ExceptionZZZ{
		//super(objJob);
		AbstractJobStepControllerNew_(objJob);
	}
	
	private boolean AbstractJobStepControllerNew_(IJobZZZ objJob) throws ExceptionZZZ{
		this.setJob(objJob);
		return true;
	}
	
	@Override
	public ArrayList<IJobStepZZZ> getJobSteps() {
		if(this.listaJobStep==null) {
			this.listaJobStep = new ArrayList<IJobStepZZZ>();
		}
		return this.listaJobStep;
	}
	
	@Override
	public void setJobSteps(ArrayList<IJobStepZZZ> listaJobStep) {
		this.listaJobStep = listaJobStep;
	}
	
	@Override
	public void addJobStep(IJobStepZZZ objJobStep) throws ExceptionZZZ {
		this.getJobSteps().add(objJobStep);
	}
	
	@Override
	public IJobStepZZZ getJobStep(String sJobStepAlias) throws ExceptionZZZ {
		IJobStepZZZ objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sJobStepAlias)) break main;
			
			ArrayList<IJobStepZZZ> listaJobStep = this.getJobSteps();
			for(IJobStepZZZ objJobStep : listaJobStep) {
				if(objJobStep.getJobStepAlias().equals(sJobStepAlias)) {
					objReturn = objJobStep;
				}
			}
		}//end main:
		return objReturn;		
	}
	
	@Override
	public IJobStepOutputProviderZZZ getJobStepForOutput(String sJobStepAlias) throws ExceptionZZZ {
		IJobStepOutputProviderZZZ objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sJobStepAlias)) break main;
			
			IJobStepZZZ objJobStep = this.getJobStep(sJobStepAlias);
			if(objJobStep instanceof IJobStepOutputProviderZZZ) {
				objReturn = (IJobStepOutputProviderZZZ) objJobStep;
			}
		}//end main:
		return objReturn;		
	}
	
	@Override
	public abstract boolean process() throws ExceptionZZZ;

	//### aus IJobUserZZZ
	@Override
	public IJobZZZ getJob() {
		return this.objJob;
	}
	@Override
	public void setJob(IJobZZZ objJob) {
		this.objJob = objJob;
	}
	
	//### aus IKernelUserZZZ
	@Override
	public IKernelZZZ getKernelObject() throws ExceptionZZZ {
		return this.getJob().getKernelObject();
	}
	@Override
	public void setKernelObject(IKernelZZZ objKernel) {
		this.getJob().setKernelObject(objKernel);
	}
	@Override
	public LogZZZ getLogObject() throws ExceptionZZZ {
		return this.getKernelObject().getLogObject();
	}
	@Override
	public void setLogObject(LogZZZ objLog) throws ExceptionZZZ {
		this.getKernelObject().setLogObject(objLog);
	}
	@Override
	public void logLineDate(String sLog) throws ExceptionZZZ {
		this.getLogObject().logLineDate(sLog);
	}
}
