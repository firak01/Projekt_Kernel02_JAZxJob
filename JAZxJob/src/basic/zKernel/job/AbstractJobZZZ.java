package basic.zKernel.job;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.net.client.IApplicationUserZZZ;
import basic.zKernel.net.client.IApplicationZZZ;

public abstract class AbstractJobZZZ implements IJobZZZ, IApplicationUserZZZ, IConstantZZZ{
	public static String sJOB_ALIAS="DEFAULT, IN ERBENDER KLASSE ERSETZEN";
	
	private String sAlias = null;
	private IApplicationZZZ objApplication = null;
	private IJobStepControllerZZZ objJobStepController = null;
	
	
	public AbstractJobZZZ() throws ExceptionZZZ  {	
		//super();
	}
	
	public AbstractJobZZZ(IApplicationZZZ objApplication) throws ExceptionZZZ {
		//super();
		AbstractJobNew_(objApplication);
	}
	
	private boolean AbstractJobNew_(IApplicationZZZ objApplication) throws ExceptionZZZ {
		this.setApplicationObject(objApplication);
		
		String sJobAlias = this.getJobAliasCustom();//Diese Methode soll auf die static Variable DER ERBENDEN Klasse zugreifen.
		this.setJobAlias(sJobAlias);
		
		return true;
	}
	
	@Override
	public abstract String getJobAliasCustom() throws ExceptionZZZ;
	
	@Override
	public String getJobAlias() throws ExceptionZZZ {
		if(StringZZZ.isEmpty(this.sAlias)) {
			this.sAlias = this.getJobAliasCustom();
			if(StringZZZ.isEmpty(this.sAlias)) {
				this.sAlias = this.getJobAliasDefault();
			}
		}
		return this.sAlias;
	}
	
	@Override 
	public void setJobAlias(String sAlias) {
		this.sAlias = sAlias;
	}
	
	public String getJobAliasDefault() {
		return this.getClass().getCanonicalName();
	}
	
	
	//### Aus IJobZZZ
	@Override
	public IJobStepControllerZZZ getJobStepController() {
		return this.objJobStepController;
	}

	@Override
	public void setJobStepController(IJobStepControllerZZZ objJobStepController) {
		this.objJobStepController = objJobStepController;
	}
	
	public abstract boolean process() throws ExceptionZZZ;
	
	//### Aus IApplicationUserZZZ
	@Override
	public IApplicationZZZ getApplicationObject() {
		return this.objApplication;
	}

	@Override
	public void setApplicationObject(IApplicationZZZ objApplication) {
		this.objApplication = objApplication;
	}
}
