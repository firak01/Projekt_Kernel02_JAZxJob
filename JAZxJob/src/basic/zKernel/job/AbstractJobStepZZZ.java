package basic.zKernel.job;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.net.client.IMainZZZ;
import custom.zKernel.LogZZZ;

public abstract class AbstractJobStepZZZ extends KernelUseObjectZZZ implements IJobStepZZZ, IConstantZZZ{
	public static String sJOBSTEP_ALIAS="DEFAULT, IN ERBENDER KLASSE ERSETZEN";
	
	private IJobStepControllerZZZ objJobStepController=null;
	private String sStepAlias = null;
	
	private IMainZZZ objMainHandler=null;//die im JobStep verwendete Application, das ist nicht die andere "aussen" rum.
	
	public AbstractJobStepZZZ() throws ExceptionZZZ{
		//super();
	}
	
	public AbstractJobStepZZZ(IJobStepControllerZZZ objController) throws ExceptionZZZ {
		//super();
		AbstractJobStepNew_(objController);
	}
	
	private boolean AbstractJobStepNew_(IJobStepControllerZZZ objController) throws ExceptionZZZ {
		this.setJobStepController(objController);
		
		String sJobStepAlias = this.getJobStepAliasCustom();//Diese Methode soll auf die static Variable DER ERBENDEN Klasse zugreifen.
		this.setJobStepAlias(sJobStepAlias);
		return true;
	}
	
	//Aus IJobStepZZZ
	@Override 
	public abstract boolean process() throws ExceptionZZZ;
	
	@Override
	public abstract String getJobStepAliasCustom() throws ExceptionZZZ;
	
	@Override
	public String getJobStepAlias() throws ExceptionZZZ {
		if(StringZZZ.isEmpty(this.sStepAlias)) {
			this.sStepAlias = this.getJobStepAliasCustom();
			if(StringZZZ.isEmpty(this.sStepAlias)) {
				this.sStepAlias = this.getJobStepAliasDefault();
			}
		}
		return this.sStepAlias;
	}
	
	@Override 
	public void setJobStepAlias(String sStepAlias) {
		this.sStepAlias = sStepAlias;
	}
	
	public String getJobStepAliasDefault() {
		return this.getClass().getCanonicalName();
	}

	//Aus IMainUserZZZ
	@Override
	public IMainZZZ getMainObject() {
		return this.objMainHandler;
	}
	
	@Override
	public void setMainObject(IMainZZZ objMainHandler) {
		this.objMainHandler = objMainHandler;
	}
	
	//Aus IJobStepControllerUserZZZ
	@Override
	public IJobStepControllerZZZ getJobStepController() {
		return this.objJobStepController;
	}

	@Override
	public void setJobStepController(IJobStepControllerZZZ objJobStepController) {
		this.objJobStepController = objJobStepController;
	}
	
	//Aus IKernelUserZZZ
	@Override
	public IKernelZZZ getKernelObject() {
		IKernelZZZ objReturn = super.getKernelObject();
		if(objReturn==null) {
			IKernelZZZ objKernelApplication = this.getJobStepController().getKernelObject();
			this.setKernelObject(objKernelApplication);
		}
		return objReturn;
	}

	@Override
	public void setKernelObject(IKernelZZZ objKernel) {
		super.setKernelObject(objKernel);
	}

	@Override
	public LogZZZ getLogObject() {
		return this.getKernelObject().getLogObject();
	}

	@Override
	public void setLogObject(LogZZZ objLog) {
		this.getKernelObject().setLogObject(objLog);
	}

	@Override
	public void logLineDate(String sLog) {
		this.getLogObject().logLineDate(sLog);
	}


}
